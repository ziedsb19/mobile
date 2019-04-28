package com.mycompany.myapp;

import com.codename1.ui.util.Resources;

public class VarGlobales {
    
    private static Utilisateur utilisateur = null;
    private static int eventId;
    private static Resources theme;
    
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
    
    public static void setTheme(Resources theme){
        VarGlobales.theme= theme;
    }
    
    public static Resources getTheme(){
        return theme;
    }
}
