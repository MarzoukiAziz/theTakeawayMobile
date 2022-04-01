package Services;

import Entities.Reservation;
import Entities.Table;
import GUI.Reservation.ReservationsScreen;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kraya.moazmar.com.CONSTANTS;
import com.codename1.ui.util.Resources;

public class ReservationService {

    public ArrayList<Reservation> rev;

    public static ReservationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ReservationService() {
        req = new ConnectionRequest();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    private ArrayList<Table> parseTable(List<Map<String, Object>> list) {
        ArrayList<Table> tabs = new ArrayList<>();
        for (Map<String, Object> obj : list) {
            Table t = new Table();
            t.setId((int) Float.parseFloat(obj.get("id").toString()));
            t.setPos_x((int) Float.parseFloat(obj.get("posX").toString()));
            t.setPos_y((int) Float.parseFloat(obj.get("posY").toString()));
            t.setNumero((int) Float.parseFloat(obj.get("numero").toString()));
            t.setRestaurant_id((int) Float.parseFloat(obj.get("restaurant_id").toString()));
            t.setNbPlaces((int) Float.parseFloat(obj.get("nbPalces").toString()));
            tabs.add(t);
        }
        return tabs;
    }

    private ArrayList<Reservation> parseRev(String jsonText) {
        try {
            rev = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Reservation r = new Reservation();
                r.setId((int) Float.parseFloat(obj.get("id").toString()));
                java.sql.Date d = new Date(Integer.parseInt(obj.get("date").toString().substring(0, 4)) - 1900, Integer.parseInt(obj.get("date").toString().substring(5, 7))-1, Integer.parseInt(obj.get("date").toString().substring(8, 10)));
                java.sql.Time ha = new Time(Integer.parseInt(obj.get("heureArrive").toString().substring(11, 13)), Integer.parseInt(obj.get("heureArrive").toString().substring(14, 16)), 0);
                java.sql.Time hd = new Time(Integer.parseInt(obj.get("heureDepart").toString().substring(11, 13)), Integer.parseInt(obj.get("heureDepart").toString().substring(14, 16)), 0);
                r.setHeure_arrive(ha);
                r.setHeure_depart(hd);
                r.setDate(d);
                r.setNb_personne((int) Float.parseFloat(obj.get("nbPersonne").toString()));
                r.setClient_id((int) Float.parseFloat(obj.get("clientId").toString()));
                r.setRestaurant_id((int) Float.parseFloat(obj.get("restaurant").toString()));
                r.setStatut(obj.get("statut").toString());
                r.setTables(parseTable((List<Map<String, Object>>) obj.get("tables")));
                rev.add(r);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return rev;
    }

    public ArrayList<Reservation> getReservations() {
        String url = CONSTANTS.BASEURL + "rev/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rev = parseRev(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return rev;
    }

    public boolean changerStatutReservation(Reservation e) {
        String url = CONSTANTS.BASEURL + "rev/change/";
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

    public ArrayList<Table> getDispoTables(Reservation e, int duree) {
        ArrayList<Table> tables = new ArrayList<>();
        String url = CONSTANTS.BASEURL + "tables/" + e.getRestaurant_id() + "/" + e.getDate() + "/" + e.getHeure_arrive() + "/" + duree+"/" + "5";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    JSONParser j = new JSONParser();
                    String data = new String(req.getResponseData());
                    Map<String, Object> elementListJson;

                    elementListJson = j.parseJSON(new CharArrayReader(data.toCharArray()));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("freeTables");
                    for (Map<String, Object> obj : list) {
                        Table t = new Table();
                        t.setId((int) Float.parseFloat(obj.get("id").toString()));
                        t.setPos_x((int) Float.parseFloat(obj.get("posX").toString()));
                        t.setPos_y((int) Float.parseFloat(obj.get("posY").toString()));
                        t.setNumero((int) Float.parseFloat(obj.get("numero").toString()));
                        t.setRestaurant_id((int) Float.parseFloat(obj.get("restaurant_id").toString()));
                        t.setNbPlaces((int) Float.parseFloat(obj.get("nbPalces").toString()));
                        tables.add(t);
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tables;
    }
    
    
    public void reserve(Reservation e,int duree, Resources rs) {
       
        ArrayList<Table> tables = new ArrayList<>();
        String tabs = e.getTables().stream().map(x->x.getId()+"").reduce("", (x,y)->x+"-"+y);
        tabs=tabs.substring(1);
        String url = CONSTANTS.BASEURL + "create-rev/" +
                e.getRestaurant_id() + "/" +
                e.getDate() + "/" +
                e.getHeure_arrive() + "/" +
                duree+"/" +
                e.getNb_personne()+"/"+
                e.getClient_id()+"/"+
                tabs;
        ConnectionRequest request= new ConnectionRequest();;
        request.setUrl(url);
        request.setPost(true);
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
                        Dialog.show("Félicitations", "Votre réservation a été ajouté avec succcés!", "OK", null);
                        ReservationsScreen rvs = new ReservationsScreen(rs, "");
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
