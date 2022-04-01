package GUI;

import GUI.RestoMenu.RestaurantScreen;
import GUI.USER.MyProfileScreen;
import GUI.Reservation.ReservationsScreen;
import Entities.Restaurant;
import GUI.Commande.CommandeScreen;
import Services.RestaurantService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class DashboardScreen extends SideMenuBaseForm {

    static String keyword = "";

    public DashboardScreen(Resources res) {
        super(BoxLayout.y());
        EncodedImage encProfile = EncodedImage.createFromImage(res.getImage("user.jpg"), false);
        URLImage urlimProfile = URLImage.createToStorage(encProfile, Session.currentUser.getAvatar(), CONSTANTS.AVATARURL + Session.currentUser.getAvatar() , URLImage.RESIZE_SCALE);
        ImageViewer imgvProfile = new ImageViewer(urlimProfile);
        Toolbar tb = getToolbar();
        tb.setUIID("dashboardToolbar");
        tb.setTitleCentered(false);
        Image profilePic =imgvProfile.getImage();
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Button reservationsBtn =new Button("Réservations", "CenterTitle");
        reservationsBtn.addActionListener((e)->{
            new ReservationsScreen(res, "").show();
        });
        Button cmdsBtn =new Button("Commandes", "CenterTitle");
        cmdsBtn.addActionListener((e)->{
            new CommandeScreen(res, "",null).show();
        });
        Container remainingTasks = BoxLayout.encloseY(
                reservationsBtn
        );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                cmdsBtn
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label(Session.currentUser.getNom()+" "+Session.currentUser.getPrenom(), "USERNAME")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ACCOUNT_CIRCLE);
        fab.addActionListener((e) -> {
            new MyProfileScreen(res).show();
        });
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        add(new Label("Découvrir Nos Restaurants", "TodayTitle"));
        
        
        Container recherche = new Container();
        recherche.setLayout(BoxLayout.x());
        
        TextField tf = new TextField();
        tf.setText(keyword);
        tf.setWidth((int) (getWidth() * 0.5));
        tf.setHint("Rechercher un resto");
        
        Button btn = new Button(FontImage.MATERIAL_SEARCH);
        btn.addActionListener((e) -> {
            keyword = tf.getText();
            new DashboardScreen(res).show();
        });
        recherche.addAll(tf, btn);
        add(recherche);

        ArrayList<Restaurant> elements = RestaurantService.getInstance().getRestaurants();
        elements.stream()
                .filter((e) -> e.getNom().toUpperCase().contains(keyword.toUpperCase()))
                .map((e) -> {
                    
                    Container c = new Container();
                    c.setUIID("RestaurantContainer");
                    c.setLayout(BoxLayout.x());
                    
                    EncodedImage enc = EncodedImage.createFromImage(res.getImage("hot-pot.png"), false);
                    URLImage urlim = URLImage.createToStorage(enc, e.getImages().get(0), CONSTANTS.IMAGEURL + e.getImages().get(0), URLImage.RESIZE_SCALE);
                    ImageViewer imgv = new ImageViewer(urlim);
                    c.add(imgv);
                    
                    Container inner = new Container();
                    inner.setLayout(BoxLayout.y());
                    
                    Label title = new Label();
                    title.setText(e.getNom());
                    title.setUIID("RestaurantTitle");
                    
                    SpanLabel adresse = new SpanLabel();
                    adresse.setText(e.getAdresse());
                    adresse.setUIID("RestaurantSubtitle");
                    
                    Button select = new Button();
                    select.setText("Plus de details");
                    select.addActionListener((action) -> {
                        RestaurantScreen rs = new RestaurantScreen(res, e);
                        rs.show();

                    });
                    inner.addAll(title, adresse, select);
                    c.add(inner);
                    return c;
                }).forEachOrdered((c) -> {
            add(c);
        });
        
        setupSideMenu(res);
        keyword = "";

    }

    @Override
    protected void showOtherForm(Resources res) {

    }

}
