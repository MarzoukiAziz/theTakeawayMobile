package GUI.Reclamation;

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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;

public class NewReclamationScreen extends Form {

    public NewReclamationScreen(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Nouveau Reclamation");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new ReclamationsScreen(res).show();
        });
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());
        
        
        Label tl = new Label("Sujet : ");
        TextField titre = new TextField("", "Sujet");
        
        
        Label cl = new Label("Contenu : ");
        TextArea contenu = new TextArea();
        
        
        Button addBtn = new Button("Publier");
        addBtn.setUIID("adminBtn");
        addBtn.addActionListener(l -> {
            if (titre.getText().length() < 3 || contenu.getText().length() < 3) {
                Dialog.show("Désolé", "Vérifier le sujet et le contenu", "ok", null);
            } else {
                Boolean resultat = ReclamationService.getInstance().addReclamation(titre.getText(), contenu.getText());
                if (resultat) {
                    Dialog.show("Felicitations", "Votre Reclamation a été ajouté!", "ok", null);
                    new ReclamationsScreen(res).show();
                } else {
                    Dialog.show("Désolé", "Vérifier le sujet et le contenu", "ok", null);
                }
            }
        });
        addAll(tl, titre, cl, contenu, addBtn);

    }

}
