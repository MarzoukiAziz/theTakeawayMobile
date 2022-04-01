package Services;

import Entities.Commande;
import Entities.CommandeElement;
import GUI.Commande.CommandeScreen;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kraya.moazmar.com.CONSTANTS;

public class CommandeService {

    public ArrayList<Commande> cmds;

    public static CommandeService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private CommandeService() {
        req = new ConnectionRequest();
    }

    public static CommandeService getInstance() {
        if (instance == null) {
            instance = new CommandeService();
        }
        return instance;
    }

    private ArrayList<CommandeElement> parseElements(List<Map<String, Object>> list) {
        ArrayList<CommandeElement> tabs = new ArrayList<>();
        for (Map<String, Object> obj : list) {
            CommandeElement t = new CommandeElement();
            t.setId((int) Float.parseFloat(obj.get("id").toString()));
            t.setElement_id((int) Float.parseFloat(obj.get("eid").toString()));
            t.setQuantite((int) Float.parseFloat(obj.get("q").toString()));
            tabs.add(t);
        }
        return tabs;
    }

    private ArrayList<Commande> parseCommande(String jsonText) {
        try {
            cmds = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Commande r = new Commande();
                r.setId((int) Float.parseFloat(obj.get("id").toString()));
                java.sql.Date d = new Date(Integer.parseInt(obj.get("date").toString().substring(0, 4)) - 1900, Integer.parseInt(obj.get("date").toString().substring(5, 7)) - 1, Integer.parseInt(obj.get("date").toString().substring(8, 10)));
                r.setDate(d);
                r.setRestaurant_id((int) Float.parseFloat(obj.get("rid").toString()));
                r.setClient_id((int) Float.parseFloat(obj.get("cid").toString()));
                r.setTotal(Float.parseFloat(obj.get("total").toString()));
                r.setStatut(obj.get("statut").toString());
                r.setMethode(obj.get("methode").toString());
                r.setElements(parseElements((List<Map<String, Object>>) obj.get("elements")));
                cmds.add(r);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return cmds;
    }

    public ArrayList<Commande> getcommandes() {
        String url = CONSTANTS.BASEURL + "cmd/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cmds = parseCommande(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cmds;
    }
    String res = "";

    public String qrcode(int id) {
      
        
        String url = CONSTANTS.BASEURL + "cmd/qrcode/";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("id", id + "");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String jsonText = new String(req.getResponseData());
                JSONParser j = new JSONParser();
                Map<String, Object> elementListJson;
                System.out.println(jsonText);
                try {
                    elementListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                    res = (String) elementListJson.get("qrcode");
                } catch (IOException ex) {
                    Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return res;
    }

    public boolean changerStatutCommande(Commande e) {
        String url = CONSTANTS.BASEURL + "cmd/change/";
        ConnectionRequest r = new ConnectionRequest();
        r.setUrl(url);
        r.setPost(true);
        r.addArgument("id", e.getId() + "");
        r.addArgument("statut", e.getStatut());
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
    String eid = "";
    String eq = "";

    public void commander(Commande e, Resources rs) {

        String url = CONSTANTS.BASEURL + "cmd/new/";

        ConnectionRequest request = new ConnectionRequest();;
        request.setUrl(url);
        request.setPost(true);
        request.addArgument("cid", e.getClient_id() + "");
        request.addArgument("rid", e.getRestaurant_id() + "");
        request.addArgument("total", e.getTotal() + "");

        e.getElements().stream()
                .forEach(exx -> {
                    eid += ("-" + exx.getElement_id());
                    eq += ("-" + exx.getQuantite());
                });
        request.addArgument("eid", eid.substring(1));
        request.addArgument("eq", eq.substring(1));
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String jsonText = new String(request.getResponseData());
                    JSONParser j = new JSONParser();
                    Map<String, Object> elementListJson;
                    elementListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                    if (elementListJson.get("error") == "true") {
                        Dialog.show("Echec", "Vérifier vos données", "OK", null);
                    } else {
                        Dialog.show("Félicitations", "Votre Commande a été ajouté avec succcés!", "OK", null);
                       CommandeScreen rvs = new CommandeScreen(rs, "",null);
                       rvs.show();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
          
    }

}
