package ch.supsi.dti.isin.meteoapp.db;

import android.content.ContentValues;

import ch.supsi.dti.isin.meteoapp.model.Location;

public class MeteoContentValues {
    public static ContentValues getContentValues(Location location) {
        ContentValues values = new ContentValues();
        values.put(MeteoSchema.LocationTable.Cols.UUID, location.getId().toString());
        values.put(MeteoSchema.LocationTable.Cols.NAME, location.getName());
        return values;
    }
}
