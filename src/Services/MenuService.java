package Services;

import Entities.MenuElement;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kraya.moazmar.com.CONSTANTS;

public class MenuService {

    public ArrayList<MenuElement> elements;

    public static MenuService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MenuService() {
        req = new ConnectionRequest();
    }
    public static MenuService getInstance() {
        if (instance == null) {
            instance = new MenuService();
        }
        return instance;
    }

    public boolean addMenu(MenuElement e) {
        String url = CONSTANTS.BASEURL+"menu/add/";
     ConnectionRequest request= new ConnectionRequest();;

        request.setUrl(url);
        request.setPost(true);
        
        request.addArgument("nom", e.getNom());
        request.addArgument("description", e.getDescription());
        request.addArgument("categorie", e.getCategorie());
        request.addArgument("prix",""+ e.getPrix());
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = request.getResponseCode() == 200; //Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOK;
    }
    public boolean editMenu(MenuElement e) {
        String url = CONSTANTS.BASEURL+"menu/edit/";
     ConnectionRequest r= new ConnectionRequest();

        r.setUrl(url);
        r.setPost(true);
        r.addArgument("id", e.getId()+"");
        r.addArgument("nom", e.getNom());
        r.addArgument("description", e.getDescription());
        r.addArgument("categorie", e.getCategorie());
        r.addArgument("prix",""+ e.getPrix());
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

    private ArrayList<MenuElement> parseMenu(String jsonText) {
        try {
            elements = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                MenuElement ele = new MenuElement();
                ele.setId((int)Float.parseFloat(obj.get("id").toString()));
                ele.setNom(obj.get("nom").toString());
                ele.setDescription(obj.get("description").toString());
                ele.setCategorie(obj.get("categorie").toString());
                ele.setImage(obj.get("image").toString());
                ele.setPrix(Float.parseFloat(obj.get("prix").toString()));
                elements.add(ele);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return elements;
    }

    public ArrayList<MenuElement> getMenu() {
        String url = CONSTANTS.BASEURL + "menu/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                elements = parseMenu(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return elements;
    }
    
    
    public void deleteMenu(int id) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(  CONSTANTS.BASEURL + "menu/delete/"+ id);
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
