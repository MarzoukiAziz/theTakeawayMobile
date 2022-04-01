package GUI.RestoMenu;

import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.List;

public class MapForm {

    Form f = new Form();
    MapContainer cnt = null;

    public MapForm(float x, float y) {

        try {
            cnt = new MapContainer("AIzaSyCy-fMWerzvXcPCV0FDI07hW2DAzs_mnpY");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        
        Button btnMoveCamera = new Button("Localistion");
        btnMoveCamera.addActionListener(e -> {
            cnt.zoom(new Coord(x, y), 16);
        });
        cnt.setCameraPosition(new Coord(x, y));

        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s);
        cnt.addMarker(
                EncodedImage.createFromImage(markerImg, false),
                new Coord(x, y),
                "" + cnt.getCameraPosition().toString(),
                "",
                e3 -> {
                    ToastBar.showMessage("You clicked " + cnt.getName(), FontImage.MATERIAL_PLACE);
                }
        );

        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.NORTH, btnMoveCamera);
        f.addComponent(BorderLayout.CENTER, cnt);
    }

}
