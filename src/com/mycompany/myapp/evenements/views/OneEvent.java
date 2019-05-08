package com.mycompany.myapp.evenements.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.fingerprint.Fingerprint;
import com.codename1.googlemaps.MapContainer;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.entites.Inscription;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.io.IOException;

public class OneEvent extends BaseEvent{
    
    //FIXME: delete button 
    //NOTE: size of map

    private Form  form ;
    private Toolbar tb;
    private EvenementService es ;
    private Evenement evenement;
    private EncodedImage enc ;
    private final String urlI = "http://"+VarGlobales.path+"/pi/tech_events/web/images/evenements/";
    
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
        titre.getTextAllStyles().setFgColor(0x0e1b4d);
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
        
        Container userCon = new Container(BoxLayout.x());
        Label utilisateur = new Label(evenement.getUtilisateur().getUsername());
        utilisateur.setUIID("event_label");
        Label utilisateur_lab = new Label("créée par: ");
        FontImage.setMaterialIcon(utilisateur_lab, FontImage.MATERIAL_PERSON);
        //utilisateur_lab.setW(300);
        userCon.addAll(utilisateur_lab, utilisateur);

        Container adresseCon = new Container(BoxLayout.x());
        Label adresse = new Label(evenement.getAdresse());
        adresse.setUIID("event_label");
        Label adresse_lab = new Label("Adresse: ");
        FontImage.setMaterialIcon(adresse_lab, FontImage.MATERIAL_PLACE);
        //adresse_lab.setWidth(300);
        adresseCon.addAll(adresse_lab, adresse);
        
        Container dateCon = new Container(BoxLayout.x());
        Label date = new Label(dateFormater.format(evenement.getDate()));
        date.setUIID("event_label");
        Label date_lab = new Label("Date : ");
        FontImage.setMaterialIcon(date_lab, FontImage.MATERIAL_ACCESS_TIME);
        //date_lab.setWidth(300);
        dateCon.addAll(date_lab, date);

        Container prixCon = new Container(BoxLayout.x());
        Label prix = new Label(evenement.getPrix()+" $");
        prix.setUIID("event_label");
        Label prix_lab = new Label("prix : ");
        FontImage.setMaterialIcon(prix_lab, FontImage.MATERIAL_CREDIT_CARD);
        //prix_lab.setWidth(300);
        prixCon.addAll(prix_lab, prix);
                
        Container billetsCon = new Container(BoxLayout.x());
        Label billets = new Label(evenement.getBillets_restants()+" billets");
        billets.setUIID("event_label");
        Label billets_lab = new Label("Billets : ");
        FontImage.setMaterialIcon(billets_lab, FontImage.MATERIAL_BOOK);
        //billets_lab(300);
        billetsCon.addAll(billets_lab, billets);
        
        
        String categories = "";
        for (Categorie c : evenement.getListCategories()){
            categories += "#"+c.getName()+" ";
        }
        
        Container categoriesCont = new Container(BoxLayout.x());
        SpanLabel categorie_label = new SpanLabel(categories);
        categorie_label.setUIID("event_label");
        Label tag_lab = new Label("tags: ");
        FontImage.setMaterialIcon(tag_lab, FontImage.MATERIAL_LOCAL_OFFER);
        //tag_lab.setWidth(300);
        categoriesCont.addAll(tag_lab, categorie_label);          
        middle.addAll(titre_middle, userCon, adresseCon, dateCon, prixCon, billetsCon, categoriesCont);
        middle.setSameWidth(date_lab,adresse_lab,utilisateur_lab,prix_lab,billets_lab,tag_lab);
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
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                new UpdateEvent().getForm().show();
            });
            Button delete = new Button(" delete",FontImage.MATERIAL_DELETE,"delete");
           
            delete.setUIID("secondary_button");
            delete.addActionListener((l)->{
                if (Fingerprint.isAvailable()){
                    Fingerprint.scanFingerprint("supprimer l'evenement !!",
                        success->{
                            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                            if (es.deleteEvent(evenement)){
                                if (Dialog.show("suppression", "evenement supprimé avec succes ", "ok", null))
                                new EvenementsView().getForm().show();             
                            }
                        },
                            (sender, err, errorCode, errorMessage) ->{
                                Dialog.show("suppression", "erreur !", "ok",null);
                                form.refreshTheme();
                            });
                }
                else {
                    Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                     if (es.deleteEvent(evenement)){
                        if (Dialog.show("suppression", "evenement supprimé avec succes ", "ok", null))
                            new EvenementsView().getForm().show();             
                    }
                    else 
                        ip.dispose();
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
            mapCnt.setCameraPosition(new Coord(lat, lng));
            mapCnt.setPreferredH((Display.getInstance().getDisplayHeight()/4)*3);
            map.add(mapCnt);
        }
        else 
            map.add(new Label("pas de coordonnés poue cette adresse "));
        
        form.add(map);
    }
}