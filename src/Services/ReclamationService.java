
package Services;

import java.util.ArrayList;
import Entities.Reclamation;
import Entities.Reponse;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class ReclamationService {
   private ArrayList<Reclamation> recs;
   private  ArrayList<Reponse> reps;
    private static ReclamationService instance = null;
    private boolean resultOK;
    private ConnectionRequest req;

    private ReclamationService() {
        req = new ConnectionRequest();
    }

    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }


    private ArrayList<Reclamation> parseReclamation(String jsonText) {
        try {
            recs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Reclamation ele = new Reclamation();
                ele.setId((int)Float.parseFloat(obj.get("id").toString()));
                ele.setAuthor_id((int)Float.parseFloat(obj.get("aid").toString()));
                ele.setContenu(obj.get("contenu").toString());
                ele.setSujet(obj.get("title").toString());
                ele.setStatut(obj.get("statut").toString());
                ele.setAuthor_name(obj.get("aname").toString());
                java.sql.Date d = new Date(Integer.parseInt(obj.get("date").toString().substring(0, 4)) - 1900, Integer.parseInt(obj.get("date").toString().substring(5, 7)) - 1, Integer.parseInt(obj.get("date").toString().substring(8, 10)));
                ele.setDate(d);
                recs.add(ele);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return recs;
    }
    
    private ArrayList<Reponse> parseComment(String jsonText) {
        try {
            reps = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Reponse ele = new Reponse();
                ele.setId((int)Float.parseFloat(obj.get("id").toString()));
                ele.setAuthor_id((int)Float.parseFloat(obj.get("aid").toString()));
                ele.setContenu(obj.get("contenu").toString());
                ele.setAuthor_name(obj.get("aname").toString());
                java.sql.Date d = new Date(Integer.parseInt(obj.get("date").toString().substring(0, 4)) - 1900, Integer.parseInt(obj.get("date").toString().substring(5, 7)) - 1, Integer.parseInt(obj.get("date").toString().substring(8, 10)));
                ele.setDate(d);
                reps.add(ele);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return reps;
    }

    public ArrayList<Reclamation> getReclamations() {
        String url = CONSTANTS.BASEURL + "recs/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                recs = parseReclamation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return recs;
    }
    
    public ArrayList<Reponse> getReponses(Reclamation e){
        String url = CONSTANTS.BASEURL + "rep/"+e.getId();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reps = parseComment(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reps;
    }
    
    
    public boolean addReclamation(String title,String contenu) {
        String url = CONSTANTS.BASEURL+"rec/add/";
     ConnectionRequest request= new ConnectionRequest();;
        request.setUrl(url);
        request.setPost(true);
        request.addArgument("sujet", title);
        request.addArgument("contenu", contenu);
        request.addArgument("author", Session.currentUser.getId()+"");
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
     public boolean addReponse(int recid,String contenu) {
        String url = CONSTANTS.BASEURL+"rep/add/";
     ConnectionRequest request= new ConnectionRequest();;
        request.setUrl(url);
        request.setPost(true);
        request.addArgument("recid", recid+"");
        request.addArgument("contenu", contenu);
        request.addArgument("author", Session.currentUser.getId()+"");
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
     
     public boolean changerStatutReclamation(Reclamation e) {
        String url = CONSTANTS.BASEURL + "rec/change/";
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
     
     public void deleteReclamation(int id) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(  CONSTANTS.BASEURL + "rec/delete/"+id);
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
