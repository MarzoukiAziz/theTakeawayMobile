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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;

public class NewBlogScreen extends Form {

    public NewBlogScreen(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Nouveau Blog");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new BlogsScreen(res).show();
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
                Boolean resultat = BlogService.getInstance().addBlog(titre.getText(), contenu.getText());
                if (resultat) {
                    Dialog.show("Felicitations", "Votre Blog a été ajouté!", "ok", null);
                    new BlogsScreen(res).show();
                } else {
                    Dialog.show("Désolé", "Vérifier le sujet et le contenu", "ok", null);
                }
            }
        });
        addAll(tl, titre, cl, contenu, addBtn);

    }

}
