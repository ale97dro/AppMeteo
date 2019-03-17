package ch.supsi.dti.isin.meteoapp.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.db.MeteoCursorWrapper;
import ch.supsi.dti.isin.meteoapp.db.MeteoHelper;
import ch.supsi.dti.isin.meteoapp.db.MeteoSchema;

public class LocationsHolder {

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;

    public static LocationsHolder get(Context context) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context);

        return sLocationsHolder;
    }

    private LocationsHolder(Context context) {
        SQLiteDatabase mDatabase = new MeteoHelper(context).getWritableDatabase();
        mLocations = new ArrayList<>();

        // Ordine alfabetico
        String sortOrder = MeteoSchema.LocationTable.Cols.NAME + " COLLATE NOCASE ASC";

        // Database query
        Cursor cursor = mDatabase.query(
                MeteoSchema.LocationTable.NAME,
                null, // columns - null selects all columns
                null,
                null,
                null, // groupBy
                null,  // having
                sortOrder  // orderBy
        );

        MeteoCursorWrapper meteo = new MeteoCursorWrapper(cursor);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ch.supsi.dti.isin.meteoapp.model.Location location = meteo.getLocation();
                mLocations.add(location);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public Location getLocation(UUID id) {
        for (Location location : mLocations) {
            if (location.getId().equals(id))
                return location;
        }

        return null;
    }
}
