package com.example.dell.addbarcodetolistview;

/**
 * Created by dell on 3/16/2018.
 */

public class Barcode {

    String name;
    boolean selected;


    public Barcode(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
