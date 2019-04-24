package com.mycompany.myapp.evenements.views;

import com.codename1.capture.Capture;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.Utilisateur;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.io.IOException;
import java.util.Date;

public class Evenement extends BaseEvent{
    
    private EvenementService es ;
    
    public Evenement(){
        
        es= new EvenementService();
        
        setLayout(new FlowLayout(CENTER, CENTER));
        Button fileB = new Button("+");
        fileB.addActionListener((e)->{
            String file = Capture.capturePhoto();
            Utilisateur user1 = new Utilisateur();
            user1.setId(5);
            com.mycompany.myapp.evenements.entites.Evenement ev = new com.mycompany.myapp.evenements.entites.Evenement
            ("test java", new Date(), "", "", 403.5, "test addresse", 10, user1);
            //ev.addCategorie(new Categorie(5,"tes11"));
            //ev.addCategorie(new Categorie(3,"tes22"));
            int eventId = es.addEvent(ev);
            if (file != null){
               
               es.uploadImage(file, eventId);
            }
            System.out.println(eventId);    
        });
        
        add(fileB);
    }
}
