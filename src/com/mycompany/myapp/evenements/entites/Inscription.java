package com.mycompany.myapp.evenements.entites;

import java.util.Date;
import com.mycompany.myapp.Utilisateur;

public class Inscription {
    
    private int id;
    private Evenement evenement;
    private Utilisateur utilisateur;
    private Date dateInscription;

    public Inscription(int id, Evenement evenement, Utilisateur utilisateur, Date dateInscription) {
        this.id = id;
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.dateInscription = dateInscription;
    }
    
    public Inscription( Evenement evenement, Utilisateur utilisateur) {
        this.evenement = evenement;
        this.utilisateur = utilisateur;
    }
    
    public Inscription(){}

    public Inscription(Evenement evenement) {
        this.evenement= evenement;
    }

    public int getId() {
        return id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final Inscription other = (Inscription) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
