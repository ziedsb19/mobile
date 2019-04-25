package com.mycompany.myapp;

public class VarGlobales {
    
    private static Utilisateur utilisateur = null;
    
    public static Utilisateur getUtilisateur(){
        return utilisateur;
    }
    
    public static void setUtilisateur(Utilisateur utilisateur){
        VarGlobales.utilisateur = utilisateur;
    }
}
