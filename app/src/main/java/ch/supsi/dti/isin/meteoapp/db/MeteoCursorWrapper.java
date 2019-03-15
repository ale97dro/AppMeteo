package ch.supsi.dti.isin.meteoapp.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.model.Location;

public class MeteoCursorWrapper extends CursorWrapper {

    public MeteoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Location getLocation() {
        String id = getString(getColumnIndex(MeteoSchema.LocationTable.Cols.UUID));
        String name = getString(getColumnIndex(MeteoSchema.LocationTable.Cols.NAME));

        return new Location(UUID.fromString(id), name);
    }
}
