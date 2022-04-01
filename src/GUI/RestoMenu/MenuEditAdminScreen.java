package GUI.RestoMenu;

import Entities.MenuElement;
import GUI.AdminPanelScreen;
import Services.MenuService;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;


public class MenuEditAdminScreen extends Form {

    public MenuEditAdminScreen(Resources res,MenuElement ele) {
     setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Menu Edit");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(res).show();
        });
        
        TextField nom =new TextField(ele.getNom(), "Nom");
        TextField description =new TextField(ele.getDescription(), "Description");
        TextField categorie = new TextField(ele.getCategorie(), "Categorie");
        TextField prix = new TextField(ele.getPrix()+"", "Prix");
        Button addBtn = new Button("Confirmer",FontImage.MATERIAL_ADD,"adminBtn");
        addBtn.addActionListener((ed)->{
            ele.setNom(nom.getText());
            ele.setDescription(description.getText());
            ele.setCategorie(categorie.getText());
            ele.setPrix(Float.parseFloat(prix.getText()));
            MenuService.getInstance().editMenu(ele);
            Dialog.show("Edit effectué", "Votre element a été edité avec succees", "ok", null);
new MenuAdminScreen(res).show();        });
        addAll(nom,description,categorie,prix,addBtn);
        
    }
    
}
