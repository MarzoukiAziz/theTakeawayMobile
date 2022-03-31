
package GUI.USER;

import Entities.User;
import GUI.DashboardScreen;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import kraya.moazmar.com.CONSTANTS;
import kraya.moazmar.com.Session;

public class MyProfileScreen extends Form {

    public MyProfileScreen(Resources res) {
         super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Mon Profile");tb.setTitleCentered(true);
        tb.setBackCommand("Back",(ets)->{new DashboardScreen(res).show();});
        Container c = new Container();
        c.setLayout(BoxLayout.y());
        
        User u= Session.currentUser;
        
        
        EncodedImage encProfile = EncodedImage.createFromImage(res.getImage("user.jpg"), false);
        URLImage urlimProfile = URLImage.createToStorage(encProfile,u.getAvatar(), CONSTANTS.AVATARURL + u.getAvatar() , URLImage.RESIZE_SCALE_TO_FILL);
        ImageViewer imgvProfile = new ImageViewer(urlimProfile);
        Image profilePic =imgvProfile.getImage();
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        

        SpanLabel email = new SpanLabel();
        email.setText("Email : " + u.getEmail());
        email.setUIID("RestaurantSubtitle");
        
        SpanLabel nom = new SpanLabel();
        nom.setText("Nom  : " + u.getNom() + " " + u.getPrenom());
        nom.setUIID("RestaurantSubtitle");
        
        SpanLabel tel = new SpanLabel();
        tel.setText("Telephone : " + u.getNum_tel());
        tel.setUIID("RestaurantSubtitle");
        
        SpanLabel points = new SpanLabel();
        points.setText("Points : " + u.getPoints());
        points.setUIID("RestaurantSubtitle");
        
        c.addAll(profilePicLabel, email, nom, tel,points);
        add(BorderLayout.centerAbsolute(c));

    }
    
}
