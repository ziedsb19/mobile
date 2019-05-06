package com.mycompany.myapp.evenements.views;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.util.Date;
import java.util.List;

public class UpdateEvent extends BaseEvent {
    
    //FIXME: ability to delete image

    private Form form;
    private Toolbar tb;
    private TextField titre;
    private TextField adresse;
    private Picker date;
    private TextField description;
    private Button photo;
    private Button valider;
    private String file;
    private EvenementService es  ;
    private Evenement evenement ; 
    
    public UpdateEvent(){
        es = new EvenementService();
        evenement = es.getEventById(VarGlobales.getEventId());
        setForm();
    }
    
    private void setForm(){
        form = new Form("màj evenement", BoxLayout.y());
        tb = form.getToolbar();
        addToolBar(tb);
        tb.addMaterialCommandToLeftBar("retour", FontImage.MATERIAL_ARROW_BACK, (l)->{
            new OneEvent().getForm().show();
        });
        //*************************************************
        titre = new TextField(evenement.getTitre(), "titre de l'event");
        adresse = new TextField(evenement.getAdresse(), "adresse de l'event");
        date = new Picker();
        date.setDate(evenement.getDate());
        description = new TextField(evenement.getDescription(),"description de l'evenements");
        description.setRows(2);
        
        photo = new Button(" ajouter image",FontImage.MATERIAL_CAMERA,"photo");
        photo.setUIID("primary_outline_button");
        Label photo_helper = new Label();
        if (evenement.getUrl_image() != null)
            photo_helper.setText("image existante");
        photo_helper.addPointerPressedListener((l)->{
            if (photo_helper.getText().equals("image ajouté X")){
                file = null;
                photo_helper.setText(null);
            }
        });
        photo.addActionListener((event)->{
            file = Capture.capturePhoto();
            if (file != null){
                photo_helper.setText("image ajouté X");
            }
            else{ 
                photo_helper.setText(null);
            }
            form.revalidate();
        });
        valider = new Button("valider");
        valider.setUIID("primary_button");
        valider.addActionListener((l)->{
            if (testArgs()){
                evenement.setTitre(titre.getText());
                evenement.setAdresse(adresse.getText());
                evenement.setDate(date.getDate());
                evenement.setDescription(description.getText());
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                if (es.updateEvent(evenement)){
                    if (file != null)
                        es.uploadImage(file, evenement.getId());
                    else if (file == null && evenement.getUrl_image()!=null) 
                        es.deleteImage(evenement);
                    if (Dialog.show("mis à jour", "mis à jour de l'evenement avec succés", "ok", null))
                        new OneEvent().getForm().show();
                }
            }
        });
        //*******************************************************
        
        Container photo_label = new Container(BoxLayout.x());
        photo_label.addAll(photo, photo_helper);
        form.addAll(new Label("Informations générales :"),titre, adresse, date ,description);
        setCategories();
        form.addAll(new Label("image de l'evenement :"),photo_label,valider);
    }
    
        public boolean testArgs(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");    
        if (titre.getText() == null ||titre.getText().isEmpty() || adresse.getText() == null || adresse.getText().isEmpty() || date.getText() == null  )
        {
            Dialog.show("erreur !", "vous devez remplissez tous les champs obligatoires (titre, adresse, date)", "ok", null);
            return false;
        }
        if (formatter.format(date.getDate()).compareTo(formatter.format(new Date()))<=0){
            Dialog.show("erreur !", "date invalide ", "ok", null); 
            return false;
        }
        return true;
    }
    
    private void setCategories() {
        Label catTitre = new Label("categories :");
        Container catCon = new Container(BoxLayout.y());
        List<Categorie> listC= es.getCategories();
        for (Categorie c : listC){
            CheckBox check = new CheckBox(c.getName());
            if (evenement.getListCategories().contains(c))
                check.setSelected(true);
            check.addActionListener((e)->{
                if (check.isSelected())
                    evenement.addCategorie(c);
                else 
                    evenement.removeCategorie(c);
            });
            catCon.add(check);
        }
        form.addAll(catTitre,catCon);
    }
    
    public Form getForm(){
        return form;
    }
}
