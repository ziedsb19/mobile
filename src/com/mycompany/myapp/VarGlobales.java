package com.mycompany.myapp;

public class VarGlobales {
    
    private static Utilisateur utilisateur = null;
    private static int eventId;
    
    
    public static Utilisateur getUtilisateur(){
        return utilisateur;
    }
    
    public static void setUtilisateur(Utilisateur utilisateur){
        VarGlobales.utilisateur = utilisateur;
    }
    
     public static int getEventId(){
        return eventId;
    }
     
    public static void setEventId(int id){
        eventId = id;
    }; 
}
