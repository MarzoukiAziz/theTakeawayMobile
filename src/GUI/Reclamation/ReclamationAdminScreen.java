package GUI.Reclamation;

import Entities.Reclamation;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;

public class ReclamationAdminScreen extends Form {

    public ReclamationAdminScreen(Resources res, Reclamation e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reclamation");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new ReclamationsAdminScreen(res).show();
        });
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());


        SpanLabel titleReclamation = new SpanLabel();
        titleReclamation.setText("Titre : " + e.getSujet());
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

        SpanLabel statut = new SpanLabel();
        statut.setText("Statut : " + e.getStatut());
        statut.setUIID("RestaurantSubtitle");

        Label changeStatut = new Label("Changer Statut : ", "adminBtn");
        Button open = new Button("Ouvrir");
        open.addActionListener(x -> {
            e.setStatut("Ouvert");
            ReclamationService.getInstance().changerStatutReclamation(e);
            
            Dialog.show("Statut Mis A jour", "Statut Mis A jour avec succés !", "ok", null);
            new ReclamationAdminScreen(res, e).show();
        });
        Button refuse = new Button("Fermer");
        refuse.addActionListener(x -> {
            e.setStatut("Ferme");
            ReclamationService.getInstance().changerStatutReclamation(e);
            
            Dialog.show("Statut Mis A jour", "Statut Mis A jour avec succés !", "ok", null);
            new ReclamationAdminScreen(res, e).show();
        });

        Label supprimerLabel = new Label("Supprimer : ", "adminBtn");
        Button del = new Button("Supprimer");
        del.addActionListener(x -> {
            ReclamationService.getInstance().deleteReclamation(e.getId());
            Dialog.show("Supprimé", "La reclamation a été supprimer avec succés !", "ok", null);
            new ReclamationsAdminScreen(res).show();
        });

        c.addAll( titleReclamation, dateReclamation, authorReclamation, contenuReclamation, statut, changeStatut, open, refuse, supprimerLabel, del);
        add(c);

    }

}
