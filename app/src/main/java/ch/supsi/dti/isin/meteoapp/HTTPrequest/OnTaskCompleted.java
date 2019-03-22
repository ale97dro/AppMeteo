package ch.supsi.dti.isin.meteoapp.HTTPrequest;

import ch.supsi.dti.isin.meteoapp.model.Location;

public interface OnTaskCompleted {
    void onTaskCompleted(Location location);
}
