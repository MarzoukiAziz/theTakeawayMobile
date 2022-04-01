package GUI.USER;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import Entities.User;
import GUI.AdminPanelScreen;
import Services.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import kraya.moazmar.com.CONSTANTS;

public class UsersAdminScreen extends Form {

    public UsersAdminScreen(Resources res) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Users Admin");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(res).show();
        });
        ArrayList<User> users = UserService.getInstance().getUsers();

        users.stream()
                .map((User u) -> {
                    Container element = new Container();
                    element.setLayout(BoxLayout.x());
                    EncodedImage encProfile = EncodedImage.createFromImage(res.getImage("user.jpg"), false);
                    URLImage urlimProfile = URLImage.createToStorage(encProfile, u.getAvatar(), CONSTANTS.AVATARURL + u.getAvatar(), URLImage.RESIZE_SCALE_TO_FILL);
                    ImageViewer imgvProfile = new ImageViewer(urlimProfile);
                    Image profilePic = imgvProfile.getImage();
                    Image mask = res.getImage("round-mask.png");
                    profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
                    Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
                    profilePicLabel.setMask(mask.createMask());
                    element.add(profilePicLabel);
                    
                    Container data = new Container();
                    data.setLayout(BoxLayout.y());
                    
                    SpanLabel id = new SpanLabel();
                    id.setText("Ref : "+u.getId());
                    
                    SpanLabel nom = new SpanLabel();
                    nom.setText("Nom : "+u.getNom());
                    
                    
                    SpanLabel prenom = new SpanLabel();
                    prenom.setText("Prenom : "+u.getPrenom());
                    
                    SpanLabel email = new SpanLabel();
                    email.setText("Email : "+u.getEmail());
                    
                    SpanLabel tel = new SpanLabel();
                    tel.setText("Téléphone : "+u.getNom());
                    
                    SpanLabel points = new SpanLabel();
                    points.setText("Points : "+u.getPoints());
                    
                  
                    Button delete = new Button("Delete", "delBtn");
                    delete.addActionListener((ed)
                            -> {
                        UserService.getInstance().deleteUser(u.getId());
                        Dialog.show("Suppression effectué", "Ce utilisateur a été supprimé avec succees", "ok", null);
                        new UsersAdminScreen(res).show();

                    });
                    data.addAll(id,nom, prenom,email,tel,points, delete);
                    element.add(data);
                    return element;
                }).forEachOrdered((element) -> {
            add(element);
        });
    }

}
