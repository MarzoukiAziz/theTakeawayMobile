package GUI.RestoMenu;

import Entities.MenuElement;
import Entities.Restaurant;
import GUI.Commande.PanierScreen;
import GUI.DashboardScreen;
import Services.MenuService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.function.Function;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class MenuScreen extends Form {

    public MenuScreen(Resources res, Restaurant restaurant) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Menu");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(res).show();
        });

        tb.addMaterialCommandToOverflowMenu("Prix Déroissant", FontImage.MATERIAL_ARROW_DROP_DOWN,
                ev -> {
                    Session.MenuPrixCroissant = false;
                    new MenuScreen(res, restaurant).show();
                }
        );
        tb.addMaterialCommandToOverflowMenu("Prix Croissant", FontImage.MATERIAL_ARROW_DROP_UP,
                ev -> {
                    Session.MenuPrixCroissant = true;
                    new MenuScreen(res, restaurant).show();
                }
        );

        ArrayList<MenuElement> panier = new ArrayList<MenuElement>();
        Button panierBtn = new Button("Panier (" + panier.size() + ")", "adminBtn");
        panierBtn.addActionListener(l -> {
            new PanierScreen(res, panier, restaurant).show();
        });
        add(panierBtn);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("BOXES");
        categories.add("MEALS");
        categories.add("SANDWICHES");
        categories.add("SIDES");

        ArrayList<MenuElement> elements = MenuService.getInstance().getMenu();
        for (String cat : categories) {
            Label title = new Label();
            title.setText(cat);
            title.setUIID("CatName");
            add(title);
            elements.stream()
                    .filter((e) -> e.getCategorie().equals(cat))
                    .sorted((e1, e2) -> {
                        if (Session.MenuPrixCroissant) {
                            return (int) (e1.getPrix() - e2.getPrix());
                        }
                        return -1 * (int) (e1.getPrix() - e2.getPrix());
                    })
                    .map((MenuElement e) -> {
                        Container element = new Container();
                        element.setLayout(BoxLayout.x());

                        EncodedImage enc = EncodedImage.createFromImage(res.getImage("hot-pot.png"), false);
                        URLImage urlim = URLImage.createToStorage(enc, e.getImage(), CONSTANTS.IMAGEURL + e.getImage(), URLImage.RESIZE_SCALE);
                        ImageViewer imgv = new ImageViewer(urlim);
                        element.add(imgv);

                        Container data = new Container();
                        data.setLayout(BoxLayout.y());
                        Label l1 = new Label();
                        l1.setText(e.getNom());
                        SpanLabel l2 = new SpanLabel();
                        l2.setText(e.getDescription());
                        Label l4 = new Label();
                        l4.setText("Prix : " + e.getPrix() + "Dt");

                        Button addToPanier = new Button("Ajouter au Panier", FontImage.MATERIAL_SHOPPING_BASKET, "todayTitle");
                        addToPanier.addActionListener(xx -> {
                            panier.add(e);
                            panierBtn.setText("Panier (" + panier.size() + ")");
                        });

                        data.addAll(l1, l2, l4, addToPanier);
                        element.add(data);
                        return element;
                    }).forEachOrdered((element) -> {
                add(element);
            });
        }
    }
}
