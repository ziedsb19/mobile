package com.mycompany.myapp.evenements.views;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.Utilisateur;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.services.EvenementService;
import java.util.Date;

public class Evenement extends BaseEvent{
    
    private EvenementService es ;
    
    public Evenement(){
        
        es= new EvenementService();
        
        setLayout(new FlowLayout(CENTER, CENTER));
        Button fileB = new Button("+");
        Utilisateur user1 = new Utilisateur();
        user1.setId(1);
        Utilisateur user2 = new Utilisateur();
        user2.setId(5);
        
        com.mycompany.myapp.evenements.entites.Evenement ev2 = new com.mycompany.myapp.evenements.entites.Evenement
        ("test java ", new Date(), "", "hello ", 0, "test addresse ", 10, user1);
        ev2.addCategorie(new Categorie(2,"tes11"));
        ev2.setId(31);
        es.inscriEvent(ev2, user2);
            
        es.deleteEvent(ev2);
        fileB.addActionListener((e)->{
            String file = Capture.capturePhoto();
            com.mycompany.myapp.evenements.entites.Evenement ev = new com.mycompany.myapp.evenements.entites.Evenement
            ("test java", new Date(), "", "testing !", 0, "test addresse", 10, user1);
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
