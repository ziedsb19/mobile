package com.mycompany.myapp.evenements.views;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
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
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvenementsView extends BaseEvent {
    
    //NOTE: x button when press on search ..... fixe adresse size

    private Form form ;
    private Toolbar tb;
    private EvenementService es;
    private List<Evenement> listEvenements;
    private EncodedImage enc;
    private String urlImage = "http://"+VarGlobales.path+"/pi/tech_events/web/images/evenements/";
    private Container evenements;
    private TextField searchBar;
    private Container topFilter;
    private List<Categorie> listCategories;
    
    public EvenementsView(){
        form = new Form();
        Container center_form = new Container(BoxLayout.y());
        center_form.setScrollableY(true);
        es = new EvenementService();
        listEvenements = es.getEvents();
        listCategories = es.getCategories();
        setForm();
        setEvents(listEvenements);
        Container filterContainer = new Container(new GridLayout(1, 2));
        RadioButton gratuit = new RadioButton("gratuit");
        RadioButton payant = new RadioButton("payant");
        filterContainer.addAll(gratuit, payant);
        topFilter = new Container(BoxLayout.y()); 
        setFilter();
        TextField searchText = new TextField(null, "chercher par titre ...");
        searchText.setHidden(true);
        //*********************************************************
        Button search = new Button(FontImage.MATERIAL_SEARCH);
        search.getAllStyles().setPadding(3, 3, 3, 3);
        Container searchCont = new Container(new FlowLayout(RIGHT, CENTER));
        searchCont.add(search);
        searchCont.setWidth(20);
        Container searchBorder = new Container(new BorderLayout());
        searchBorder.add(BorderLayout.SOUTH,searchCont);
        search.addActionListener((l)->{
            if (searchText.isHidden()){
                searchText.setHidden(false);
            }
            else {
                searchText.setHidden(true);
                searchText.setText(null);
                evenements.removeAll();
                setEvents(listEvenements);
            }
            form.refreshTheme();
        });
        searchText.addDataChangedListener((l,i)->{
            List<Evenement> tomponList = new ArrayList<>();
            for (Evenement ev : listEvenements){
                if (ev.getTitre().toUpperCase().trim().startsWith(searchText.getText().toUpperCase().trim()))
                    tomponList.add(ev);
            }
            evenements.removeAll();
            setEvents(tomponList);
            form.revalidate();
        });
        //*********************************************************
        center_form.addAll(topFilter,searchText);
        center_form.add(evenements);
        form.add(BorderLayout.CENTER,LayeredLayout.encloseIn(center_form,searchBorder));
    }
    
    public void setFilter(){
        Button filtrerShow = new Button("Filtrer");
        filtrerShow.addActionListener((l)->{
            topFilter.removeAll();
            topFilter.setUIID("filter_con");
            TextField organisateur = new TextField(null,"organisateur du l'event");
            Picker date = new Picker();
            date.setDate(null);
            ComboBox categories = new ComboBox();
            categories.addItem("pas de choix");
            for (Categorie c : listCategories){
                categories.addItem(c.getName());
            }
            RadioButton gratuit = new RadioButton("Gratuit");
            RadioButton payant = new RadioButton("payant");
            Container prix_cont = new Container(new GridLayout(1,2));
            payant.addActionListener((ep)->{
                if (gratuit.isSelected())
                    gratuit.setSelected(false);
            });
            gratuit.addActionListener((ep)->{
                if (payant.isSelected())
                    payant.setSelected(false);
            });
            prix_cont.addAll(gratuit,payant);
            Button appliquer = new Button("appliquer");
            appliquer.setUIID("primary_button");
            
            Button close = new Button("reset");
            close.setUIID("primary_outline_button");
            
            close.addActionListener((el)->{
                topFilter.removeAll();
                evenements.removeAll();
                setEvents(listEvenements);
                setFilter();
                form.revalidate();
            });
            appliquer.addActionListener((el2)->{
                List<Evenement> filtredEvents = listEvenements;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                if (!organisateur.getText().isEmpty())
                {
                    List<Evenement> tomponList = new ArrayList<>();
                    for (Evenement e : filtredEvents){
                        if (e.getUtilisateur().getUsername().equals(organisateur.getText()))
                            tomponList.add(e);
                    }
                    filtredEvents = tomponList;
                }
                if (date.getDate() != null){
                    List<Evenement> tomponList = new ArrayList<>();
                    for (Evenement e : filtredEvents){
                        if (dateFormat.format(date.getDate()).equals(dateFormat.format(e.getDate())))
                            tomponList.add(e);
                    }
                    filtredEvents = tomponList;
                }
                if (gratuit.isSelected()){
                    List<Evenement> tomponList = new ArrayList<>();
                    for (Evenement e : filtredEvents){
                        if (e.getPrix()==0)
                            tomponList.add(e);
                    }
                    filtredEvents = tomponList;
                }
                if (payant.isSelected()){
                    List<Evenement> tomponList = new ArrayList<>();
                    for (Evenement e : filtredEvents){
                        if (e.getPrix()!=0)
                            tomponList.add(e);
                    }
                    filtredEvents = tomponList;
                }                
                if (categories.getSelectedIndex()!=0){
                    List<Evenement> tomponList = new ArrayList<>();
                    for (Evenement e : filtredEvents){
                        if (e.getListCategories().contains(listCategories.get(categories.getSelectedIndex()-1)))
                        tomponList.add(e);
                    }
                    filtredEvents = tomponList;
                }                

                evenements.removeAll();
                setEvents(filtredEvents);
                form.revalidate();
            });
            Container butt_cont = new Container(new GridLayout(1, 2));
            butt_cont.addAll(appliquer, close);
            topFilter.addAll(new Label("organisateur : "),organisateur,new Label("date : "), date,new Label("prix :"),
                    prix_cont,new Label("categories :") ,categories,butt_cont);
            form.revalidate();
        });
        topFilter.add(filtrerShow);
        topFilter.setUIID("");
    }
    
    public void setForm(){    
        form.setTitle("evenements");
        form.setLayout(new BorderLayout());
        form.setScrollable(false);
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
                VarGlobales.setEventId(e.getId());
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
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
