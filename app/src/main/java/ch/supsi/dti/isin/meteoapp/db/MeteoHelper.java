package ch.supsi.dti.isin.meteoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}