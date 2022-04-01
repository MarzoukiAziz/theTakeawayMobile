package GUI.Commande;

import Entities.MenuElement;
import Entities.Restaurant;
import Entities.Commande;
import Entities.CommandeElement;
import GUI.DashboardScreen;
import GUI.RestoMenu.MenuScreen;
import Services.CommandeService;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.HashMap;
import kraya.moazmar.com.Session;

public class PanierScreen extends Form {

    public PanierScreen(Resources res, ArrayList<MenuElement> panier,Restaurant restaurant) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Panier");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new MenuScreen(res,restaurant).show();
        });
        HashMap<MenuElement, Integer> quantities = new HashMap<MenuElement, Integer>();
        Label prixTotal = new Label("Prix Total: " + calculePrixTotale(quantities));

        panier.stream().forEach(e -> {
            quantities.put(e, 1);
            Container element = new Container();
            element.setLayout(BoxLayout.x());
            Label ename = new Label(e.getNom());
            Label quan = new Label("x" + quantities.get(e));
            Button addBtn = new Button(FontImage.MATERIAL_ADD);
            addBtn.addActionListener(x -> {
                quantities.put(e, quantities.get(e) + 1);
                quan.setText("x" + quantities.get(e));
                prixTotal.setText("Prix Total: " + calculePrixTotale(quantities));
            });
            Button subBtn = new Button(FontImage.MATERIAL_CANCEL);
            subBtn.addActionListener(x -> {
                if (quantities.get(e) > 0) {
                    quantities.put(e, quantities.get(e) - 1);
                    quan.setText("x" + quantities.get(e));
                    prixTotal.setText("Prix Total: " + calculePrixTotale(quantities));
                }
            });

            element.addAll(addBtn, quan, subBtn);
            addAll(ename, element);
        });
        SpanLabel cash = new SpanLabel("Seulement paiement en cash est disponible dans cette version");
        Button commander = new Button("Commander", "adminBtn");
        commander.addActionListener(x -> {
            Commande cmd = new Commande();
            cmd.setClient_id(Session.currentUser.getId());
            cmd.setRestaurant_id(restaurant.getId());
            cmd.setTotal((float)calculePrixTotale(quantities));
            for(MenuElement mm: panier){
                CommandeElement ce = new CommandeElement();
                ce.setElement_id(mm.getId());
                ce.setQuantite(quantities.get(mm));
                cmd.addElement(ce);
            }
            CommandeService.getInstance().commander(cmd, res);
        });
        prixTotal.setText("Prix Total: " + calculePrixTotale(quantities));
        addAll(cash, prixTotal, commander);
    }

    double calculePrixTotale(HashMap<MenuElement, Integer> panier) {
        double p =  panier.keySet().stream()
                .mapToDouble(x -> {
                    return (panier.get(x) * x.getPrix());
                }).sum();
        return (double)Math.round(p * 10d) / 10d;
    }
}
