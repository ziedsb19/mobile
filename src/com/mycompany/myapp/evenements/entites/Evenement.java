package com.mycompany.myapp.evenements.entites;

import java.util.Date;
import java.util.HashSet;
import com.mycompany.myapp.Utilisateur;

    public class Evenement {
        private int id;
        private String titre;
        private Date date;
        private String url_image ;
        private String description;
        private double prix;
        private String adresse;
        private int billets_restants;
        private int disponibilite;
        private Date date_modification;
        private Utilisateur utilisateur;
        private HashSet<Image> listImages ;
        private HashSet<Categorie> listCategories;
        private HashSet<Inscription> listInscriptions;
    
    public Evenement(int id, String titre, Date date, String url_image, String description, double prix, String adresse, int billets_restants, int disponibilite, Date date_modification, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.url_image=url_image;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.billets_restants = billets_restants;
        this.disponibilite = disponibilite;
        this.date_modification = date_modification;
        this.utilisateur=utilisateur;
        listImages = new HashSet<>();
        listCategories = new HashSet<>();
        listInscriptions= new HashSet<>();
    }
    
    public Evenement(int id, String titre, Date date, String url_image, String description, double prix, String adresse, int billets_restants, int disponibilite, Date date_modification) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.url_image=url_image;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.billets_restants = billets_restants;
        this.disponibilite = disponibilite;
        this.date_modification = date_modification;
        listImages = new HashSet<>();
        listCategories = new HashSet<>();
        listInscriptions= new HashSet<>();
    }
    
    public Evenement( String titre, Date date, String url_image, String description, double prix, String adresse, int billets_restants, Utilisateur utilisateur) {
        this.titre = titre;
        this.date = date;
        this.url_image=url_image;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.billets_restants = billets_restants;
        this.utilisateur= utilisateur;
        listImages = new HashSet<>();
        listCategories = new HashSet<>();
        listInscriptions= new HashSet<>();
    }
        

    public Evenement() {
        listImages = new HashSet<>();
        listCategories = new HashSet<>();
        listInscriptions= new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl_image() {
        return url_image;
    }
   
    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getBillets_restants() {
        return billets_restants;
    }

    public int getDisponibilite() {
        return disponibilite;
    }

    public Date getDate_modification() {
        return date_modification;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    
    public HashSet<Image> getListImages() {
        return listImages;
    }

    public HashSet<Categorie> getListCategories() {
        return listCategories;
    }
    
    public HashSet<Inscription> getListInscriptions() {
        return listInscriptions;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setBillets_restants(int billets_restants) {
        this.billets_restants = billets_restants;
    }

    public void setDisponibilite(int disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setDate_modification(Date date_modification) {
        this.date_modification = date_modification;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    

    public void setListImages(HashSet<Image> listImages) {
        this.listImages = listImages;
    }

    public void setListCategories(HashSet<Categorie> listCategories) {
        this.listCategories = listCategories;
    }

    public void setListInscriptions(HashSet<Inscription> listInscriptions) {
        this.listInscriptions = listInscriptions;
    }
    
    public void addImage (Image image){
        if(!this.listImages.contains(image))
            this.listImages.add(image);
    }

    public void removeImage (Image image){
        this.listImages.remove(image);
    }
    
    public void addCategorie (Categorie categorie){
        if(!this.listCategories.contains(categorie))
            this.listCategories.add(categorie);
    }

    public void removeCategorie (Categorie categorie){
        this.listCategories.remove(categorie);
    }
    
    public void addInscription (Inscription inscription){
        if(!this.listInscriptions.contains(inscription))
            this.listInscriptions.add(inscription);
    }

    public void removeInscription (Inscription inscription){
        this.listInscriptions.remove(inscription);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override 
    public String toString(){
        return "///"+titre+"///"+adresse+"///"+date.toString()+"////"+utilisateur.getUsername()+"///"+prix+"///"+billets_restants;
    } 
    
}