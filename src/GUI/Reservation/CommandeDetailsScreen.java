package GUI.Reservation;

import GUI.Reservation.ReservationsScreen;
import Entities.Restaurant;
import Entities.Reservation;
import Entities.Table;
import Entities.User;
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

    CommandeDetailsScreen(Resources rs, Reservation rev) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reservation n° " + rev.getId());
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new ReservationsScreen(rs, "").show();
        });

        LocalNotification n = new LocalNotification();
        n.setId("demo-notification");
        n.setAlertBody("It's time to take a break and look at me");
        n.setAlertTitle("Break Time!");
        n.setAlertSound("/notification_sound_beep-01a.mp3");
        // alert sound file name must begin with notification_sound

        Display.getInstance().scheduleLocalNotification(
                n,
                System.currentTimeMillis() + 10 , // fire date/time
                LocalNotification.REPEAT_NONE // Whether to repeat and what frequency
        );
        
        
        
        
        ArrayList<Restaurant> res = RestaurantService.getInstance().getRestaurants();
        Restaurant r = new Restaurant();
        for (Restaurant x : res) {
            if (x.getId() == rev.getRestaurant_id()) {
                r = x;
                break;
            }
        }
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
        resto.setText("Restaurant : " + r.getNom());
        resto.setUIID("RestaurantSubtitle");
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

        Label tab = new Label();
        tab.setText("Tables:");
        tab.setUIID("CatName");
        Container tabs = new Container();

        for (Table x : rev.getTables()) {
            tabs.add(new Label("Table N°" + x.getNumero() + " (" + x.getNbPlaces() + ") "));
        }

        c.addAll(profilePicLabel, resto, date, horaire, nbp, statut, tab, tabs);
        System.out.println(rev.getStatut());
        if ("En Attente".equals(rev.getStatut())) {
            Label change = new Label();
            change.setText("Annuler La Réservation:");
            change.setUIID("CatName");
            Container btns = new Container();
            btns.setLayout(BoxLayout.x());
            Button Cancel = new Button();
            Cancel.setText("Annulé");
            Cancel.addActionListener((es) -> {
                rev.setStatut("Annulé");
                ReservationService.getInstance().changerStatutReservation(rev);
                new CommandeDetailsScreen(rs, rev).show();
            });

            btns.addAll(Cancel);
            c.addAll(change, btns);
        }
        add(BorderLayout.centerAbsolute(c));

    }
;

}
