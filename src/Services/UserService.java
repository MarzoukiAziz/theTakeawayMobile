package Services;

import Entities.User;
import GUI.DashboardScreen;
import GUI.USER.LoginScreen;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    public ArrayList<User> users;

    public static UserService instance;

    public static boolean resultOk = true;
    String json;
    public boolean resultOK;

    private ConnectionRequest req;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public UserService() {
        req = new ConnectionRequest();

    }

    public void inscription(String email, String nom, String prenom, String tel, String plainPassword, Resources rs) {

        String url = CONSTANTS.BASEURL + "register";
        if (email.isEmpty() || nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || plainPassword.isEmpty()) {
            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);
            
        }

        req.setUrl(url);
        req.addArgument("email", email);
        req.addArgument("nom", nom);
        req.addArgument("prenom", prenom);
        req.addArgument("tel", tel);
        req.addArgument("plainPassword", plainPassword);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
                if (resultOK) {
                    Dialog.show("Félicitations!", "Votre compte a été crée", "OK", null);
                    new LoginScreen(rs).show();
                } else {
                    Dialog.show("Désolé!", "Un probeme lors de l'inscription", "Try Again", null);
                }
            }
        }
        );

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void connexion(String email, String pwd, Resources rs) {
        String url = CONSTANTS.BASEURL + "login";
        req = new ConnectionRequest(url, false);

        req.addArgument("email", email);
        req.addArgument("pwd", pwd);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String jsonText = new String(req.getResponseData());
                    JSONParser j = new JSONParser();
                    Map<String, Object> elementListJson;
                    elementListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                    if (elementListJson.get("error") == "true") {
                        Dialog.show("Echec d'authentification", "Username ou mot de passe éronné", "OK", null);
                    } else {
                        connexionSucces((Map<String, Object>) elementListJson.get("user"), rs);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                }

                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void connexionSucces(Map<String, Object> data, Resources rs) {
        User u = new User();
        u.setId((int) Float.parseFloat(data.get("id").toString()));
        u.setNom(data.get("nom").toString());
        u.setPrenom(data.get("prenom").toString());
        u.setEmail(data.get("email").toString());
        u.setRole(data.get("role").toString());
        u.setNum_tel(data.get("num_tel").toString());
        u.setPoints((int) Float.parseFloat(data.get("points").toString()));
        u.setAvatar(data.get("avatar").toString());
        Session.currentUser = u;
        new DashboardScreen(rs).show();
    }

    public boolean editUser(User u) {
        String url = CONSTANTS.BASEURL + "user/edit/";
        ConnectionRequest r = new ConnectionRequest();
        r.setUrl(url);
        r.setPost(true);
        r.addArgument("id", u.getId()+"");
        r.addArgument("nom", u.getNom());
        r.addArgument("prenom", u.getPrenom());
        r.addArgument("email", u.getEmail());
        r.addArgument("num_tel", "" + u.getNum_tel());
        r.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = r.getResponseCode() == 200; //Code HTTP 200 OK
                r.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return resultOK;
    }

    private ArrayList<User> parseUser(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("users");
            for (Map<String, Object> data : list) {
                User u = new User();
                u.setId((int) Float.parseFloat(data.get("id").toString()));
                u.setNom(data.get("nom").toString());
                u.setPrenom(data.get("prenom").toString());
                u.setEmail(data.get("email").toString());
                u.setRole(data.get("role").toString());
                u.setNum_tel(data.get("num_tel").toString());
                u.setPoints((int) Float.parseFloat(data.get("points").toString()));
                u.setAvatar(data.get("avatar").toString());
                users.add(u);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    public ArrayList<User> getUsers() {
        String url = CONSTANTS.BASEURL + "users/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    
     public void deleteUser(int id) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(  CONSTANTS.BASEURL + "user/delete/"+ id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = con.getResponseCode() == 200; //Code HTTP 200 OK
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }
}
