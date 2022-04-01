package GUI.Blog;

import GUI.DashboardScreen;
import Services.UserService;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

public class SendMailsScreen extends Form {

    public SendMailsScreen(Resources rs) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Notifier les clients");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(rs).show();
        });
        Label l1 = new Label("Le Sujet d'email : ");
        TextArea sujet = new TextArea();
        Label l2 = new Label("Le contenu d'email : ");
        TextArea ta = new TextArea();
        Button btn = new Button("Notifier", "adminBtn");
        btn.addActionListener(x -> {
            Message m = new Message(ta.getText());
            ArrayList<String> emails = new ArrayList<String>();
            UserService.getInstance().getUsers().stream().forEach(user -> emails.add(user.getEmail()));
            
            Display.getInstance().sendMessage(emails.toArray(new String[0]), sujet.getText(), m);
        });
        addAll(l1,sujet,l2,ta,btn);
    }
}
