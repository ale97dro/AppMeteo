package ch.supsi.dti.isin.meteoapp.HTTPrequest;

import android.os.AsyncTask;

import ch.supsi.dti.isin.meteoapp.model.Location;

public class MyTask extends AsyncTask<Location,Void, Location> {

    private OnTaskCompleted listener;
    private Location location;

    public MyTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected Location doInBackground(Location... locations) {
        return new TestFetcher().fetchItem(locations[0]);
    }

    @Override
    protected void onPostExecute(Location location) {
        listener.onTaskCompleted(location);
    }
}
