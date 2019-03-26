package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.HTTPrequest.MyTask;
import ch.supsi.dti.isin.meteoapp.HTTPrequest.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailActivity extends SingleFragmentActivity implements OnTaskCompleted {
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

        //location che mi passa l'intent
        Location location = new Location();
        location.setId(locationId);
        location.setName((String)getIntent().getSerializableExtra(EXTRA_LOCATION_NAME));
        location.setLatitude((double)getIntent().getSerializableExtra(EXTRA_LOCATION_LATITUDE));
        location.setLongitude((double)getIntent().getSerializableExtra(EXTRA_LOCATION_LONGITUDE));

        //creo task per richiesta HTTP
        MyTask t = new MyTask(DetailActivity.this, location);
        t.execute();
        //return new DetailLocationFragment().newInstance(locationId);
        return new DetailLocationFragment().newInstance(location);
    }

    @Override
    public void onTaskCompleted(Location location) {
        TextView lat = findViewById(R.id.displayedLatitude);
        TextView lon = findViewById(R.id.displayedLongitude);
        TextView temp = findViewById(R.id.displayedTemperature);
        TextView desc = findViewById(R.id.displayedDescription);
        String icon=location.getIcon();

        lat.setText(Double.toString(location.getLatitude()));
        lon.setText(Double.toString(location.getLongitude()));
        temp.setText(Double.toString(location.getTemperatura()));
        desc.setText(location.getStatus());

        ImageView imageView = findViewById(R.id.detailImageView);

        if(icon.equals("01n")) {
            //clear
            imageView.setImageResource(R.drawable.moon);
        }else if(icon.equals("01d")) {
            //clear
            imageView.setImageResource(R.drawable.sun);
        }else if(icon.equals("02n")){
            //little cloudy with sun
            imageView.setImageResource(R.drawable.mooncloud);
        }else if(icon.equals("02d")){
            //little cloudy with sun
            imageView.setImageResource(R.drawable.sunnycloud);
        }else if((icon.equals("03n"))||(icon.equals("03d"))){
            //cloudy no sun
            imageView.setImageResource(R.drawable.cloud);
        }else if((icon.equals("04n"))||(icon.equals("04d"))){
            //cloud cloud
            imageView.setImageResource(R.drawable.verycloud);
        }else if((icon.equals("09n"))||(icon.equals("10n"))||(icon.equals("09d"))||(icon.equals("10d"))){
            //rain
            imageView.setImageResource(R.drawable.rainy);
        }else if((icon.equals("11n"))||(icon.equals("11d"))){
            //thunder
            imageView.setImageResource(R.drawable.thunderstorm);
        }else if((icon.equals("13n"))||(icon.equals("13d"))){
            //snow
            imageView.setImageResource(R.drawable.snowing);
        }else if((icon.equals("50n"))||(icon.equals("50d"))){
            //foggy
            imageView.setImageResource(R.drawable.windy);
        }else{
            //the rock
            imageView.setImageResource(R.drawable.therock);
        }

    }
}
