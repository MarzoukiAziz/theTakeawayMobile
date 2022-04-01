
package GUI.Reclamation;

import Entities.Reclamation;
import GUI.SideMenuBaseForm;
import Services.ReclamationService;
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


public class ReclamationsScreen extends SideMenuBaseForm {

    static String keyword = "";

    public ReclamationsScreen(Resources res) {
        super(BoxLayout.y());
        
        ImageViewer imgvProfile = new ImageViewer(res.getImage("recincon.png"));
        Toolbar tb = getToolbar();
        tb.setUIID("recsToolbar");
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
                                new Label(" Reclamations          ", "USERNAME")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
                
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener((e) -> {
           new NewReclamationScreen(res).show();
        });
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        
        
        add(new Label("Notre But est vous satisfaire", "TodayTitle"));
        
        
        Container recherche = new Container();
        recherche.setLayout(BoxLayout.x());
        TextField tf = new TextField();
        tf.setText(keyword);
        tf.setHint("Rechercher ");
        
        Button btn = new Button(FontImage.MATERIAL_SEARCH);
        btn.addActionListener((e) -> {
            keyword = tf.getText();
           new ReclamationsScreen(res).show();
        });
        recherche.addAll(tf, btn);
        add(recherche);

        ArrayList<Reclamation> recs = ReclamationService.getInstance().getReclamations();
        recs.stream()
                .filter((e)->e.getAuthor_id()==Session.currentUser.getId())
                .filter((e) ->(e.getSujet().toUpperCase().contains(keyword.toUpperCase())|| e.getContenu().toUpperCase().contains(keyword.toUpperCase())))
                .forEach((e) -> {
                    Container c = new Container();
                    c.setUIID("reclamation");
                    c.setLayout(BoxLayout.y());
                    
                    SpanLabel title = new SpanLabel();
                    title.setText("  Sujet : " +e.getSujet());
                    
                     SpanLabel statut = new SpanLabel();
                    statut.setText("  Statut : "+e.getStatut());
                    
                    Button select = new Button();
                    select.setText("Consulter");
                    
                    select.addActionListener((action) -> {
                        new ReclamationScreen(res, e).show();

                    });
                    c.addAll(title,statut, select);
                    add(c);
                });
        setupSideMenu(res);
        keyword = "";

    }

    @Override
    protected void showOtherForm(Resources res) {

    }

}
