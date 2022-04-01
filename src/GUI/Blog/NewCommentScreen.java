package GUI.Blog;

import Entities.Blog;
import GUI.Blog.BlogsScreen;
import Services.BlogService;
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

public class NewCommentScreen extends Form {

    public NewCommentScreen(Resources res, Blog e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Nouveau Commentaire");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new BlogScreen(res, e).show();
        });
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());

        TextField contenu = new TextField("", "Votre Commentaire");
        Button addBtn = new Button("Publier");
        addBtn.setUIID("adminBtn");
        addBtn.addActionListener(l -> {
            if (contenu.getText().length() < 3) {
                Dialog.show("Désolé", "Vérifier le contenu", "ok", null);
            } else {
                Boolean resultat = BlogService.getInstance().addComment(e.getId(), contenu.getText());
                if (resultat) {
                    Dialog.show("Felicitations", "Votre Commentaire a été publié!", "ok", null);
                    new BlogScreen(res, e).show();
                } else {
                    Dialog.show("Désolé", "Vérifier le contenu", "ok", null);
                }
            }

        });
        addAll(contenu, addBtn);

    }

}
