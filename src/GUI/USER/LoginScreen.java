package GUI.USER;
import Services.UserService;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

public class LoginScreen extends Form {
    public LoginScreen(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenue au TakeAway ", "WelcomeWhite")
        );
        
        getTitleArea().setUIID("Container");
        
        Image profilePic = theme.getImage("logo2.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        TextField login = new TextField("rafrafi@gmail.com", "Email", 20, TextField.EMAILADDR) ;
        login.setHint("Votre Email");
        TextField password = new TextField("rafrafi@gmail.com", "Mot De Passe", 20, TextField.PASSWORD) ;
        password.setHint("Votre Mot De Passe");
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("Connexion");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
          UserService.getInstance().connexion(login.getText(), password.getText(), theme);
        });
        
        Button createNewAccount = new Button("Créer un compte");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener((e)->{new RegisterScreen(theme).show();});
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount
        );
       
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
