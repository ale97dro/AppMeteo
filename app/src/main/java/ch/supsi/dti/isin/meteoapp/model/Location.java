package ch.supsi.dti.isin.meteoapp.model;

import java.util.UUID;

public class Location {
    private UUID Id;
    private String mName;

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

    public Location() {
        Id = UUID.randomUUID();
    }
}