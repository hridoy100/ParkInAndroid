package com.example.parkin;

import com.example.parkin.DB.GarageDetails;
import com.google.android.gms.maps.model.LatLng;

public class GarageObject {
    private int garage_id;
    private LatLng pos;
    private GarageDetails garage;

    public GarageObject() {
    }

    public GarageObject(int garage_id, LatLng pos, GarageDetails garage) {
        this.garage_id = garage_id;
        this.pos = pos;
        this.garage = garage;
    }

    public GarageDetails getGarage() {
        return garage;
    }

    public void setGarage(GarageDetails garage) {
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
