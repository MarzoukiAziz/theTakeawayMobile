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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;

public class BlogAdminScreen extends Form {

    public BlogAdminScreen(Resources res, Blog e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Blog");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new BlogsAdminScreen(res).show();
        });
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());

        EncodedImage enc = EncodedImage.createFromImage(res.getImage("hot-pot.png"), false);
        URLImage urlim = URLImage.createToStorage(enc, e.getImage(), CONSTANTS.IMAGEURL + e.getImage(), URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(urlim);

        SpanLabel titleBlog = new SpanLabel();
        titleBlog.setText("Titre : " + e.getTitle());
        titleBlog.setUIID("RestaurantTitle");

        SpanLabel contenuBlog = new SpanLabel();
        contenuBlog.setText(e.getContenu());
        contenuBlog.setUIID("RestaurantSubtitle");

        SpanLabel dateBlog = new SpanLabel();
        dateBlog.setText("Date : " + e.getDate());
        dateBlog.setUIID("RestaurantSubtitle");

        SpanLabel authorBlog = new SpanLabel();
        authorBlog.setText("Auteur : " + e.getAuthor_name());
        authorBlog.setUIID("RestaurantSubtitle");

        SpanLabel statut = new SpanLabel();
        statut.setText("Statut : " + e.getStatut());
        statut.setUIID("RestaurantSubtitle");

        Label changeStatut = new Label("Changer Statut : ", "adminBtn");
        Button open = new Button("Accepter");
        open.addActionListener(x -> {
            e.setStatut("Ouvert");
            BlogService.getInstance().changerStatutBlog(e);
            
            Dialog.show("Statut Mis A jour", "Statut Mis A jour avec succés !", "ok", null);
            new BlogAdminScreen(res, e).show();
        });
        Button refuse = new Button("Réfuser");
        refuse.addActionListener(x -> {
            e.setStatut("Ferme");
            BlogService.getInstance().changerStatutBlog(e);
            
            Dialog.show("Statut Mis A jour", "Statut Mis A jour avec succés !", "ok", null);
            new BlogAdminScreen(res, e).show();
        });

        Label supprimerLabel = new Label("Supprimer : ", "adminBtn");
        Button del = new Button("Supprimer");
        del.addActionListener(x -> {
            BlogService.getInstance().deleteBlog(e.getId());
            Dialog.show("Supprimé", "Le blog a été supprimer avec succés !", "ok", null);
            new BlogsAdminScreen(res).show();
        });

        c.addAll(imgv, titleBlog, dateBlog, authorBlog, contenuBlog, statut, changeStatut, open, refuse, supprimerLabel, del);
        add(c);

    }

}
