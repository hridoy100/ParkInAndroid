package com.example.parkin;

import com.google.android.gms.maps.model.LatLng;

public class GarageObject {
    private int garage_id;
    private LatLng pos;
    private Garage garage;

    public GarageObject() {
    }

    public GarageObject(int garage_id, LatLng pos, Garage garage) {
        this.garage_id = garage_id;
        this.pos = pos;
        this.garage = garage;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public int getGarage_id() {
        return garage_id;
    }

    public void setGarage_id(int garage_id) {
        this.garage_id = garage_id;
    }

    public LatLng getPos() {
        return pos;
    }

    public void setPos(LatLng pos) {
        this.pos = pos;
    }
}
