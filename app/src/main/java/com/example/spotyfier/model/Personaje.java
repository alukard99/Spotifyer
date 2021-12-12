package com.example.spotyfier.model;

public class Personaje {
    private int id;
    private String fullname;
    private String title;
    private String family;
    private String imageurl;

    public Personaje(int id, String fullname, String title, String family, String imageurl) {
        this.id = id;
        this.fullname = fullname;
        this.title = title;
        this.family = family;
        this.imageurl = imageurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
