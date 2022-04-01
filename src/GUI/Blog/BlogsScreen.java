
package GUI.Blog;

import Entities.Blog;
import GUI.Commande.CommandeScreen;
import GUI.SideMenuBaseForm;
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
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;


public class BlogsScreen extends SideMenuBaseForm {

    static String keyword = "";

    public BlogsScreen(Resources res) {
        super(BoxLayout.y());
        
        ImageViewer imgvProfile = new ImageViewer(res.getImage("blogicon.png"));
        Toolbar tb = getToolbar();
        tb.setUIID("blogsToolbar");
        tb.setTitleCentered(false);
        Image profilePic =imgvProfile.getImage();
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        
        

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.east(
                        BoxLayout.encloseY(
                                new Label("The TakeAway Blog   ", "USERNAME")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
                
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener((e) -> {
           new NewBlogScreen(res).show();
        });
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        add(new Label("Découvrir les blogs de nos clients", "TodayTitle"));
        
        
        Container recherche = new Container();
        recherche.setLayout(BoxLayout.x());
        TextField tf = new TextField();
        tf.setText(keyword);
        tf.setHint("Rechercher ");
        
        Button btn = new Button(FontImage.MATERIAL_SEARCH);
        btn.addActionListener((e) -> {
            keyword = tf.getText();
           new BlogsScreen(res).show();
        });
        recherche.addAll(tf, btn);
        add(recherche);

        ArrayList<Blog> blogs = BlogService.getInstance().getBlogs();
        System.out.println(blogs);
        blogs.stream()
                .filter((e) ->(e.getStatut().equals("Ouvert"))&&(e.getTitle().toUpperCase().contains(keyword.toUpperCase())|| e.getContenu().toUpperCase().contains(keyword.toUpperCase())))
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
                    
                    Button select = new Button();
                    select.setText("Consulter");
                    
                    select.addActionListener((action) -> {
                        new BlogScreen(res, e).show();

                    });
                    inner.addAll(title, select);
                    c.add(inner);
                    return c;
                })
                .forEachOrdered((c) -> {
            add(c);
        });
        setupSideMenu(res);
        keyword = "";

    }

    @Override
    protected void showOtherForm(Resources res) {

    }

}
