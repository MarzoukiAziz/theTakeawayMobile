
package GUI.RestoMenu;

import Entities.MenuElement;
import GUI.AdminPanelScreen;
import Services.MenuService;
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
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class MenuAdminScreen extends Form {
    public MenuAdminScreen(Resources res) {
        
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Menu Admin");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(res).show();
        });
        
        tb.addMaterialCommandToOverflowMenu("Add", FontImage.MATERIAL_ADD,
                ev -> {
                    new MenuAddAdminScreen(res).show();
                }
        );
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
                    .filter((e)->e.getCategorie().equals(cat))
                    .sorted((e1,e2)->{
                        if(Session.MenuPrixCroissant){
                            return (int)(e1.getPrix()-e2.getPrix());
                        }
                        return -1*(int)(e1.getPrix()-e2.getPrix());
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
                        l4.setText("Prix : " + e.getPrix()+"Dt");
                        Button edit = new Button("Edit","editBtn");
                        edit.addActionListener((ed)->{
                           MenuEditAdminScreen mea = new MenuEditAdminScreen(res, e);
                           mea.show();
                        });
                        Button delete = new Button("Delete","delBtn");
                        delete.addActionListener((ed)->
                            {
                        MenuService.getInstance().deleteMenu(e.getId());
                                Dialog.show("Suppression effectué", "Votre annonce a été supprimé avec succees", "ok", null);
                        new MenuAdminScreen(res).show();

                    
                        });
                        data.addAll(l1, l2, l4,edit ,delete);
                        element.add(data);
                        return element;
            }).forEachOrdered((element) -> {
                add(element);
            });
        }
    }
}
