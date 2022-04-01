package GUI.Reclamation;

import Entities.Reclamation;
import GUI.Reclamation.ReclamationsScreen;
import Services.ReclamationService;
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
import kraya.moazmar.com.Session;

public class ReclamationScreen extends Form {

    public ReclamationScreen(Resources res, Reclamation e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reclamation");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new ReclamationsScreen(res).show();
        });

        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());

        

        SpanLabel titleReclamation = new SpanLabel();
        titleReclamation.setText("Sujet : " + e.getSujet());
        titleReclamation.setUIID("RestaurantTitle");

        SpanLabel contenuReclamation = new SpanLabel();
        contenuReclamation.setText(e.getContenu());
        contenuReclamation.setUIID("RestaurantSubtitle");

        SpanLabel dateReclamation = new SpanLabel();
        dateReclamation.setText("Date : " + e.getDate());
        dateReclamation.setUIID("RestaurantSubtitle");

        SpanLabel authorReclamation = new SpanLabel();
        authorReclamation.setText("Auteur : " + e.getAuthor_name());
        authorReclamation.setUIID("RestaurantSubtitle");
       
        SpanLabel stautReclamation = new SpanLabel();
        stautReclamation.setText("Statut : " + e.getStatut());
        stautReclamation.setUIID("RestaurantSubtitle");
        
        Label reponsesLabel = new Label("Réponses : ", "adminBtn");
        Button addBtn = new Button("Nouveau Réponse");
        addBtn.addActionListener(x -> {
            new NewReponseScreen(res, e).show();
        });
        c.addAll(titleReclamation,contenuReclamation, dateReclamation, authorReclamation,stautReclamation,reponsesLabel);
        if(e.getStatut().equals("Ouvert")){
            c.add(addBtn);
        }
        add(c);
        ReclamationService.getInstance().getReponses(e)
                .stream()
                .forEach(com -> {
            Container con = new Container();
            con.setLayout(BoxLayout.y());
            con.setUIID("reponse");
            String autheur = com.getAuthor_id()==Session.currentUser.getId()?"Vous":"Admin";
            Label aname = new Label("Auteur : " + autheur);
            Label date = new Label("Date : " + com.getDate());
            SpanLabel contenu = new SpanLabel(com.getContenu());
            con.addAll(aname, date, contenu);
            add(con);
        });

    }

}
