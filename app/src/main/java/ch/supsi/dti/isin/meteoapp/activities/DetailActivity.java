package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.HTTPrequest.MeteoTask;
import ch.supsi.dti.isin.meteoapp.HTTPrequest.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailActivity extends SingleFragmentActivity implements OnTaskCompleted {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";
    private static final String EXTRA_LOCATION_NAME = "ch.supsi.dti.isin.meteoapp.location_name";
    private static final String EXTRA_LOCATION_LATITUDE = "ch.supsi.dti.isin.meteoapp.location_latitude";
    private static final String EXTRA_LOCATION_LONGITUDE = "ch.supsi.dti.isin.meteoapp.location_longitude";

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

        //location che mi passa l'intent
        Location location = new Location();
        location.setId(locationId);
        location.setName((String)getIntent().getSerializableExtra(EXTRA_LOCATION_NAME));
        location.setLatitude((double)getIntent().getSerializableExtra(EXTRA_LOCATION_LATITUDE));
        location.setLongitude((double)getIntent().getSerializableExtra(EXTRA_LOCATION_LONGITUDE));

        //creo task per richiesta HTTP
        MeteoTask t = new MeteoTask(DetailActivity.this, location);
        t.execute();
        return new DetailLocationFragment().newInstance(location);
    }

    @Override
    public void onTaskCompleted(Location location) {
        TextView lat = findViewById(R.id.displayedLatitude);
        TextView lon = findViewById(R.id.displayedLongitude);
        TextView temp = findViewById(R.id.displayedTemperature);
        TextView desc = findViewById(R.id.displayedDescription);
        String icon=location.getIcon();
        DecimalFormat decimalFormat=new DecimalFormat("#.0");

        lat.setText("Lat: " + Double.toString(location.getLatitude()));
        lon.setText("Long: " + Double.toString(location.getLongitude()));
        temp.setText(decimalFormat.format(location.getTemperature())+"°");

        String locationStatus = location.getStatus();

        locationStatus = Character.toString(Character.toUpperCase(locationStatus.charAt(0))) + locationStatus.substring(1);

        desc.setText(locationStatus);

        ImageView imageView = findViewById(R.id.detailImageView);

        switch (icon)
        {
            case "01n":
                imageView.setImageResource(R.drawable.moon);
                break;
            case "01d":
                imageView.setImageResource(R.drawable.sun);
                break;
            case "02n":
                imageView.setImageResource(R.drawable.mooncloud);
                break;
            case "02d":
                imageView.setImageResource(R.drawable.sunnycloud);
            case "03n":
            case "03d":
                imageView.setImageResource(R.drawable.cloud);
                break;
            case "04n":
            case "04d":
                imageView.setImageResource(R.drawable.verycloud);
                break;
            case "09n":
            case "10n":
            case "09d":
            case "10d":
                imageView.setImageResource(R.drawable.rainy);
                break;
            case "11n":
            case "11d":
                imageView.setImageResource(R.drawable.thunderstorm);
                break;
            case "13n":
            case "13d":
                imageView.setImageResource(R.drawable.snowing);
                break;
            case "50n":
            case "50d":
                imageView.setImageResource(R.drawable.windy);
                break;
            default:
                imageView.setImageResource(R.drawable.therock);
                break;
        }
    }
}
