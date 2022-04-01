package GUI.Commande;

import Entities.Restaurant;
import Entities.Commande;
import Entities.MenuElement;
import Entities.Table;
import Entities.User;
import GUI.AdminPanelScreen;
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

public class CommandeDetailsAdminScreen extends Form {

    CommandeDetailsAdminScreen(Resources rs, Commande cmd, Restaurant r) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Commande n° " + cmd.getId());
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new CommandePerRestaurantAdminScreen(rs,r,"",null).show();
        });

        ArrayList<MenuElement> menu = MenuService.getInstance().getMenu();

        Container c = new Container();
        c.setLayout(BoxLayout.y());
        EncodedImage encProfile = EncodedImage.createFromImage(rs.getImage("hot-pot.png"), false);
        URLImage urlimProfile = URLImage.createToStorage(encProfile, r.getImages().get(0), CONSTANTS.AVATARURL + r.getImages().get(0), URLImage.RESIZE_SCALE_TO_FILL);
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

        Label client = new Label();
        client.setText("Client:");
        client.setUIID("CatName");

        Container data = new Container();
        data.setLayout(BoxLayout.y());
        ArrayList<User> ux = UserService.getInstance().getUsers();
        User u = new User();
        for (User i : ux) {
            if (i.getId() == cmd.getClient_id()) {
                u = i;
            }
        }
        SpanLabel nom = new SpanLabel();
        nom.setText("Nom : " + u.getNom());

        SpanLabel prenom = new SpanLabel();
        prenom.setText("Prenom : " + u.getPrenom());

        SpanLabel email = new SpanLabel();
        email.setText("Email : " + u.getEmail());

        SpanLabel tel = new SpanLabel();
        tel.setText("Téléphone : " + u.getNum_tel());
        data.addAll(nom, prenom, email, tel);

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
                .forEach(e -> {
                    String elementName = "";
                    float price = 0;
                    for (MenuElement x : menu) {
                        if (x.getId() == e.getElement_id()) {
                            elementName = x.getNom();
                            price = x.getPrix();
                            break;
                        }
                    }
                    Label element = new Label(e.getQuantite() + "x " + elementName + "  " + price);
                    elements.add(element);
                });

        c.addAll(profilePicLabel, resto, data, date, prix, statut, meth, tab, elements);
        Label change = new Label();
        change.setText("Changer la statut de la Commande:");
        change.setUIID("CatName");
        Container btns = new Container();
        btns.setLayout(BoxLayout.x());

        Button accept = new Button();
        accept.setText("Accepter");
        accept.addActionListener((es) -> {
            cmd.setStatut("Accepté");
            CommandeService.getInstance().changerStatutCommande(cmd);
            new CommandeDetailsAdminScreen(rs, cmd, r).show();
        });

        Button refuse = new Button();
        refuse.setText("Réfuser");
        refuse.addActionListener((es) -> {
            cmd.setStatut("Réfusé");
            CommandeService.getInstance().changerStatutCommande(cmd);
            new CommandeDetailsAdminScreen(rs, cmd, r).show();
        });

        btns.addAll(accept, refuse);
        c.addAll(change, btns);

        add(BorderLayout.centerAbsolute(c));

    }

}
