
package Services;

import java.util.ArrayList;
import Entities.Blog;
import Entities.Commentaire;
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

public class BlogService {
   public ArrayList<Blog> blogs;
   public ArrayList<Commentaire> comments;
    public static BlogService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private BlogService() {
        req = new ConnectionRequest();
    }

    public static BlogService getInstance() {
        if (instance == null) {
            instance = new BlogService();
        }
        return instance;
    }


    private ArrayList<Blog> parseBlog(String jsonText) {
        try {
            blogs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Blog ele = new Blog();
                ele.setId((int)Float.parseFloat(obj.get("id").toString()));
                ele.setAuthor_id((int)Float.parseFloat(obj.get("aid").toString()));
                ele.setContenu(obj.get("contenu").toString());
                ele.setTitle(obj.get("title").toString());
                ele.setImage(obj.get("image").toString());
                ele.setStatut(obj.get("statut").toString());
                ele.setAuthor_name(obj.get("aname").toString());
                java.sql.Date d = new Date(Integer.parseInt(obj.get("date").toString().substring(0, 4)) - 1900, Integer.parseInt(obj.get("date").toString().substring(5, 7)) - 1, Integer.parseInt(obj.get("date").toString().substring(8, 10)));
                ele.setDate(d);
                blogs.add(ele);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return blogs;
    }
    
    private ArrayList<Commentaire> parseComment(String jsonText) {
        try {
            comments = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> elementListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) elementListJson.get("res");
            for (Map<String, Object> obj : list) {
                Commentaire ele = new Commentaire();
                ele.setId((int)Float.parseFloat(obj.get("id").toString()));
                ele.setAuthor_id((int)Float.parseFloat(obj.get("aid").toString()));
                ele.setContenu(obj.get("contenu").toString());
                ele.setAuthor_name(obj.get("aname").toString());
                java.sql.Date d = new Date(Integer.parseInt(obj.get("date").toString().substring(0, 4)) - 1900, Integer.parseInt(obj.get("date").toString().substring(5, 7)) - 1, Integer.parseInt(obj.get("date").toString().substring(8, 10)));
                ele.setDate(d);
                comments.add(ele);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

    public ArrayList<Blog> getBlogs() {
        String url = CONSTANTS.BASEURL + "blogs/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                blogs = parseBlog(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return blogs;
    }
    
    public ArrayList<Commentaire> getComments(Blog e){
        String url = CONSTANTS.BASEURL + "comments/"+e.getId();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                comments = parseComment(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return comments;
    }
    
    
    public boolean addBlog(String title,String contenu) {
        String url = CONSTANTS.BASEURL+"blog/add/";
     ConnectionRequest request= new ConnectionRequest();;
        request.setUrl(url);
        request.setPost(true);
        request.addArgument("title", title);
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
     public boolean addComment(int blogid,String contenu) {
        String url = CONSTANTS.BASEURL+"comment/add/";
     ConnectionRequest request= new ConnectionRequest();;
        request.setUrl(url);
        request.setPost(true);
        request.addArgument("blogid", blogid+"");
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
     
     public boolean changerStatutBlog(Blog e) {
        String url = CONSTANTS.BASEURL + "blog/change/";
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
     
     public void deleteBlog(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(  CONSTANTS.BASEURL + "blog/delete/"+id);
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
