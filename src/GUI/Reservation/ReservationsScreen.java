package GUI.Reservation;

import Entities.MenuElement;
import Entities.Restaurant;
import Entities.Reservation;
import Entities.Table;
import GUI.DashboardScreen;
import Services.MenuService;
import Services.ReservationService;
import Services.RestaurantService;
import Services.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.function.Predicate;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class ReservationsScreen extends Form {

    public ReservationsScreen(Resources rs, String filtrage) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reservations");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(rs).show();
        });
        tb.addMaterialCommandToOverflowMenu("Tous", FontImage.MATERIAL_ALL_INCLUSIVE,
                ev -> {
                    new ReservationsScreen(rs, "").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Accepté", FontImage.MATERIAL_DONE,
                ev -> {
                    new ReservationsScreen(rs, "Accepté").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("En Attente", FontImage.MATERIAL_TIMER,
                ev -> {
                    new ReservationsScreen(rs, "En Attente").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Annulé", FontImage.MATERIAL_CANCEL,
                ev -> {
                    new ReservationsScreen(rs, "Annulé").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Réfusé", FontImage.MATERIAL_REMOVE,
                ev -> {
                    new ReservationsScreen(rs, "Réfusé").show();
                }
        );

        ArrayList<Reservation> rev = ReservationService.getInstance().getReservations();
        ArrayList<Restaurant> res = RestaurantService.getInstance().getRestaurants();
        rev.stream()
                .filter((Reservation e) -> {
                    if ("".equals(filtrage)) {
                        return e.getClient_id() == Session.currentUser.getId();
                    } else {
                        return e.getClient_id() == Session.currentUser.getId() && (e.getStatut() == null ? filtrage == null : e.getStatut().equals(filtrage));
                    }
                })
                .map((Reservation u) -> {
                    Restaurant r = new Restaurant();
                    for (Restaurant x : res) {
                        if (x.getId() == u.getRestaurant_id()) {
                            r = x;
                            break;
                        }
                    }
                    Container data = new Container();
                    data.setUIID("revc");
                    data.setLayout(BoxLayout.y());
                    SpanLabel resto = new SpanLabel();
                    resto.setText("Restaurant : " + r.getNom());

                    SpanLabel date = new SpanLabel();
                    date.setText("Date : " + u.getDate().toString());

                    SpanLabel pour = new SpanLabel();
                    pour.setText("Pour : " + u.getNb_personne());

                    SpanLabel statut = new SpanLabel();
                    statut.setText("Statut : " + u.getStatut());
                    Button details = new Button("Details", "editBtn");
                    details.addActionListener((ed)
                            -> {
                        new CommandeDetailsScreen(rs, u).show();
                    });

                    data.addAll(resto, date, pour, statut, details);
                    return data;
                }).forEachOrdered((element) -> {
            add(element);
        });

    }

}
