package com.mycompany.myapp.evenements.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.googlemaps.MapContainer;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.CN.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.entites.Inscription;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.io.IOException;

public class OneEvent extends BaseEvent{
    
    private Form  form ;
    private Toolbar tb;
    private EvenementService es ;
    private Evenement evenement;
    private EncodedImage enc ;
    private final String urlI = "http://localhost/pi/tech_events/web/images/evenements/";
    
    public OneEvent(){
        setForm();
        es = new EvenementService();
        evenement = es.getEventById(VarGlobales.getEventId());
        setEvent();
        setPrivileges();
        setMap();
    }
    
    public void setForm(){
        form = new Form("Details", BoxLayout.y());
        form.getAllStyles().setBgColor(0xf4f9f4);
        form.getAllStyles().setOpacity(255);
        tb = form.getToolbar();
        addToolBar(tb);
        tb.addMaterialCommandToLeftBar("retour", FontImage.MATERIAL_ARROW_BACK, (e)->{
            EvenementsView ev = new EvenementsView();
            ev.getForm().show();
        });
    }
    
    public Form getForm(){
        return form;
    }

    private void setEvent() {
        Container top = new Container(BoxLayout.y());
        Container vtop = new Container(new FlowLayout(CENTER));
        SpanLabel titre = new SpanLabel(evenement.getTitre().toUpperCase());
        ImageViewer iv = new ImageViewer();
        iv.setWidth(Display.getInstance().getDisplayWidth());
        String urlImage ;
        if (evenement.getUrl_image()!= null)
            urlImage = urlI+evenement.getUrl_image();
        else 
            urlImage = urlI+"default.png";
        try {
            enc = enc.create("/placeholder9.jpg");
            Image image = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE_TO_FILL); 
            iv.setImage(image);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        vtop.addAll(titre);
        top.addAll(iv,vtop);
        //*****************************************************************
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
        Container middle = new Container(BoxLayout.y());
        Label titre_middle = new Label("Informations génerales :");
        FontImage.setMaterialIcon(titre_middle, FontImage.MATERIAL_INFO);
        titre_middle.setUIID("event_titre");
        
        Container userCon = new Container(new FlowLayout(LEFT, CENTER));
        Label utilisateur = new Label(evenement.getUtilisateur().getUsername());
        utilisateur.setUIID("event_label");
        userCon.addAll(new Label("créée par: ",FontImage.createMaterial(FontImage.MATERIAL_PERSON, new Style())), utilisateur);

        Container adresseCon = new Container(BoxLayout.x());
        Label adresse = new Label(evenement.getAdresse());
        adresse.setUIID("event_label");
        adresseCon.addAll(new Label("Adresse: ",FontImage.createMaterial(FontImage.MATERIAL_PLACE, new Style())), adresse);
        
        Container dateCon = new Container(new FlowLayout(LEFT, CENTER));
        Label date = new Label(dateFormater.format(evenement.getDate()));
        date.setUIID("event_label");
        dateCon.addAll(new Label("Date : ",FontImage.createMaterial(FontImage.MATERIAL_ACCESS_TIME, new Style())), date);

        Container prixCon = new Container(new FlowLayout(LEFT, CENTER));
        Label prix = new Label(evenement.getPrix()+" $");
        prix.setUIID("event_label");
        prixCon.addAll(new Label("prix : ",FontImage.createMaterial(FontImage.MATERIAL_CREDIT_CARD, new Style())), prix);
                
        Container billetsCon = new Container(new FlowLayout(LEFT, CENTER));
        Label billets = new Label(evenement.getBillets_restants()+" billets");
        billets.setUIID("event_label");
        billetsCon.addAll(new Label("Billets : ",FontImage.createMaterial(FontImage.MATERIAL_BOOK, new Style())), billets);
        
        
        String categories = "";
        for (Categorie c : evenement.getListCategories()){
            categories += " #"+c.getName();
        }
        
        Container categoriesCont = new Container(new FlowLayout(LEFT, CENTER));
        SpanLabel categorie_label = new SpanLabel(categories);
        categorie_label.setUIID("event_label");
        categoriesCont.addAll(new Label("tags : ",FontImage.createMaterial(FontImage.MATERIAL_LOCAL_OFFER, new Style())), categorie_label);  
        middle.addAll(titre_middle, userCon, adresseCon, dateCon, prixCon, billetsCon, categoriesCont);
       
        //************************************************************************
        Container bottom = new Container(BoxLayout.y());
        Label description_Titre = new Label("Description de l'evenement :");
        description_Titre.setUIID("event_titre");
        Container descriptionBox = new Container();
        if (evenement.getDescription() == null)
        {
            descriptionBox.setLayout(new FlowLayout(CENTER));
            descriptionBox.add("pas de description");
        }
        else{
            descriptionBox.setLayout(new FlowLayout());
            descriptionBox.add(new SpanLabel(evenement.getDescription()));            
        }
        bottom.addAll(description_Titre,descriptionBox);
        //************************************************************************
        form.addAll(top, middle,bottom); 
    }

    private void setPrivileges() {
        Container operations = new Container (BoxLayout.y());
        Container priv;
        Label priv_title = new Label("Operations :");
        priv_title.setUIID("event_titre");
        if (evenement.getUtilisateur().getId() == VarGlobales.getUtilisateur().getId()){
            priv = new Container (new GridLayout(1, 2));
            Button update = new Button(" update ",FontImage.MATERIAL_UPDATE,"update");
            update.setUIID("secondary_button");
            update.addActionListener((l)->{
                new UpdateEvent().getForm().show();
            });
            Button delete = new Button(" delete",FontImage.MATERIAL_DELETE,"delete");
           
            delete.setUIID("danger_button");
            delete.addActionListener((l)->{
                if (es.deleteEvent(evenement)){
                    if (Dialog.show("suppression", "evenement supprimé avec succes ", "ok", null))
                        new EvenementsView().getForm().show();             
                }
            });
            priv.addAll(delete, update);
        }
        else {
            priv = new Container(new GridLayout(1, 2));
            Button inscrire = new Button();
            boolean test = false;
            for (Inscription inscri : evenement.getListInscriptions()){
                if (inscri.getUtilisateur().getId() == VarGlobales.getUtilisateur().getId()){
                    test = true;
                    break;
                }
            }
            if (test){
                inscrire.setText("annuler Inscription"); 
                inscrire.setUIID("primary_outline_button");
            }
            else {
                inscrire.setText("inscrire ");
                inscrire.setUIID("primary_button");
            }
            inscrire.addActionListener((l)->{
                if (es.inscriEvent(evenement, VarGlobales.getUtilisateur()) != null){
                    if (inscrire.getText().equals("annuler Inscription")){
                        Dialog.show("inscription", "vous avez annulez votre inscription", "ok", null);
                        inscrire.setText("inscrire ");
                        inscrire.setUIID("primary_button");
                        form.revalidate();
                    }
                    else{
                        Dialog.show("inscription", "inscription avec succes", "ok", null);
                        inscrire.setText("annuler Inscription");
                        inscrire.setUIID("primary_outline_button");
                        form.revalidate();
                    }
                }
            });
            Button save = new Button();
            if (es.isSaved(evenement, VarGlobales.getUtilisateur().getId())){
                save.setText("enlever du fav");
                save.setUIID("primary_outline_button");
            }
            else {
                save.setText("ajouter au fav");
                save.setUIID("primary_button");
            }
            save.addActionListener((l)->{
                if (es.save(evenement, VarGlobales.getUtilisateur().getId())){
                    save.setText("enlever du fav");
                    Dialog.show("favoris", "evenement ajouté au favoris", "ok", null);
                    save.setUIID("primary_outline_button");
                    form.revalidate();
                }
                else{
                    save.setText("ajouter au fav");
                    Dialog.show("favoris", "evenement enleve du favoris", "ok", null);
                    save.setUIID("primary_button");
                    form.revalidate();
                }
            });
            priv.addAll(inscrire,save);
        }
        operations.addAll(priv_title,priv);
        form.add(operations);
    }
    
    private void setMap(){
        Container map = new Container(BoxLayout.y());
        Label map_titre = new Label ("map :");
        FontImage.setMaterialIcon(map_titre, FontImage.MATERIAL_SATELLITE);
        map_titre.setUIID("event_titre");
        map.add(map_titre);
        if (evenement.getLatLng()!= null){        
            MapContainer mapCnt = new MapContainer("AIzaSyCdvU3nYMT74CA2mGDKJLQt07Ea-bg1Ql0");
            String latLng = evenement.getLatLng();
            float lat =Float.parseFloat(latLng.substring(0, latLng.indexOf("/")));
            float lng =Float.parseFloat(latLng.substring(latLng.indexOf("/")+1, latLng.length()));
            mapCnt.zoom(new Coord(lat, lng), 13);
            mapCnt.setHeight(200);
            map.add(mapCnt);
        }
        else 
            map.add(new Label("pas de coordonnés poue cette adresse "));
        
        form.add(map);
    }
}