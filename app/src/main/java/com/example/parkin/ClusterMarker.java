package com.example.parkin;
import com.example.parkin.DB.GarageDetails;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.Objects;

public class ClusterMarker implements ClusterItem{
    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;
    private GarageDetails garage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterMarker that = (ClusterMarker) o;
        return Objects.equals(garage.getAddressId(), that.garage.getGarageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    public ClusterMarker(LatLng position, String title, String snippet, int iconPicture, GarageDetails garage) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconPicture = iconPicture;
        this.garage=garage;
    }

    public ClusterMarker() {
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public GarageDetails getGarage() {
        return garage;
    }

    public void setGarage(GarageDetails garage) {
        this.garage = garage;
    }

    public int getIconPicture() {
        return iconPicture;
    }

    public void setIconPicture(int iconPicture) {
        this.iconPicture = iconPicture;
    }
}
