package com.example.parkin;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
public class ClusterMarker implements ClusterItem{
    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;
    private GarageObject garage;
    public ClusterMarker(LatLng position, String title, String snippet, int iconPicture,GarageObject garage) {
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

    public GarageObject getGarage() {
        return garage;
    }

    public void setGarage(GarageObject garage) {
        this.garage = garage;
    }

    public int getIconPicture() {
        return iconPicture;
    }

    public void setIconPicture(int iconPicture) {
        this.iconPicture = iconPicture;
    }
}
