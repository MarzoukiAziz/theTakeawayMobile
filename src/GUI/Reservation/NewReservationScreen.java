package GUI.Reservation;
import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;
import GUI.DashboardScreen;
import Services.ReservationService;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.Switch;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import kraya.moazmar.com.CONSTANTS;
import com.codename1.imagemap.ImageMapContainer;
import kraya.moazmar.com.Session;


public class NewReservationScreen extends Form {
    Reservation nRev;
    
    public NewReservationScreen(Resources res, Restaurant e) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Nouvelle Reservation");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(res).show();
        });
        Container c = new Container();
        c.setUIID("RestaurantContainer");
        c.setLayout(BoxLayout.y());
        EncodedImage enc = EncodedImage.createFromImage(res.getImage("hot-pot.png"), false);
       URLImage urlim = URLImage.createToStorage(enc, e.getImages().get(0), CONSTANTS.IMAGEURL + e.getImages().get(0), URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(urlim);
        Label dateLabel= new Label("Date");
        Picker date = new Picker();
        
        Label heureLabel= new Label("Heure D'arrivé");
        Container heure =new Container();
        heure.setLayout(BoxLayout.x());
        
        ComboBox ha1 = new ComboBox(new String[]{"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"});
        ComboBox ha2 =new ComboBox(new String[]{"0", "15", "30", "45"});
        heure.addAll(ha1,new Label(" : "),ha2);
        Label dureeLabel= new Label("Départ dans ");
        Container depart = new Container();
        depart.setLayout(BoxLayout.x());
        Label min= new Label("Minutes");
        ComboBox hd = new ComboBox(new String[]{"15", "30", "45"});
        depart.addAll(hd,min);
        Label nbPleabel= new Label("Nombre de Personnes ");
        ComboBox nbP = new ComboBox(new String[]{"1","2","3","4","5","6","7","8","9","10"});
        Button reserve = new Button();
        reserve.setText("-> Tables Disponibles");
        reserve.addActionListener((es) -> {
            nRev=new Reservation();
            nRev.setRestaurant_id(e.getId());
            nRev.setNb_personne(nbP.getSelectedIndex()+1);
            nRev.setDate(new Date(date.getDate().getYear(), date.getDate().getMonth(), date.getDate().getMinutes()));
            nRev.setHeure_arrive(new Time(ha1.getSelectedIndex()+11, ha2.getSelectedIndex()*15, 0));
            nRev.setClient_id(Session.currentUser.getId());
            ArrayList<Table> tables =Services.ReservationService.getInstance().getDispoTables(nRev, hd.getSelectedIndex()*15+15);
            Container tabs = new Container();
            tabs.setLayout(BoxLayout.y());
            for(Table t: tables){
                RadioButton s = new RadioButton(t.getNumero()+"("+t.getNbPlaces()+" Places)");
                s.addActionListener((exx)->{if(s.isSelected()){
                   nRev.addTable(t);
                }
                }
                );
                tabs.add(s);
            }
            
            Button confirm = new Button("Confirmer");
            confirm.addActionListener((ey)->{
                Services.ReservationService.getInstance().reserve(nRev, hd.getSelectedIndex()*15+15,res);
            });
            c.addAll(tabs,confirm);
            revalidate();
        });
        c.addAll(imgv,dateLabel,date,heureLabel,heure,dureeLabel,depart,nbPleabel,nbP,reserve);
        add(BorderLayout.centerAbsolute(c));

    }
}
