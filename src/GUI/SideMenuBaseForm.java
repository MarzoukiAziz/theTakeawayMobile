package GUI;

import GUI.Blog.BlogsScreen;
import GUI.Blog.SendMailsScreen;
import GUI.Reclamation.ReclamationScreen;
import GUI.Reclamation.ReclamationsScreen;
import GUI.USER.EditProfileScreen;
import GUI.USER.LoginScreen;
import GUI.Reservation.ChartsScreen;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);

        EncodedImage enc = EncodedImage.createFromImage(res.getImage("user.jpg"), false);
        URLImage urlim = URLImage.createToStorage(enc, Session.currentUser.getAvatar(), CONSTANTS.AVATARURL + Session.currentUser.getAvatar(), URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(urlim);
        Image profilePic = imgv.getImage();

        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("       " + Session.currentUser.getNom() + " " + Session.currentUser.getPrenom(), profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());
        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");
        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Dashboard", FontImage.MATERIAL_DASHBOARD, e -> new DashboardScreen(res).show());

        if (Session.currentUser.getRole().contains("ROLE_ADMIN")) {
            getToolbar().addMaterialCommandToSideMenu("  Admin Panel", FontImage.MATERIAL_SETTINGS, e -> new AdminPanelScreen(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Statistiques", FontImage.MATERIAL_PIE_CHART, e -> new ChartsScreen(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Notifier Les Clients", FontImage.MATERIAL_EMAIL, e -> new SendMailsScreen(res).show());
        }
        getToolbar().addMaterialCommandToSideMenu("  Twitter",
                FontImage.MATERIAL_GROUP,
                e -> {
                    Form hi = new Form("Suiver Nous Sur twitter", new BorderLayout());
                    hi.getToolbar().setBackCommand("Back", (ets) -> {
                        new DashboardScreen(res).show();
                    });
                    BrowserComponent browser = new BrowserComponent();
                    browser.setURL("https://twitter.com/OpenTable");
                    hi.add(BorderLayout.CENTER, browser);
                    hi.show();
                });
        getToolbar().addMaterialCommandToSideMenu("  Reclamations", FontImage.MATERIAL_MESSAGE, e -> {
            new ReclamationsScreen(res).show();
        });
        getToolbar().addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_FORUM, e -> {
            new BlogsScreen(res).show();
        });
        getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> {
            new EditProfileScreen(res).show();
        });
        getToolbar().addMaterialCommandToSideMenu("  Déconnexion", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            Session.currentUser = null;
            Dialog.show("Déconnexion", "Au Revoir!", "OK", null);
            new LoginScreen(res).show();
        });
    }

    protected abstract void showOtherForm(Resources res);
}
