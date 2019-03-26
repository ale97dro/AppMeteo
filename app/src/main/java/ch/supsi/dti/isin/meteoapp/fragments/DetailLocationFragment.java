package ch.supsi.dti.isin.meteoapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailLocationFragment extends Fragment {
    private static final String ARG_LOCATION_ID = "location_id";
    private static final String ARG_LOCATION = "location_obj";

    private Location mLocation;
    private TextView mIdTextView;

    private TextView cityNameTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;

    private ImageView imageView;

//    public static DetailLocationFragment newInstance(UUID locationId) {
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_LOCATION_ID, locationId);
//
//        DetailLocationFragment fragment = new DetailLocationFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static DetailLocationFragment newInstance(Location location) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION, location);
        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID locationId = (UUID) getArguments().getSerializable(ARG_LOCATION_ID);
        Location loc = (Location)getArguments().getSerializable(ARG_LOCATION);
        mLocation = LocationsHolder.get(getActivity()).getLocation(loc.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        mIdTextView = v.findViewById(R.id.id_textView);
        mIdTextView.setText(mLocation.getId().toString());

        cityNameTextView = v.findViewById(R.id.displayedCityName);
        cityNameTextView.setText(mLocation.getName());

        latitudeTextView = v.findViewById(R.id.displayedLatitude);
        latitudeTextView.setText(Double.toString(mLocation.getLatitude()));

        longitudeTextView = v.findViewById(R.id.displayedLongitude);
        longitudeTextView.setText(Double.toString(mLocation.getLongitude()));

        imageView = v.findViewById(R.id.detailImageView);
        imageView.setImageResource(R.drawable.therock);

        //this.getView().setBackground(getResources().getDrawable(Integer.parseInt("drawable/clearSky.jpg")));

        return v;
    }
}

