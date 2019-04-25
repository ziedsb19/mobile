package com.mycompany.myapp.evenements.entites;

public class Image {
    private int id;
    private String url;
    private String alt;
    private Evenement evenement;

    public Image(String url, String alt, Evenement evenement) {
        this.url = url;
        this.alt = alt;
        this.evenement= evenement;
    }

    public Image(int id, String url, String alt) {
        this.id = id;
        this.url = url;
        this.alt = alt;
    }

    public Image(int id, String url, String alt, Evenement evenement) {
        this.id = id;
        this.url = url;
        this.alt = alt;
        this.evenement = evenement;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAlt() {
        return alt;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
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
        final Image other = (Image) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
