package GUI.Reservation;

import Entities.Restaurant;
import Entities.Reservation;
import Entities.Table;
import Entities.User;
import GUI.AdminPanelScreen;
import Services.ReservationService;
import Services.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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

public class ReservationDetailsAdminScreen extends Form {

    ReservationDetailsAdminScreen(Resources rs, Reservation rev, Restaurant res) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reservation n° " + rev.getId());
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(rs).show();
        });
        Container c = new Container();
        c.setLayout(BoxLayout.y());
        EncodedImage encProfile = EncodedImage.createFromImage(rs.getImage("hot-pot.png"), false);
        URLImage urlimProfile = URLImage.createToStorage(encProfile, res.getImages(), CONSTANTS.AVATARURL + res.getImages(), URLImage.RESIZE_SCALE_TO_FILL);
        ImageViewer imgvProfile = new ImageViewer(urlimProfile);
        Image profilePic = imgvProfile.getImage();
        Image mask = rs.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        SpanLabel date = new SpanLabel();
        date.setText("Date : " + rev.getDate().toString());
        date.setUIID("RestaurantSubtitle");

        SpanLabel horaire = new SpanLabel();
        horaire.setText("Horaire  : " + rev.getHeure_arrive().toString().substring(0, 5) + " - " + rev.getHeure_depart().toString().substring(0, 5));
        horaire.setUIID("RestaurantSubtitle");

        SpanLabel nbp = new SpanLabel();
        nbp.setText("Nombre de Personnes : " + rev.getNb_personne());
        nbp.setUIID("RestaurantSubtitle");

        SpanLabel statut = new SpanLabel();
        statut.setText("Statut : " + rev.getStatut());
        statut.setUIID("RestaurantSubtitle");

        Label client = new Label();
        client.setText("Client:");
        client.setUIID("CatName");

        Container data = new Container();
        data.setLayout(BoxLayout.y());
        ArrayList<User> ux = UserService.getInstance().getUsers();
        User u = new User();
        for (User i : ux) {
            if (i.getId() == rev.getClient_id()) {
                u = i;
            }
        }
        SpanLabel id = new SpanLabel();
        id.setText("Ref : " + u.getId());

        SpanLabel nom = new SpanLabel();
        nom.setText("Nom : " + u.getNom());

        SpanLabel prenom = new SpanLabel();
        prenom.setText("Prenom : " + u.getPrenom());

        SpanLabel email = new SpanLabel();
        email.setText("Email : " + u.getEmail());

        SpanLabel tel = new SpanLabel();
        tel.setText("Téléphone : " + u.getNum_tel());
        data.addAll(id, nom, prenom, email, tel);
        Label tab = new Label();
        tab.setText("Tables:");
        tab.setUIID("CatName");
        Container tabs = new Container();

        for (Table x : rev.getTables()) {
            tabs.add(new Label("Table N°" + x.getNumero() + " (" + x.getNbPlaces() + ") "));
        }

        c.addAll(profilePicLabel, date, horaire, nbp, statut, client, data, tab, tabs);
        System.out.println(rev.getStatut());
        if ("En Attente".equals(rev.getStatut())) {
            Label change = new Label();
            change.setText("Change Le Statut:");
            change.setUIID("CatName");
            Container btns = new Container();
            btns.setLayout(BoxLayout.x());
            Button Accepter = new Button();
            Accepter.setText("Accepter");
            Accepter.addActionListener((es) -> {
                rev.setStatut("Accepté");
                ReservationService.getInstance().changerStatutReservation(rev);
                new ReservationDetailsAdminScreen(rs,rev,res).show();
                Dialog.show("Statut Mis A jour", "Statut Mis A jour avec succés !", "ok",null);
            });
            Button Refuser = new Button();
            Refuser.setText("Refuser");
            Refuser.addActionListener((es) -> {
                rev.setStatut("Réfusé");
                ReservationService.getInstance().changerStatutReservation(rev);
                new ReservationDetailsAdminScreen(rs,rev,res).show();
                Dialog.show("Statut Mis A jour", "Statut Mis A jour avec succés !", "ok",null);
            });
            btns.addAll(Accepter, Refuser);
            c.addAll(change, btns);
        }
        add(BorderLayout.centerAbsolute(c));

    }
;

}
