package com.mycompany.myapp.evenements.views;

import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import static com.codename1.ui.CN.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Library  extends BaseEvent{
    
    private Form form ; 
    private Toolbar tb;
    private EvenementService es;
    private List<Evenement> listEvenementsOrg;
    private List<Evenement> listEvenementsInscris;
    private EncodedImage enc;
    private String urlImage = "http://localhost/pi/tech_events/web/images/evenements/";
    private Container evenements;
    
    public Library(){
        setForm();
        es = new EvenementService();
        Label titre_bib = new Label("evenements organisés :");
        titre_bib.setUIID("event_titre");
        evenements = new Container(BoxLayout.y());
        listEvenementsOrg = es.getEventsOrganises(VarGlobales.getUtilisateur().getId());
        listEvenementsInscris = es.getEventsInscris(VarGlobales.getUtilisateur().getId());
        setEvents(listEvenementsOrg);
        Container south = new Container (new GridLayout(1, 2));
        Container buttOrg = new Container(new FlowLayout(CENTER));
        Label org_label = new Label(FontImage.createMaterial(FontImage.MATERIAL_BOOKMARK, new Style())); 
        buttOrg.add(org_label);
        org_label.addPointerPressedListener((l)->{
            titre_bib.setText("evenements organisés :");
            evenements.removeAll();
            setEvents(listEvenementsOrg);
            form.refreshTheme();
        });
        buttOrg.setLeadComponent(org_label);
        Container buttInscri = new Container(new FlowLayout(CENTER));
        Label inscri_label = new Label(FontImage.createMaterial(FontImage.MATERIAL_HOME, new Style()));
        inscri_label.addPointerPressedListener((l)->{
            titre_bib.setText("evenements inscris :");
            evenements.removeAll();
            setEvents(listEvenementsInscris);
            form.refreshTheme();
        });
        buttInscri.add(inscri_label);
        buttInscri.setLeadComponent(inscri_label);
        south.addAll(buttOrg, buttInscri);
        Container center_con = new Container(BoxLayout.y());
        center_con.addAll(titre_bib,evenements);
        center_con.setScrollableY(true);
        form.add(BorderLayout.SOUTH, south);
        form.add(BorderLayout.CENTER, center_con);
    }
    
    public void setForm(){    
        form = new Form("bibliotheque", new BorderLayout());
        form.setScrollable(false);
        tb = form.getToolbar();
        addToolBar(tb);
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
            adresse.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
            SpanLabel date = new SpanLabel("Date: "+dateF.format(e.getDate()));
            date.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
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
