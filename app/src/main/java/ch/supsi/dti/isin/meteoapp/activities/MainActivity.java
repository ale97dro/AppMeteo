package ch.supsi.dti.isin.meteoapp.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.db.MeteoContentValues;
import ch.supsi.dti.isin.meteoapp.db.MeteoCursorWrapper;
import ch.supsi.dti.isin.meteoapp.db.MeteoHelper;
import ch.supsi.dti.isin.meteoapp.db.MeteoSchema;
import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MainActivity extends SingleFragmentActivity {
    private SQLiteDatabase mDatabase;
    private ch.supsi.dti.isin.meteoapp.model.Location realLocation = new ch.supsi.dti.isin.meteoapp.model.Location();

    @Override
    protected Fragment createFragment() {
        //return new ListFragment();
        return ListFragment.newInstance(realLocation);
    }


    private static final String TAG = "AppMeteo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_single_fragment);

        Context context = getApplicationContext();
        mDatabase = new MeteoHelper(context).getWritableDatabase();

        insertData();
        readData();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted");
            requestPermissions();
        } else {
            Log.i(TAG, "Permission granted");
            startLocationListener();
        }

        mDatabase.close();
    }

    private void insertData() {
        ch.supsi.dti.isin.meteoapp.model.Location location = new ch.supsi.dti.isin.meteoapp.model.Location("locationName");
        ContentValues values = MeteoContentValues.getContentValues(location);
        mDatabase.insert(MeteoSchema.LocationTable.NAME, null, values);
    }

    private void readData() {
        String res = "Data:";

        MeteoCursorWrapper cursor = queryData(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ch.supsi.dti.isin.meteoapp.model.Location location = cursor.getLocation();
                res += "\n" + location.getName();
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    private MeteoCursorWrapper queryData(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MeteoSchema.LocationTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null,  // having
                null  // orderBy
        );
        return new MeteoCursorWrapper(cursor);
    }

    private void startLocationListener() {
        long mLocTrackingInterval = 1000 * 5; // 5 sec
        float trackingDistance = 0;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(this)
                .location()
                .continuous()
                .config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Log.i(TAG, "Location: ciao " + location);
                        realLocation.setName("prova");
                    }
                });
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            startLocationListener();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startLocationListener();
                return;
            }
        }
    }
}
