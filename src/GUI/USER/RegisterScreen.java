package GUI.USER;

import Services.UserService;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
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

public class RegisterScreen extends Form {

    public RegisterScreen(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenue au TakeAway ", "WelcomeWhite")
        );

        getTitleArea().setUIID("Container");

        Image profilePic = theme.getImage("logo2.png");
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        TextField login = new TextField("", "Email", 20, TextField.EMAILADDR);
        TextField nom = new TextField("", "Nom", 20, TextField.USERNAME);
        TextField prenom = new TextField("", "Prenom", 20, TextField.USERNAME);
        TextField tel = new TextField("", "Telephone", 20, TextField.NUMERIC);
        TextField password = new TextField("", "Mot De Passe", 20, TextField.PASSWORD);

        login.setHint("Email");
        password.setHint("Mot De Passe");
        nom.setHint("Nom");
        prenom.setHint("Prenom");
        tel.setHint("Téléphone");
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        nom.getAllStyles().setMargin(LEFT, 0);
        prenom.getAllStyles().setMargin(LEFT, 0);
        tel.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label nomIcon = new Label("", "TextField");
        Label prenomIcon = new Label("", "TextField");
        Label telIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        prenomIcon.getAllStyles().setMargin(RIGHT, 0);
        nomIcon.getAllStyles().setMargin(RIGHT, 0);
        telIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(nomIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(prenomIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(telIcon, FontImage.MATERIAL_PHONE, 3);
        
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button registerButton = new Button("Register");
        registerButton.setUIID("LoginButton");
        registerButton.addActionListener(e -> {
            //new ProfileForm(theme).show();
            UserService.getInstance().inscription(login.getText(), nom.getText(), prenom.getText(), tel.getText(), password.getText(),theme);
        });

        Button loginBtn = new Button("Login");
        loginBtn.setUIID("CreateNewAccountButton");
        loginBtn.addActionListener((ee)->{
            new LoginScreen(theme).show();
        });

        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
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
                 BorderLayout.center(nom).
                        add(BorderLayout.WEST, nomIcon),
                  BorderLayout.center(prenom).
                        add(BorderLayout.WEST, prenomIcon),
                   BorderLayout.center(tel).
                        add(BorderLayout.WEST, telIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                registerButton,
                loginBtn
        );

        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
