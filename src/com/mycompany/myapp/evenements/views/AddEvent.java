package com.mycompany.myapp.evenements.views;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import static com.codename1.ui.TextArea.DECIMAL;
import static com.codename1.ui.TextArea.NUMERIC;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.VarGlobales;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.util.Date;
import java.util.List;

public class AddEvent extends BaseEvent{
    
    //FIXME: date avant now()
    
    //TODO: supprimer image
    
    private Form form;
    private Toolbar tb;
    private TextField titre;
    private TextField adresse;
    private Picker date;
    private TextField prix;
    private TextField billetsRestants;
    private TextField description;
    private Button photo;
    private Button valider;
    private String file;
    private EvenementService es  ;
    private Evenement evenement ; 
    
    public AddEvent(){
        evenement = new Evenement();
        setForm();
    }
    
    public void setForm(){
        form = new Form("ajouter votre evenement", BoxLayout.y());
        tb = form.getToolbar();
        es = new EvenementService();
        addToolBar(tb);
        //********************************************************
        titre = new TextField(null, "titre de l'event");
        adresse = new TextField(null, "adresse de l'event");
        date = new Picker();
        description = new TextField(null,"description de l'evenements");
        description.setRows(2);
        prix = new TextField(null, "prix");
        prix.setConstraint(DECIMAL);
        billetsRestants = new TextField(null, "nombre de places");
        billetsRestants.setConstraint(NUMERIC);
        photo = new Button(" ajouter image",FontImage.MATERIAL_CAMERA,"photo");
        photo.setUIID("primary_outline_button");
        Label photo_helper = new Label();
        
        photo_helper.addPointerPressedListener((l)->{
            file = null;
            photo_helper.setText(null);
        });
        
        photo.addActionListener((event)->{
            FileChooser.showOpenDialog("image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", l->{
                try {
                    file = (String)l.getSource();
                    if (file != null){
                        photo_helper.setText("image ajouté X");
                    }
                    else{ 
                        photo_helper.setText(null);
                    }
                    form.revalidate();  
                }
                catch (Exception ex){}
            });
            //file = Capture.capturePhoto();
        });
        
        valider = new Button("valider");
        valider.setUIID("primary_button");
        valider.addActionListener((event)->{
            if (testArgs()){
                evenement.setTitre(titre.getText());
                evenement.setAdresse(adresse.getText());
                evenement.setDescription(description.getText());
                if (!prix.getText().isEmpty())
                    evenement.setPrix(Double.parseDouble(prix.getText()));
                if (!billetsRestants.getText().isEmpty())
                    evenement.setBillets_restants(Integer.parseInt(billetsRestants.getText()));
                evenement.setDate(date.getDate());
                evenement.setUtilisateur(VarGlobales.getUtilisateur());
                //******************************************************************
                Dialog ip = new InfiniteProgress().showInfiniteBlocking();
                int eventId = es.addEvent(evenement);
                if (eventId != 0){
                    if (file != null)
                        es.uploadImage(file, eventId);
                    if (Dialog.show("success", "evenement ajouté", "ok", null)){
                        VarGlobales.setEventId(eventId);
                        new OneEvent().getForm().show();
                    }
                        
                }
                
            }
        });
        //********************************************************
        Container prix_billets = new Container(new GridLayout(1, 2));
        Container photo_label = new Container(BoxLayout.x());
        prix_billets.addAll(prix, billetsRestants);
        photo_label.addAll(photo, photo_helper);
        form.addAll(new Label("Informations générales :"),titre, adresse, date ,description, prix_billets);
        setCategories();
        form.addAll(new Label("image de l'evenement :"),photo_label,valider);
    }
    
    public Form getForm(){
        return form;
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
        if (!prix.getText().isEmpty()){
            try {
                double prix_test = Double.parseDouble(prix.getText());
            }
            catch (Exception e){
                Dialog.show("erreur !", "prix invalide ", "ok", null);
                return false;
            }
            if (Double.parseDouble(prix.getText())<0){
                Dialog.show("erreur !", "prix ne doit pas etre inferieur à 0 ", "ok", null);
                return false;    
            }
        }
        if (!billetsRestants.getText().isEmpty()){
            try {
                Integer.parseInt(billetsRestants.getText());
            }
            catch (Exception e){
                Dialog.show("erreur !", "nombre de place invalide ", "ok", null);
                return false;
            }
            if (Integer.parseInt(billetsRestants.getText())<0){
                Dialog.show("erreur !", "nombre ne doit pas etre inferieur à 0 ", "ok", null);
                return false;    
            }
        }
        return true;
    }

    private void setCategories() {
        Label catTitre = new Label("categories :");
        Container catCon = new Container(BoxLayout.y());
        List<Categorie> listC= es.getCategories();
        for (Categorie c : listC){
            CheckBox check = new CheckBox(c.getName());
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
}
