
package GUI.Reclamation;

import Entities.Reclamation;
import GUI.AdminPanelScreen;
import static GUI.Reclamation.ReclamationsScreen.keyword;
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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import kraya.moazmar.com.CONSTANTS;


public class ReclamationsAdminScreen extends Form {
    
    public ReclamationsAdminScreen(Resources res) {
        super(BoxLayout.y());
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reclamations " );
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(res).show();
        });
       
        ArrayList<Reclamation> recs = ReclamationService.getInstance().getReclamations();
        recs.stream()
                .map((e) -> {
                    Container c = new Container();
                    c.setUIID("RestaurantContainer");
                    c.setLayout(BoxLayout.y());
                    SpanLabel title = new SpanLabel();
                    title.setText(e.getSujet());
                    Label statut = new Label("Statut : "+e.getStatut());
                    Button select = new Button();
                    select.setText("Consulter");
                    select.addActionListener((action) -> {
                        new ReclamationAdminScreen(res, e).show();

                    });
                    c.addAll(title,statut, select);
                    return c;
                }).forEachOrdered((c) -> {
            add(c);
        });

    }
}
