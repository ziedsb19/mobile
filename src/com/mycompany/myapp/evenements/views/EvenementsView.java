package com.mycompany.myapp.evenements.views;

import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.services.EvenementService;
import com.sun.prism.paint.Color;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EvenementsView extends BaseEvent {
    
    private Form form ;
    private Toolbar tb;
    private EvenementService es;
    private List<Evenement> listEvenements;
    private EncodedImage enc;
    private String urlImage = "http://localhost/pi/tech_events/web/images/evenements/";
    private Container evenements;
    private TextField searchBar;
    
    public EvenementsView(){
        form = new Form();
        es = new EvenementService();
        listEvenements = es.getEvents();
        setForm();
        setEvents(listEvenements);
        Container filterContainer = new Container(new GridLayout(1, 2));
        RadioButton gratuit = new RadioButton("gratuit");
        RadioButton payant = new RadioButton("payant");
        filterContainer.addAll(gratuit, payant);
        searchBar = new TextField(null, "chercher evenement");
        form.addAll(filterContainer,searchBar);
        form.add(evenements);
    }
    
    public void setForm(){    
        form.setTitle("evenements");
        form.setLayout(BoxLayout.y());
        tb = form.getToolbar();
        addToolBar(tb);
        evenements = new Container(BoxLayout.y());
    }
    
    public void setEvents(List<Evenement> listEvenements){
        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
        for (Evenement e : listEvenements){
            Container eventCont = new Container(new BorderLayout());
            SpanLabel titre = new SpanLabel(e.getTitre());
            Container titreC = new Container(new FlowLayout());
            titreC.addAll(titre);
            eventCont.add(BorderLayout.NORTH, titreC);
            Container eventCenter = new Container(new GridLayout(1, 2));
            String urlImage2 ;    
            if (e.getUrl_image()!=null)
                urlImage2 = urlImage+e.getUrl_image();
            else
                urlImage2 = urlImage+"default.png";
            try {
                enc = enc.create("/load.png");
                Image img = URLImage.createToStorage(enc, urlImage2, urlImage2);
                eventCenter.add(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Container bottomC = new Container(BoxLayout.y());
            String adresseText ;
            if (e.getAdresse().length()>30)
                adresseText = e.getAdresse().substring(0,30)+"...";
            else
                adresseText = e.getAdresse();
            SpanLabel adresse = new SpanLabel(adresseText);
            adresse.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
            SpanLabel date = new SpanLabel("Date: "+dateF.format(new Date()));
            date.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
            String categories = "";
            for (Categorie c : e.getListCategories()){
                categories += " # "+c.getName();
            }
            Label Categories_label = new Label("tags: "+categories);
            double prix = e.getPrix();
            Label prix_label = new Label();
            prix_label.getAllStyles().setFgColor(0x0000ff); 
            prix_label.addPointerPressedListener((l)->{
                System.out.println("yes");
                VarGlobales.setEventId(e.getId());
                new OneEvent().getForm().show();
            });
            if (prix>0){
                prix_label.setText("Payant");
            }
            else{
                prix_label.setText("Gratuit");
            }
            bottomC.addAll( adresse, date, Categories_label ,prix_label);
            eventCenter.add(bottomC);
            eventCont.add(BorderLayout.CENTER, eventCenter);
            eventCont.setUIID("event_cont");
            bottomC.setLeadComponent(prix_label);
            evenements.add(eventCont);
        }
    }
    
    public Form getForm(){
        return form;
    } 
}
