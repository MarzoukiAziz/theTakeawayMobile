package GUI.Reservation;
import Entities.Reservation;
import Entities.Restaurant;
import GUI.AdminPanelScreen;
import GUI.DashboardScreen;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.plaf.Style;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class ChartsScreen extends Form {


    public ChartsScreen(Resources res) {
        Toolbar tb = getToolbar();
        tb.setTitle("Statistiques des Reservations");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new DashboardScreen(res).show();
        });
        Container c = new Container();
        c.setLayout(BoxLayout.y());
        ArrayList<Restaurant> resto =Services.RestaurantService.getInstance().getRestaurants();
        ArrayList<Reservation>rev = Services.ReservationService.getInstance().getReservations();
        TreeMap<String,Integer> statusMap = new TreeMap<String,Integer>();
        TreeMap data=new TreeMap<Integer,Double>();
        TreeMap nb=new TreeMap<Integer,Integer>();
        int x,y,z;
        for(Reservation r :rev){
            x=0;
            if(data.containsKey(r.getRestaurant_id())){
                x=(int) data.get(r.getRestaurant_id());
            }
            data.put(r.getRestaurant_id() ,x +1);
            
            y=0;
            if(statusMap.containsKey(r.getStatut())){
                y=(int) statusMap.get(r.getStatut());
            }
            statusMap.put(r.getStatut(), y+1);
            z=0;
            if(nb.containsKey(r.getNb_personne())){
                z=(int) nb.get(r.getNb_personne());
            }
            nb.put(r.getNb_personne(), z+1);
        }
        /*First Chart*/
        Label firstLabel = new Label("Les Reservations par Restaurant","adminBtn");
        ArrayList<String> titles=new ArrayList<String>();
        ArrayList<Integer> values= new ArrayList<Integer>();
        data.keySet()
                .stream()
                .forEach((e)->{
                    for(Restaurant r :resto){
                        if(r.getId()==(int)e){
                            titles.add(r.getNom());
                        }
                    }
                });
        data.values()
                .stream()
                .forEach((e)->{
                    values.add((int)e);
                });
        /*Second Chart*/
        Label secondLabel = new Label("Les Reservations par Statut","adminBtn");
        ArrayList<String> t=new ArrayList<String>();
        ArrayList<Integer> v= new ArrayList<Integer>();
        statusMap.keySet()
                .stream()
                .forEach((e)->{
                    t.add(e);
                });
        statusMap.values()
                .stream()
                .forEach((e)->{
                    v.add((int)e);
                });
        
          /*Thjrd Chart*/
        Label thirdLabel = new Label("Les Reservations par Nombre De Personnes","adminBtn");
        ArrayList<String> t3=new ArrayList<String>();
        ArrayList<Integer> v3= new ArrayList<Integer>();
        nb.keySet()
                .stream()
                .forEach((e)->{
                    t3.add(Integer.toString((int)e));
                });
        nb.values()
                .stream()
                .forEach((e)->{
                    v3.add((int)e);
                });
        
       c.addAll(firstLabel,createPieChartForm(res,titles,values),secondLabel,createPieChartForm(res,t,v),thirdLabel,createPieChartForm(res,t3,v3));
       add(BorderLayout.centerAbsolute(c));
    }
    
private DefaultRenderer buildCategoryRenderer(ArrayList<Integer> colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(60);
    renderer.setLegendTextSize(60);
    
    renderer.setLabelsColor(ColorUtil.BLACK);
    
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}

protected CategorySeries buildCategoryDataset(String title,ArrayList<String> titles, ArrayList<Integer> values) {
    CategorySeries series = new CategorySeries(title);
    
    int k = 0;
    for (int i=0;i<titles.size();i++) {
        series.add(titles.get(i), values.get(i));
    }

    return series;
}

public ChartComponent createPieChartForm(Resources res,ArrayList<String> resto, ArrayList<Integer> values) {
    Random rn = new Random();
    ArrayList<Integer> colors = new ArrayList<Integer>();
    for (int i=0;i<values.size();i++) {
        int x =ColorUtil.rgb(rn.nextInt(255), rn.nextInt(255), rn.nextInt(255));
                colors.add(x);

    }

    // Set up the renderer
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.rgb(250, 0, 0));
    r.setGradientStop(0, ColorUtil.rgb(250  , 80, 80));
    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Project budget",resto, values), renderer);
   
    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);

    
    return c;

}
}
