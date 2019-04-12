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
        Location loc = (Location)getArguments().getSerializable(ARG_LOCATION);
        mLocation = LocationsHolder.get(getActivity()).getLocation(loc.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        TextView mIdTextView = v.findViewById(R.id.id_textView);
        mIdTextView.setText(mLocation.getId().toString());

        TextView cityNameTextView = v.findViewById(R.id.displayedCityName);
        cityNameTextView.setText(mLocation.getName());

        TextView latitudeTextView = v.findViewById(R.id.displayedLatitude);
        latitudeTextView.setText(Double.toString(mLocation.getLatitude()));

        TextView longitudeTextView = v.findViewById(R.id.displayedLongitude);
        longitudeTextView.setText(Double.toString(mLocation.getLongitude()));

        ImageView imageView = v.findViewById(R.id.detailImageView);
        imageView.setImageResource(R.drawable.therock);

        return v;
    }
}

