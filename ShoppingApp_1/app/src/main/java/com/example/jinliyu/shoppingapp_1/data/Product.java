package com.example.jinliyu.shoppingapp_1.data;

/**
 * Created by jinliyu on 4/15/18.
 */

public class Product {

    String pid;
    String pname;
    String quantity;
    String price;
    String pdescription;
    String pimage;

    public Product(String pid, String pname, String quantity, String price, String pdescription, String pimage) {
        this.pid = pid;
        this.pname = pname;
        this.quantity = quantity;
        this.price = price;
        this.pdescription = pdescription;
        this.pimage = pimage;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
