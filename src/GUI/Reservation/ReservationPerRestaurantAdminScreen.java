package GUI.Reservation;

import Entities.MenuElement;
import Entities.Restaurant;
import Entities.Reservation;
import Entities.Table;
import GUI.AdminPanelScreen;
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

public class ReservationPerRestaurantAdminScreen extends Form {

    public ReservationPerRestaurantAdminScreen(Resources rs, Restaurant restaurant, String filtrage) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reservations " + restaurant.getNom());
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(rs).show();
        });
        tb.addMaterialCommandToOverflowMenu("Tous", FontImage.MATERIAL_ALL_INCLUSIVE,
                ev -> {
                    new ReservationPerRestaurantAdminScreen(rs, restaurant, "").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Accepté", FontImage.MATERIAL_DONE,
                ev -> {
                    new ReservationPerRestaurantAdminScreen(rs, restaurant, "Accepté").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("En Attente", FontImage.MATERIAL_TIMER,
                ev -> {
                    new ReservationPerRestaurantAdminScreen(rs, restaurant, "En Attente").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Annulé", FontImage.MATERIAL_CANCEL,
                ev -> {
                    new ReservationPerRestaurantAdminScreen(rs, restaurant, "Annulé").show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Réfusé", FontImage.MATERIAL_REMOVE,
                ev -> {
                    new ReservationPerRestaurantAdminScreen(rs, restaurant, "Réfusé").show();
                }
        );

        ArrayList<Reservation> rev = ReservationService.getInstance().getReservations();
        rev.stream()
                .filter((Reservation e) -> {
                    if ("".equals(filtrage)) {
                        return e.getRestaurant_id() == restaurant.getId();
                    }else{
                        return e.getRestaurant_id() == restaurant.getId() && (e.getStatut() == null ? filtrage == null : e.getStatut().equals(filtrage));
                    }
        })
                .map((Reservation u) -> {

                    Container data = new Container();
                    data.setUIID("revc");
                    data.setLayout(BoxLayout.y());
                    SpanLabel id = new SpanLabel();
                    id.setText("Ref : " + u.getId());

                    SpanLabel date = new SpanLabel();
                    date.setText("Date : " + u.getDate().toString());

                    SpanLabel pour = new SpanLabel();
                    pour.setText("Pour : " + u.getNb_personne());

                    SpanLabel statut = new SpanLabel();
                    statut.setText("Statut : " + u.getStatut());
                    Button details = new Button("Details", "editBtn");
                    details.addActionListener((ed)
                            -> {
                        new ReservationDetailsAdminScreen(rs, u, restaurant).show();
                    });

                    data.addAll(id, date, pour, statut, details);
                    return data;
                }).forEachOrdered((element) -> {
            add(element);
        });

    }

}
