package GUI.USER;

import Entities.User;
import GUI.DashboardScreen;
import Services.MenuService;
import Services.UserService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class EditProfileScreen extends Form {

    public EditProfileScreen(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Edit Profile");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(res).show();
        });
        Container c = new Container();
        c.setLayout(BoxLayout.y());

        User u = Session.currentUser;

        EncodedImage encProfile = EncodedImage.createFromImage(res.getImage("user.jpg"), false);
        URLImage urlimProfile = URLImage.createToStorage(encProfile, u.getAvatar(), CONSTANTS.AVATARURL + u.getAvatar(), URLImage.RESIZE_SCALE_TO_FILL);
        ImageViewer imgvProfile = new ImageViewer(urlimProfile);
        Image profilePic = imgvProfile.getImage();
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        TextField email = new TextField(u.getEmail(), "Email");
        TextField nom = new TextField(u.getNom(), "Nom");
        TextField prenom = new TextField(u.getPrenom(), "Prenom");
        TextField tel = new TextField(u.getNum_tel(), "Téléphone");
        Button editBtn = new Button("Confirmer", FontImage.MATERIAL_ADD, "adminBtn");
        editBtn.addActionListener((ed) -> {
            u.setEmail(email.getText());
            u.setNom(nom.getText());
            u.setPrenom(prenom.getText());
            u.setNum_tel(tel.getText());
            UserService.getInstance().editUser(u);
            Dialog.show("Edit effectué", "Votre profile a été edité avec succees", "ok", null);
            new MyProfileScreen(res).show();
        });

        c.addAll(profilePicLabel, email, nom, prenom, tel, editBtn);
        add(BorderLayout.centerAbsolute(c));

    }

}
