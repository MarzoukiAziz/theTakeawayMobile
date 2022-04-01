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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;

public class MenuAddAdminScreen extends Form {

    MenuAddAdminScreen(Resources res) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Menu Add");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(res).show();
        });

        TextField nom = new TextField("", "Nom");
        TextField description = new TextField("", "Description");
        TextField categorie = new TextField("", "Categorie");
        TextField prix = new TextField("", "Prix");
        Button addBtn = new Button("Add", FontImage.MATERIAL_ADD, "adminBtn");
        addBtn.addActionListener((ed) -> {
            MenuElement e = new MenuElement();
            e.setNom(nom.getText());
            e.setDescription(description.getText());
            e.setCategorie(categorie.getText());
            e.setPrix(Float.parseFloat(prix.getText()));
            MenuService.getInstance().addMenu(e);
            Dialog.show("Ajout effectué", "Votre nouveau element a été ajouté avec succees", "ok", null);
            new MenuAdminScreen(res).show();
        });
        addAll(nom, description, categorie, prix, addBtn);

    }
}
