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

public class MeteoService extends IntentService {

    public MeteoService()
    {
        super("MeteoService");
    }

    public static Intent newIntent(Context context){
        return new Intent(context,MeteoService.class);
    }

    public static void setServiceAlarm(Context context,boolean isOn){
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

    private static int iN=0;
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("timerTAG","receved an intent:"+intent);
        sendNotification(iN++);
    }

    private void sendNotification(int i) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "TEST_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Title")
                .setContentText("i: " + i)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mNotificationManager.notify(0, mBuilder.build());
    }

}
