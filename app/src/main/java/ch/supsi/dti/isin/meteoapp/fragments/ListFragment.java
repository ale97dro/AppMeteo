package ch.supsi.dti.isin.meteoapp.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.activities.DetailActivity;
import ch.supsi.dti.isin.meteoapp.db.MeteoContentValues;
import ch.supsi.dti.isin.meteoapp.db.MeteoHelper;
import ch.supsi.dti.isin.meteoapp.db.MeteoSchema;
import ch.supsi.dti.isin.meteoapp.dialog.PositionPickerDialog;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class ListFragment extends Fragment {
    private RecyclerView mLocationRecyclerView;
    private LocationAdapter mAdapter;
    private List<Location> locations;
    private Location realPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Factory method for ListFragment
     * @param realPosition it's the user position
     * @return New fragment
     */
    public static ListFragment newInstance(Location realPosition)
    {
        ListFragment fragment = new ListFragment();
        fragment.realPosition = realPosition; //set real user position
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mLocationRecyclerView = view.findViewById(R.id.recycler_view);
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        realPosition.setName("Your position");
        //Location realPosition = new Location();
        //realPosition.setName("Posizione attuale");

        locations = LocationsHolder.get(getActivity()).getLocations();
        locations.add(0, realPosition);

        mAdapter = new LocationAdapter(locations);
        mLocationRecyclerView.setAdapter(mAdapter);

        return view;
    }

    // Menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list, menu);
    }

    public void displayDialogPlace(){
        FragmentManager fragmentManager=getFragmentManager();
        PositionPickerDialog dialog=new PositionPickerDialog();
        dialog.setTargetFragment(ListFragment.this,0);
        dialog.show(fragmentManager,"dialog_tag");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast toast = Toast.makeText(getActivity(),
                        "Add a location",
                        Toast.LENGTH_SHORT);
                toast.show();
                displayDialogPlace();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SQLiteDatabase mDatabase = new MeteoHelper(getContext()).getWritableDatabase();
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == 0) {
            String place = (String) data.getSerializableExtra("return_place");
            Log.d("ListFragment",place + "sono li");
            Location temp=new Location();//location added by dialog
            temp.setName(place);
            locations.add(temp);
            Location tLocation=locations.remove(0);
            if(Build.VERSION.SDK_INT >= 24)
            {
                locations.sort(new Comparator<Location>() {
                    @Override
                    public int compare(Location o1, Location o2) {
                        String o1Name=o1.getName().toUpperCase();
                        String o2Name=o2.getName().toUpperCase();
                        return o1Name.compareTo(o2Name);
                    }
                });
            }
            locations.add(0,tLocation);


            // Inserimento in db e refresh della listview
            ContentValues values = MeteoContentValues.getContentValues(temp);
            mDatabase.insert(MeteoSchema.LocationTable.NAME, null, values);
            mAdapter.notifyDataSetChanged();
        }
    }
    // Holder

    private class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private Location mLocation;

        public LocationHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.name); //prendo il nome della città dalla lista
//            latitude = itemView.findViewById(R.id.latitude);
//            longitude = itemView.findViewById(R.id.longitude);
        }

        @Override
        public void onClick(View view) {
            //Intent intent = DetailActivity.newIntent(getActivity(), mLocation.getId()); //original line
            Intent intent = DetailActivity.newIntent(getActivity(), mLocation);
            startActivity(intent);
        }

        public void bind(Location location) {
            mLocation = location;
            mNameTextView.setText(mLocation.getName());
        }
    }

    // Adapter

    private class LocationAdapter extends RecyclerView.Adapter<LocationHolder> {
        private List<Location> mLocations;

        public LocationAdapter(List<Location> locations) {
            mLocations = locations;
        }

        @Override
        public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(LocationHolder holder, int position) {
            Location location = mLocations.get(position);
            holder.bind(location);
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }
    }
}
