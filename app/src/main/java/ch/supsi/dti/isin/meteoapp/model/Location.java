package ch.supsi.dti.isin.meteoapp.model;

import java.io.Serializable;
import java.util.UUID;

public class Location implements Serializable {
    private UUID Id;
    private String mName;
    private double latitude;
    private double longitude;
    private double temperatura;
    private String status;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location(String name) {
        Id = UUID.randomUUID();
        this.mName = name;
    }

    public Location(UUID id, String name) {
        this.Id = id;
        this.mName = name;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location() {
        Id = UUID.randomUUID();
    }
}