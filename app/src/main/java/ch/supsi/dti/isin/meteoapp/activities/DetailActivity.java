package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailActivity extends SingleFragmentActivity {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";
    private static final String EXTRA_LOCATION_NAME = "ch.supsi.dti.isin.meteoapp.location_name";
    private static final String EXTRA_LOCATION_LATITUDE = "ch.supsi.dti.isin.meteoapp.location_latitude";
    private static final String EXTRA_LOCATION_LONGITUDE = "ch.supsi.dti.isin.meteoapp.location_longitude";

//    public static Intent newIntent(Context packageContext, UUID locationId) {
//        Intent intent = new Intent(packageContext, DetailActivity.class);
//        intent.putExtra(EXTRA_LOCATION_ID, locationId);
//        return intent;
//    }

    public static Intent newIntent(Context packageContext, Location location) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, location.getId());
        intent.putExtra(EXTRA_LOCATION_NAME, location.getName());
        intent.putExtra(EXTRA_LOCATION_LATITUDE, location.getLatitude());
        intent.putExtra(EXTRA_LOCATION_LONGITUDE, location.getLongitude());
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID locationId = (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);

        Location location = new Location();
        location.setId(locationId);
        location.setName((String)getIntent().getSerializableExtra(EXTRA_LOCATION_NAME));
        location.setLatitude((double)getIntent().getSerializableExtra(EXTRA_LOCATION_LATITUDE));
        location.setLongitude((double)getIntent().getSerializableExtra(EXTRA_LOCATION_LONGITUDE));

        //return new DetailLocationFragment().newInstance(locationId);
        return new DetailLocationFragment().newInstance(location);
    }
}
