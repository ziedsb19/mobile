package com.mycompany.myapp.evenements.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.mycompany.myapp.evenements.entites.Evenement;
import com.codename1.io.CharArrayReader;
import com.mycompany.myapp.Utilisateur;
import com.mycompany.myapp.evenements.entites.Categorie;
import com.mycompany.myapp.evenements.entites.Inscription;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Date;

public class EvenementService {

    private ConnectionRequest con;
    
    public EvenementService(){
        con = new ConnectionRequest();
    }
    
    public int addEvent(Evenement event){
        con = new ConnectionRequest();
        List<String> idCat = new ArrayList<>();
        con.setUrl("http://localhost/pi/tech_events/web/app_dev.php/evenement/mobile/add/"+event.getUtilisateur().getId());
        con.setPost(true);
        con.addArgument("titre", event.getTitre());
        con.addArgument("adresse", event.getAdresse());
        con.addArgument("description", event.getDescription());
        con.addArgument("date", event.getDate().toString());
        con.addArgument("prix", ""+event.getPrix());
        con.addArgument("billets", ""+event.getBillets_restants());
        con.addArgument("image", ""+event.getUrl_image());
        if (event.getListCategories().size()!=0){
            for (Categorie c : event.getListCategories()){
                idCat.add(""+c.getId());
            }
            String [] catList = new String[idCat.size()]; 
            idCat.toArray(catList);
            for (String test : idCat){
                System.out.println(test);
            }
            con.addArgument("categorie[]", catList);
        }
        NetworkManager.getInstance().addToQueueAndWait(con);
        return Integer.parseInt(new String(con.getResponseData()));
    }
    
    public Evenement getEventById(int id){
        Evenement evenement ;
        con.setUrl("http://localhost/pi/tech_events/web/app_dev.php/evenement/mobile/event/"+id);
        NetworkManager.getInstance().addToQueueAndWait(con);
        String data = new String(con.getResponseData());
        evenement = parseOneElement(data);
        return evenement;
    }
    
    public List<Evenement> getEvents(){
        con.setUrl("http://localhost/pi/tech_events/web/app_dev.php/evenement/mobile/");
        NetworkManager.getInstance().addToQueueAndWait(con);
        String data = new String(con.getResponseData());
        List<Evenement> events = parseElements(data);
        return events;
    }
    
    private  List<Evenement> parseElements(String data){
     List<Evenement> events = new ArrayList();
     JSONParser j = new JSONParser();
        try {
            List<Map> items = (List)j.parseJSON(new CharArrayReader(data.toCharArray())).get("root");
            for (Map element : items){
                Evenement e = new Evenement();
                e.setTitre((String)element.get("titre"));
                e.setAdresse((String)element.get("adresse"));
                e.setId((int)Math.round((double)element.get("id")));
                e.setUrl_image((String)element.get("urlImage"));
                if (element.get("prix")!= null)
                    e.setPrix((double)element.get("prix"));
                List<Map> categories = (List)element.get("categories");
                Map <String, Object> user = (Map)element.get("user");
                Map <String, Object> date = (Map)element.get("date");
                e.setDate(new Date((long)((double)date.get("timestamp"))*1000));
                Utilisateur u = new Utilisateur((int)((double)user.get("id")),(String)user.get("username") ,
                        (String)user.get("email"), (String)user.get("nomPrenom"), (String)user.get("urlPhotoProfil")
                        , Integer.parseInt((String)user.get("numeroTel")));
                e.setUtilisateur(u);
                for (Map c : categories){
                    Categorie cat = new Categorie((int)((double)c.get("id")), (String)c.get("nom"));
                    e.addCategorie(cat);
                }
                events.add(e);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     return events;
    }

    private Evenement parseOneElement(String data) {
        Evenement evenement = new Evenement();
        JSONParser json = new JSONParser();
        try {
            List<Map> elements = (List)(json.parseJSON(new CharArrayReader(data.toCharArray())).get("root"));
            Map<String,Object> eventEl = elements.get(0);
            List<Map> inscriEl = (List)elements.get(1);
            evenement.setId((int)((double)eventEl.get("id")));
            evenement.setTitre((String)eventEl.get("titre"));
            evenement.setUrl_image((String)eventEl.get("urlImage"));
            evenement.setDescription((String)eventEl.get("description"));
            evenement.setAdresse((String)eventEl.get("adresse"));
            if (eventEl.get("prix") != null)
                evenement.setPrix((double)eventEl.get("prix"));
            if (eventEl.get("billetsRestants") != null)
                evenement.setBillets_restants((int)((double)eventEl.get("billetsRestants")));
            Map<String,Object> dateEl = (Map)eventEl.get("date");
            evenement.setDate(new Date((long)((double)dateEl.get("timestamp"))*1000));
            Map<String,Object> user = (Map)eventEl.get("user");
            evenement.setUtilisateur(new Utilisateur((int)((double)user.get("id")),(String)user.get("username") ,
                        (String)user.get("email"), (String)user.get("nomPrenom"), (String)user.get("urlPhotoProfil")
                        , Integer.parseInt((String)user.get("numeroTel"))));
            List<Map> categories = (List)eventEl.get("categories");
            for (Map<String,Object> c : categories)
                evenement.addCategorie( new Categorie((int)((double)c.get("id")), (String)c.get("nom")));
              
            
            for (Map<String, Object> in : inscriEl){
                Inscription inscri = new Inscription();
                inscri.setId(1);
                
                inscri.setId((int)((double)in.get("id")));
                inscri.setEvenement(evenement);
                Map<String,Object> userInscri = (Map)in.get("user");
                inscri.setUtilisateur(new Utilisateur((int)((double)userInscri.get("id")),(String)userInscri.get("username") ,
                        (String)userInscri.get("email"), (String)userInscri.get("nomPrenom"), (String)userInscri.get("urlPhotoProfil")
                        , Integer.parseInt((String)userInscri.get("numeroTel"))));
                Map<String,Object> dateInscri = (Map)in.get("dateInscription");
                inscri.setDateInscription(new Date((long)((double)dateInscri.get("timestamp"))*1000));
                evenement.addInscription(inscri);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return evenement;        
    }
}
