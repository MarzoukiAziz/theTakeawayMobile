package GUI.Commande;

import Entities.Commande;
import Entities.Restaurant;
import GUI.DashboardScreen;
import Services.CommandeService;
import Services.RestaurantService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.function.Predicate;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class CommandeScreen extends Form {

    public CommandeScreen(Resources rs, String filtrage,Date d) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Commandes");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(rs).show();
        });
        tb.addMaterialCommandToOverflowMenu("Tous", FontImage.MATERIAL_ALL_INCLUSIVE,
                ev -> {
                    new CommandeScreen(rs, "",null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Accepté", FontImage.MATERIAL_DONE,
                ev -> {
                    new CommandeScreen(rs, "Accepté",null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("En attente", FontImage.MATERIAL_TIMER,
                ev -> {
                    new CommandeScreen(rs, "En attente",null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("En traitement", FontImage.MATERIAL_HOT_TUB,
                ev -> {
                    new CommandeScreen(rs, "En traitement",null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Annulé", FontImage.MATERIAL_CANCEL,
                ev -> {
                    new CommandeScreen(rs, "Annulé",null).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Réfusé", FontImage.MATERIAL_REMOVE,
                ev -> {
                    new CommandeScreen(rs, "Réfusé",null).show();
                }
        );
        Label calLabel = new Label("Filtrer par date");
        Calendar cal = new Calendar();
        if(d!=null){cal.setDate(d);}
        cal.addActionListener(l->{
            java.util.Date utilStartDate = cal.getDate();
            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());    
            new CommandeScreen(rs, "", sqlStartDate).show();
        });
        addAll(calLabel,cal);
        ArrayList<Commande> rev = CommandeService.getInstance().getcommandes();
        ArrayList<Restaurant> res = RestaurantService.getInstance().getRestaurants();
        rev.stream()
                .filter((Commande e) -> {
                    if ("".equals(filtrage)) {
                        return e.getClient_id() == Session.currentUser.getId();
                    } else {
                        return e.getClient_id() == Session.currentUser.getId() && (e.getStatut() == null ? filtrage == null : e.getStatut().equals(filtrage));
                    }
                })
                .filter((Commande e) -> {
                    if (d!=null) {
                        System.out.println(e.getDate()+"**"+d);
                       return  e.getDate().toString().equals(d.toString());
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
                    SpanLabel resto = new SpanLabel();
                    resto.setText("Restaurant : " + r.getNom());

                    SpanLabel date = new SpanLabel();
                    date.setText("Date : " + u.getDate().toString());

                    SpanLabel pour = new SpanLabel();
                    pour.setText("Total : " + u.getTotal()+"Dt");

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
