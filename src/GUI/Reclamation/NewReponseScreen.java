package GUI.Reclamation;

import Entities.Reclamation;
import GUI.Reclamation.ReclamationsScreen;
import Services.ReclamationService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;

public class NewReponseScreen extends Form {

    public NewReponseScreen(Resources res, Reclamation e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Nouveau Réponse");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new ReclamationScreen(res, e).show();
        });
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());

        TextField contenu = new TextField("", "Votre Réponse");
        Button addBtn = new Button("Répondre");
        addBtn.setUIID("adminBtn");
        addBtn.addActionListener(l -> {
            if (contenu.getText().length() < 3) {
                Dialog.show("Désolé", "Vérifier le contenu", "ok", null);
            } else {
                Boolean resultat = ReclamationService.getInstance().addReponse(e.getId(), contenu.getText());
                if (resultat) {
                    Dialog.show("Felicitations", "Votre réponse a été ajouté!", "ok", null);
                    new ReclamationScreen(res, e).show();
                } else {
                    Dialog.show("Désolé", "Vérifier le contenu", "ok", null);
                }
            }

        });
        addAll(contenu, addBtn);

    }

}
