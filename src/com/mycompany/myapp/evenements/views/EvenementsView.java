package com.mycompany.myapp.evenements.views;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.io.IOException;
import java.util.List;

public class EvenementsView extends BaseEvent {
    
    private Form form ;
    private Toolbar tb;
    private EvenementService es;
    private List<Evenement> listEvenements;
    private EncodedImage enc;
    private String urlImage = "http://localhost/pi/tech_events/web/images/evenements/";
    
    public EvenementsView(){
        form = new Form();
        es = new EvenementService();
        setForm();
        setEvents();
    }
    
    public void setForm(){    
        form.setTitle("evenements");
        form.setLayout(BoxLayout.y());
        tb = form.getToolbar();
        addToolBar(tb);
    }
    
    public void setEvents(){
        listEvenements = es.getEvents();
        for (Evenement e : listEvenements){
            Container eventCont = new Container(new BorderLayout());
            //Container topC = new Container(BoxLayout.y());
            SpanLabel titre = new SpanLabel(e.getTitre());
            //topC.addAll(titre);
            //eventCont.add(BorderLayout.NORTH, topC);
            String urlImage2 ;    
            if (e.getUrl_image()!=null)
                urlImage2 = urlImage+e.getUrl_image();
            else
                urlImage2 = urlImage+"default.png";
            try {
                enc = enc.create("/load.png");
                Image img = URLImage.createToStorage(enc, urlImage2, e.getId()+urlImage2);
                eventCont.add(BorderLayout.CENTER, img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Container bottomC = new Container(BoxLayout.y());
            Label adresse = new Label(e.getAdresse(), FontImage.createMaterial(FontImage.MATERIAL_GPS_FIXED, new Style()));
            Label date = new Label(e.getDate().toString(), FontImage.createMaterial(FontImage.MATERIAL_TIMER, new Style()));
            bottomC.addAll(titre, adresse, date);
            eventCont.add(BorderLayout.SOUTH, bottomC);
            form.add(eventCont);
        }
    }
    
    public Form getForm(){
        return form;
    } 
}
