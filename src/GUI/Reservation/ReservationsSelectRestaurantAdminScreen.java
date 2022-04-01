package GUI.Reservation;

import Entities.Restaurant;
import GUI.AdminPanelScreen;
import Services.RestaurantService;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

public class ReservationsSelectRestaurantAdminScreen extends Form {

    public ReservationsSelectRestaurantAdminScreen(Resources rs) {

        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Reservations Admin");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new AdminPanelScreen(rs).show();
        });
        Label title = new Label("Choisir un restaurant");
        add(title);
        ArrayList<Restaurant> res = RestaurantService.getInstance().getRestaurants();
        for (Restaurant r : res) {
            Button resBtn = new Button(r.getNom(), "adminBtn");
            resBtn.addActionListener((e) -> {
                new ReservationPerRestaurantAdminScreen(rs,r,"").show();
            });
            add(resBtn);
        }

    }
}
