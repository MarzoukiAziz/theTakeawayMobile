package GUI.Commande;

import Entities.Restaurant;
import Entities.Commande;
import Entities.MenuElement;
import Entities.Table;
import Entities.User;
import Services.CommandeService;
import Services.MenuService;
import Services.ReservationService;
import Services.RestaurantService;
import Services.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Optional;
import kraya.moazmar.com.CONSTANTS;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Display;

public class CommandeDetailsScreen extends Form {

    CommandeDetailsScreen(Resources rs, Commande cmd) {
        setLayout(BoxLayout.y());
        
        
        Toolbar tb = getToolbar();
        tb.setTitle("Commande n° " + cmd.getId());
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new CommandeScreen(rs, "",null).show();
        });
        ArrayList<Restaurant> res = RestaurantService.getInstance().getRestaurants();
        Restaurant r = new Restaurant();
        for (Restaurant x : res) {
            if (x.getId() == cmd.getRestaurant_id()) {
                r = x;
                break;
            }
        }
        ArrayList<MenuElement> menu = MenuService.getInstance().getMenu();
        
        Container c = new Container();
        c.setLayout(BoxLayout.y());
        
        EncodedImage encProfile = EncodedImage.createFromImage(rs.getImage("hot-pot.png"), false);
        URLImage urlimProfile = URLImage.createToStorage(encProfile, r.getImages(), CONSTANTS.AVATARURL + r.getImages(), URLImage.RESIZE_SCALE_TO_FILL);
        ImageViewer imgvProfile = new ImageViewer(urlimProfile);
        
        
        Image profilePic = imgvProfile.getImage();
        Image mask = rs.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        
        
        
        
        SpanLabel resto = new SpanLabel();
        resto.setText("Commande : " + r.getNom());
        resto.setUIID("RestaurantSubtitle");
        SpanLabel date = new SpanLabel();
        date.setText("Date : " + cmd.getDate().toString());
        date.setUIID("RestaurantSubtitle");

       
        SpanLabel prix = new SpanLabel();
        prix.setText("Prix Total : " + cmd.getTotal());
        prix.setUIID("RestaurantSubtitle");

        SpanLabel statut = new SpanLabel();
        statut.setText("Statut : " + cmd.getStatut());
        statut.setUIID("RestaurantSubtitle");
        SpanLabel meth = new SpanLabel();
        meth.setText("Méthode : " + cmd.getStatut());
        meth.setUIID("RestaurantSubtitle");

        Label tab = new Label();
        tab.setText("Details:");
        tab.setUIID("CatName");
        Container elements = new Container();
        elements.setLayout(BoxLayout.y());
        
        cmd.getElements()
                .stream()
                .forEach(e->{
                    String elementName="";
                    float price=0;
                    for(MenuElement x :menu){
                        if(x.getId()==e.getElement_id()){
                            elementName=x.getNom();
                            price = x.getPrix();
                            break;
                        }
                    }
                    Label element = new Label(e.getQuantite()+"x "+ elementName+"  "+price);
                    elements.add(element);
                });
       

        c.addAll(profilePicLabel, resto, date, prix, statut,meth, tab, elements);
        if ("En attente".equals(cmd.getStatut())) {
            Label change = new Label();
            change.setText("Annuler La Commande:");
            change.setUIID("CatName");
            Container btns = new Container();
            btns.setLayout(BoxLayout.x());
            Button Cancel = new Button();
            Cancel.setText("Annuler");
            Cancel.addActionListener((es) -> {
                cmd.setStatut("Annulé");
                CommandeService.getInstance().changerStatutCommande(cmd);
                new CommandeDetailsScreen(rs, cmd).show();
            });

            btns.addAll(Cancel);
            c.addAll(change, btns);
        }
        
        
        Button qrcodeBtn = new Button("QR CODE","adminBtn");
        qrcodeBtn.addActionListener(et->{
            QRCodeScreen q = new QRCodeScreen(rs, cmd);
            q.show();
        });
        c.add(qrcodeBtn);
        add(BorderLayout.centerAbsolute(c));

    }
;

}
