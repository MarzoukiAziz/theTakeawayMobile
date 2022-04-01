
package GUI.Blog;

import Entities.Blog;
import GUI.AdminPanelScreen;
import static GUI.Blog.BlogsScreen.keyword;
import Services.BlogService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import kraya.moazmar.com.CONSTANTS;


public class BlogsAdminScreen extends Form {
    
    public BlogsAdminScreen(Resources res) {
        super(BoxLayout.y());
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Blogs " );
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(res).show();
        });
       
        ArrayList<Blog> blogs = BlogService.getInstance().getBlogs();
        System.out.println(blogs);
        blogs.stream()
                .map((e) -> {
                    Container c = new Container();
                    c.setUIID("RestaurantContainer");
                    c.setLayout(BoxLayout.x());
                    EncodedImage enc = EncodedImage.createFromImage(res.getImage("hot-pot.png"), false);
                    URLImage urlim = URLImage.createToStorage(enc, e.getImage(), CONSTANTS.IMAGEURL + e.getImage(), URLImage.RESIZE_SCALE);
                    ImageViewer imgv = new ImageViewer(urlim);
                    c.add(imgv);
                    Container inner = new Container();
                    inner.setLayout(BoxLayout.y());
                    SpanLabel title = new SpanLabel();
                    title.setText(e.getTitle());
                    Label statut = new Label("Statut : "+e.getStatut());
                    Button select = new Button();
                    select.setText("Consulter");
                    select.addActionListener((action) -> {
                        new BlogAdminScreen(res, e).show();

                    });
                    inner.addAll(title,statut, select);
                    c.add(inner);
                    return c;
                }).forEachOrdered((c) -> {
            add(c);
        });

    }
}
