package GUI.RestoMenu;

import GUI.Reservation.NewReservationScreen;
import Entities.Restaurant;
import GUI.DashboardScreen;
import GUI.SideMenuBaseForm;
import Services.RestaurantService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import kraya.moazmar.com.CONSTANTS;

/**
 *
 * @author marzo
 */
public class RestaurantScreen extends SideMenuBaseForm {

    public RestaurantScreen(Resources res, Restaurant e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle(e.getNom());tb.setTitleCentered(true);
        tb.setBackCommand("Back",(ets)->{new DashboardScreen(res).show();});
        
        
        
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());
        
        EncodedImage enc = EncodedImage.createFromImage(res.getImage("hot-pot.png"), false);
        URLImage urlim = URLImage.createToStorage(enc, e.getImages().get(0), CONSTANTS.IMAGEURL + e.getImages().get(0), URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(urlim);

        SpanLabel adresse = new SpanLabel();
        adresse.setText("Adresse : " + e.getAdresse());
        adresse.setUIID("RestaurantSubtitle");
        
        SpanLabel description = new SpanLabel();
        description.setText("Description : " + e.getDescription());
        description.setUIID("RestaurantSubtitle");
        
        SpanLabel horaire = new SpanLabel();
        horaire.setText("Horaire : " + e.getHeure_ouverture().substring(11, 16) + " - " + e.getHeure_fermeture().substring(11, 16));
        horaire.setUIID("RestaurantSubtitle");
        
        SpanLabel tel = new SpanLabel();
        tel.setText("Telephone : " + e.getTelephone());
        tel.setUIID("RestaurantSubtitle");
        
        Container btns = new Container();
        btns.setLayout(BoxLayout.x());
        
        Button menu = new Button();
        menu.setText("Menu");
        menu.addActionListener((es)->{
            MenuScreen ms = new MenuScreen(res,e);
            ms.show();
        });
        
        Button reserve = new Button();
        reserve.setText("Reserver");
        reserve.addActionListener((es)->{
            NewReservationScreen rv = new NewReservationScreen(res,e);
            rv.show();
        });
        
        Label gallerie = new Label();
        gallerie.setText("Gallerie :");
        
        btns.addAll(menu,reserve);
        
        Container imgs = new Container(new FlowLayout());
        for (String img : e.getImages()) {
            URLImage urlim2 = URLImage.createToStorage(enc, img, CONSTANTS.IMAGEURL + img, URLImage.RESIZE_SCALE);
            imgs.add(new ImageViewer(urlim2));
        }
        
        c.addAll(imgv, adresse, description, horaire, tel,btns, gallerie,imgs);
        add(c);
        
        add(new MapForm(e.getX(),e.getY()).f);

    }

    @Override
    protected void showOtherForm(Resources res) {

    }

}
