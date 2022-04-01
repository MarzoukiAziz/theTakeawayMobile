package GUI.Commande;

import Entities.Commande;
import Entities.Restaurant;
import GUI.AdminPanelScreen;
import Services.CommandeService;
import Services.RestaurantService;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.sql.Date;
import java.util.ArrayList;

public class CommandePerRestaurantAdminScreen extends Form {

    public CommandePerRestaurantAdminScreen(Resources rs, Restaurant restaurant, String filtrage,Date d) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reservations " + restaurant.getNom());
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(rs).show();
        });
        tb.addMaterialCommandToOverflowMenu("Tous", FontImage.MATERIAL_ALL_INCLUSIVE,
                ev -> {
                    new CommandePerRestaurantAdminScreen(rs,restaurant, "", null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Accepté", FontImage.MATERIAL_DONE,
                ev -> {
                    new CommandePerRestaurantAdminScreen(rs,restaurant, "Accepté", null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("En attente", FontImage.MATERIAL_TIMER,
                ev -> {
                    new CommandePerRestaurantAdminScreen(rs,restaurant, "En attente", null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("En traitement", FontImage.MATERIAL_HOT_TUB,
                ev -> {
                    new CommandePerRestaurantAdminScreen(rs,restaurant, "En traitement", null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Annulé", FontImage.MATERIAL_CANCEL,
                ev -> {
                    new CommandePerRestaurantAdminScreen(rs,restaurant, "Annulé", null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Réfusé", FontImage.MATERIAL_REMOVE,
                ev -> {
                    new CommandePerRestaurantAdminScreen(rs,restaurant, "Réfusé", null).show();
                }
        );
        Label calLabel = new Label("Filtrer par date");
        Calendar cal = new Calendar();
        if (d != null) {
            cal.setDate(d);
        }
        cal.addActionListener(l -> {
            java.util.Date utilStartDate = cal.getDate();
            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            new CommandePerRestaurantAdminScreen(rs,restaurant, "", sqlStartDate).show();
        });
        addAll(calLabel, cal);
        ArrayList<Commande> rev = CommandeService.getInstance().getcommandes();
        ArrayList<Restaurant> res = RestaurantService.getInstance().getRestaurants();
        rev.stream()
                .filter((Commande e) -> {
                    if ("".equals(filtrage)) {
                        return e.getRestaurant_id()== restaurant.getId();
                    } else {
                        return  e.getRestaurant_id()== restaurant.getId() && (e.getStatut() == null ? filtrage == null : e.getStatut().equals(filtrage));
                    }
                })
                .filter((Commande e) -> {
                    if (d != null) {
                        return e.getDate().toString().equals(d.toString());
                    }
                    return true;
                })
                .map((Commande u) -> {
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

                    SpanLabel date = new SpanLabel();
                    date.setText("Date : " + u.getDate().toString());

                    SpanLabel total = new SpanLabel();
                    total.setText("Total : " + u.getTotal() + "Dt");

                    SpanLabel statut = new SpanLabel();
                    statut.setText("Statut : " + u.getStatut());
                    Button details = new Button("Details", "editBtn");
                    details.addActionListener((ed)
                            -> {
                        new CommandeDetailsAdminScreen(rs, u,restaurant).show();
                    });

                    data.addAll(date, total, statut, details);
                    return data;
                }).forEachOrdered((element) -> {
            add(element);
        });

    }

}
