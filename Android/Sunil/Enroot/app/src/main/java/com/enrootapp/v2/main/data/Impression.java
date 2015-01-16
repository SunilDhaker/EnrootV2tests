package com.enrootapp.v2.main.data;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sdhaker on 15-01-2015.
 */
public class Impression {

    Bitmap impression ;
    String geoname ;
    long latitude ;
    long longitute ;

    ArrayList<String> taggedPeople ;
    ArrayList<String> notifiedPeople ;
    ArrayList<String> discoverers ;
    String owner;
    Date time ;
    String type;


    public  Impression(){

    }


    public String getType() {
        return type;
    }

    public void setImpression(Bitmap impression) {
        this.impression = impression;
    }

    public void setGeoname(String geoname) {
        this.geoname = geoname;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setLongitute(long longitute) {
        this.longitute = longitute;
    }

    public void setTaggedPeople(ArrayList<String> taggedPeople) {
        this.taggedPeople = taggedPeople;
    }

    public void setNotifiedPeople(ArrayList<String> notifiedPeople) {
        this.notifiedPeople = notifiedPeople;
    }

    public void setDiscoverers(ArrayList<String> discoverers) {
        this.discoverers = discoverers;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getImpression() {
        return impression;
    }

    public String getGeoname() {
        return geoname;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitute() {
        return longitute;
    }

    public ArrayList<String> getTaggedPeople() {
        return taggedPeople;
    }

    public ArrayList<String> getNotifiedPeople() {
        return notifiedPeople;
    }

    public ArrayList<String> getDiscoverers() {
        return discoverers;
    }

    public String getOwner() {
        return owner;
    }

    public Date getTime() {
        return time;
    }

}
