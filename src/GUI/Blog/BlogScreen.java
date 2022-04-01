package GUI.Blog;

import Entities.Blog;
import GUI.Blog.BlogsScreen;
import Services.BlogService;
import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.OutputStream;
import kraya.moazmar.com.CONSTANTS;
import com.codename1.ui.Image;

public class BlogScreen extends Form {

    public BlogScreen(Resources res, Blog e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Blog");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new BlogsScreen(res).show();
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
        //Service

        ////
        Label commentairesLabel = new Label("Commentaires : ", "adminBtn");
        Button addBtn = new Button("Nouveau Commentaire");
        addBtn.addActionListener(x -> {
            new NewCommentScreen(res, e).show();
        });
        c.addAll(imgv, titleBlog, dateBlog, authorBlog, contenuBlog, commentairesLabel, addBtn);
        add(c);
        BlogService.getInstance().getComments(e).stream().forEach(com -> {
            Container con = new Container();
            con.setLayout(BoxLayout.y());
            con.setUIID("commentaire");
            Label aname = new Label("Auteur : " + com.getAuthor_name());
            Label date = new Label("Date : " + com.getDate());
            SpanLabel contenu = new SpanLabel(com.getContenu());
            con.addAll(aname, date, contenu);
            add(con);
        });
        ////Share Service//////
        ShareButton sb = new ShareButton();
        sb.setText("Share Screenshot");
        Image screenshot = Image.createImage(this.getWidth(), this.getHeight());
        c.revalidate();
        c.paintComponent(screenshot.getGraphics(), true);
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            System.out.println(err);
        }
        add(sb);
        //////
        sb.setImageToShare(imageFile, "image/png");
    }

}
