
package Services;

import Entities.Restaurant;
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

public class RestaurantService {
    
    public ArrayList<Restaurant> restaurants;

    public static RestaurantService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private RestaurantService() {
        req = new ConnectionRequest();
    }

    public static RestaurantService getInstance() {
        if (instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }


    private ArrayList<Restaurant> parseRestaurant(String jsonText) {
        try {
            restaurants = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Restaurant ele = new Restaurant();
                ele.setId((int)Float.parseFloat(obj.get("id").toString()));
                 ele.setX(Float.parseFloat(obj.get("x").toString()));
                 ele.setY(Float.parseFloat(obj.get("y").toString()));
                ele.setNom(obj.get("nom").toString());
                ele.setDescription(obj.get("description").toString());
                ele.setAdresse(obj.get("adresse").toString());
                ele.setHeure_ouverture(obj.get("heure_ouverture").toString());
                ele.setHeure_fermeture(obj.get("heure_fermeture").toString());
                ele.setTelephone(obj.get("telephone").toString());
                ArrayList<String> imgs =(ArrayList<String>) obj.get("images");
                ele.setImages(imgs);
                restaurants.add(ele);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return restaurants;
    }

    public ArrayList<Restaurant> getRestaurants() {
        String url = kraya.moazmar.com.CONSTANTS.BASEURL + "restaurants/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                restaurants = parseRestaurant(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return restaurants;
    }
}
