package com.example.parkin.DB;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SpaceDetails {
    int spaceid;
    int garageid;
    int spacesize;
    String position;
    Calendar starttime1;
    Calendar getStarttime2;
    String availability;

    public SpaceDetails(int spaceid, int garageid, int spacesize, String position, Calendar starttime1, Calendar getStarttime2, String availability) {
        this.spaceid = spaceid;
        this.garageid = garageid;
        this.spacesize = spacesize;
        this.position = position;
        this.starttime1 = starttime1;
        this.getStarttime2 = getStarttime2;
        this.availability = availability;
    }

    public SpaceDetails() {
    }

    public int getSpaceid() {
        return spaceid;
    }

    public void setSpaceid(int spaceid) {
        this.spaceid = spaceid;
    }

    public int getGarageid() {
        return garageid;
    }

    public void setGarageid(int garageid) {
        this.garageid = garageid;
    }

    public int getSpacesize() {
        return spacesize;
    }

    public void setSpacesize(int spacesize) {
        this.spacesize = spacesize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Calendar getStarttime1() {
        return starttime1;
    }

    public void setStarttime1(Calendar starttime1) {
        this.starttime1 = starttime1;
    }

    public Calendar getGetStarttime2() {
        return getStarttime2;
    }

    public void setGetStarttime2(Calendar getStarttime2) {
        this.getStarttime2 = getStarttime2;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
