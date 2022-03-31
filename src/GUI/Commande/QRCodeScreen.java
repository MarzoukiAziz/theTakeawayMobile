package GUI.Commande;

import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import Entities.Commande;
import Services.CommandeService;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import kraya.moazmar.com.CONSTANTS;

public class QRCodeScreen extends Form {

    QRCodeScreen(Resources res, Commande cmd) {
        setLayout(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Commande n° " + cmd.getId() + " QR Code");
        tb.setTitleCentered(true);
        tb.setBackCommand("Back", (ets) -> {
            new CommandeDetailsScreen(res, cmd).show();
        });
        String qrCodeLink =  CONSTANTS.QRCODEURL + CommandeService.getInstance().qrcode(cmd.getId());
        EncodedImage enc = EncodedImage.createFromImage(res.getImage("qr.jpg"), false);
        URLImage urlim = URLImage.createToStorage(enc, qrCodeLink,qrCodeLink, URLImage.RESIZE_SCALE_TO_FILL);
        ImageViewer imgv = new ImageViewer(urlim);
        imgv.setWidth(1000);
        imgv.setHeight(1000);
        imgv.getAllStyles().setMarginTop(800);
        add(imgv);
    }
}
