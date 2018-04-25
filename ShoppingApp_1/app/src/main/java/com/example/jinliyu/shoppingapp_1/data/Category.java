package com.example.jinliyu.shoppingapp_1.data;

/**
 * Created by jinliyu on 4/14/18.
 */

public class Category {
    String name;
    String image;
    String cid;
String description;


    public Category(String name, String image, String cid, String description) {
        this.name = name;
        this.image = image;
        this.cid = cid;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
