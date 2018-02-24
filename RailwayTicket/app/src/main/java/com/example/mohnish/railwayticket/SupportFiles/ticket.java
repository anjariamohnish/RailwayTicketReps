package com.example.mohnish.railwayticket.SupportFiles;

import android.graphics.Bitmap;

/**
 * Created by mohnish on 17-02-2018.
 */

public class ticket {

    private String creation, src, sources, des, destination, type, classType, numberOfPassenger, validity;
    private Bitmap qrBitmap;

    public ticket(String creation, String src, String sources, String des, String destination, String type, String classType, String numberOfPassenger, String validity, Bitmap qrBitmap) {
        this.creation = creation;
        this.src = src;
        this.sources = sources;
        this.des = des;
        this.destination = destination;
        this.type = type;
        this.classType = classType;
        this.numberOfPassenger = numberOfPassenger;
        this.validity = validity;
        this.qrBitmap = qrBitmap;
    }

    public String getCreation() {
        return creation;
    }

    public String getSrc() {
        return src;
    }

    public String getSources() {
        return sources;
    }

    public String getDes() {
        return des;
    }

    public String getDestination() {
        return destination;
    }

    public String getType() {
        return type;
    }

    public String getclassType() {
        return classType;
    }

    public String getNumberOfPassenger() {
        return numberOfPassenger;
    }

    public String getValidity() {
        return validity;
    }

    public Bitmap getQrBitmap() {
        return qrBitmap;
    }
}
