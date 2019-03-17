package ch.supsi.dti.isin.meteoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.List;

public class MeteoHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "meteo.db";

    public MeteoHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MeteoSchema.LocationTable.NAME + "("
                + " _id integer primary key autoincrement, " + MeteoSchema.LocationTable.Cols.UUID + ", "
                + MeteoSchema.LocationTable.Cols.NAME
                + ")"
        );

        seedDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Insert a new location to db
    private void seedDatabase(SQLiteDatabase db) {
        List<String> places = Arrays.asList("Antalya", "Bangkok", "Delhi", "Dubai", "Guangzhou",
                "Hong Kong", "Istanbul", "Kuala Lumpur", "Londra", "Macau", "Mumbai", "New York",
                "Parigi", "Phuket", "Praga", "Roma", "Shenzhen", "Singapore", "Taipei", "Tokyo");
        for (String place : places) {
            ch.supsi.dti.isin.meteoapp.model.Location location = new ch.supsi.dti.isin.meteoapp.model.Location(place);
            ContentValues values = MeteoContentValues.getContentValues(location);
            db.insert(MeteoSchema.LocationTable.NAME, null, values);
        }
    }

    public void insertData(SQLiteDatabase db) {

    }
}