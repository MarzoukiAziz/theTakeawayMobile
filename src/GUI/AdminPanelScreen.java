package GUI;

import GUI.RestoMenu.MenuAdminScreen;
import GUI.USER.UsersAdminScreen;
import GUI.Reservation.ReservationsSelectRestaurantAdminScreen;
import Entities.Restaurant;
import GUI.Blog.BlogsAdminScreen;
import GUI.Blog.BlogsScreen;
import GUI.Commande.CommandeSelectRestaurantAdminScreen;
import static GUI.DashboardScreen.keyword;
import GUI.Reclamation.ReclamationsAdminScreen;
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

/**
 *
 * @author marzo
 */
public class AdminPanelScreen extends SideMenuBaseForm {

    public AdminPanelScreen(Resources res) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Label title = new Label("Admin Panel", "CenterTitle");

        Container top = new Container();
        top.addAll(menuButton, title);
        tb.setTitleCentered(false);
        tb.setTitleComponent(top);
        Button users = new Button("Users", "adminBtn");
        users.addActionListener((e) -> {
            new UsersAdminScreen(res).show();
        });

        Button menu = new Button("Menu", "adminBtn");
        menu.addActionListener((e) -> {
            new MenuAdminScreen(res).show();
        });

        Button reclamations = new Button("Reclamations", "adminBtn");
        reclamations.addActionListener((e) -> {
            new ReclamationsAdminScreen(res).show();
        });
        Button reservations = new Button("Reservations", "adminBtn");
        reservations.addActionListener((e) -> {
            new ReservationsSelectRestaurantAdminScreen(res).show();
        });
        Button commandes = new Button("Commandes", "adminBtn");
        commandes.addActionListener((e) -> {
            new CommandeSelectRestaurantAdminScreen(res).show();
        });
        Button blogs = new Button("Blog", "adminBtn");
        blogs.addActionListener((e) -> {
            new BlogsAdminScreen(res).show();
        });
        
        
        add(BoxLayout.encloseY(users, menu, reclamations, reservations, commandes, blogs));

        setupSideMenu(res);
    }

    @Override
    protected void showOtherForm(Resources res) {
    }

}
