package ch.supsi.dti.isin.meteoapp.model;

import java.io.Serializable;
import java.util.UUID;

public class Location implements Serializable {
    private UUID Id;
    private String name;
    private double latitude;
    private double longitude;
    private double temperature;
    private String status;
    private String icon;


    public Location() {
        Id = UUID.randomUUID();
    }

    public Location(String name) {
        Id = UUID.randomUUID();
        this.name = name;
    }

    public Location(UUID id, String name) {
        this.Id = id;
        this.name = name;
    }


    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}