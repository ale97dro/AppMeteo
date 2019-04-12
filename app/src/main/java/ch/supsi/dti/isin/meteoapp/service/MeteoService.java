package ch.supsi.dti.isin.meteoapp.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import ch.supsi.dti.isin.meteoapp.HTTPrequest.MeteoFetcher;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class MeteoService extends IntentService {
    private static Location realLocation;

    public MeteoService()
    {
        super("MeteoService");
    }

    public static Intent newIntent(Context context){
        return new Intent(context,MeteoService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn, Location location){
        realLocation=location;
        Intent i=MeteoService.newIntent(context);
        PendingIntent pendingIntent=PendingIntent.getService(context,0,i,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),5000,pendingIntent);
        }else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("timerTAG","receved an intent:"+realLocation.getLongitude());

        MeteoFetcher meteoFetcher =new MeteoFetcher();
        Location locationReturned= meteoFetcher.fetchItem(realLocation);

        if(locationReturned.getTemperature()>=3){
            sendNotification(locationReturned);
        }
    }

    private void sendNotification(Location location) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "TEST_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.sun)
                .setContentTitle("MeteoApp")
                .setContentText("Temperatura supera i 3°")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mNotificationManager.notify(0, mBuilder.build());
    }

}
