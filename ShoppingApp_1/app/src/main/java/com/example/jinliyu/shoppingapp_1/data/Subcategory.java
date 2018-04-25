package com.example.jinliyu.shoppingapp_1.data;

/**
 * Created by jinliyu on 4/15/18.
 */

public class Subcategory {
    String subcateId;
    String subcateNanme;
    String subcateDescription;
    String subcateImage;

    public Subcategory(String subcateId, String subcateNanme, String subcateDescription, String subcateImage) {
        this.subcateId = subcateId;
        this.subcateNanme = subcateNanme;
        this.subcateDescription = subcateDescription;
        this.subcateImage = subcateImage;
    }

    public String getSubcateId() {
        return subcateId;
    }

    public void setSubcateId(String subcateId) {
        this.subcateId = subcateId;
    }

    public String getSubcateNanme() {
        return subcateNanme;
    }

    public void setSubcateNanme(String subcateNanme) {
        this.subcateNanme = subcateNanme;
    }

    public String getSubcateDescription() {
        return subcateDescription;
    }

    public void setSubcateDescription(String subcateDescription) {
        this.subcateDescription = subcateDescription;
    }

    public String getSubcateImage() {
        return subcateImage;
    }

    public void setSubcateImage(String subcateImage) {
        this.subcateImage = subcateImage;
    }
}
