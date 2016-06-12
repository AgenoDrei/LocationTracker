package lt1ab.agenodrei.lt1ab;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by smuel on 09.06.2016.
 */
public class TrackingService extends Service implements SingleShotLocationProvider.LocationCallback{
    private final int TRACKING_INTERVAL = 10000;
    CountDownTimer geoTimer;

    private Double longitude, latitude;

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        longitude = intent.getDoubleExtra("longitude", 0.0);
        latitude = intent.getDoubleExtra("latitude", 0.0);

        Log.d(MainActivity.TAG, "User Longitude: " + longitude);
        Log.d(MainActivity.TAG, "User Latitude: " + latitude);

        geoTimer = new GeoTimer(TRACKING_INTERVAL, 5000, this);
        geoTimer.start();
        return START_REDELIVER_INTENT;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewLocationAvailable(Location location) {
        Log.d(MainActivity.TAG, "Actual Longitude: " + location.getLongitude());
        Log.d(MainActivity.TAG, "Actual Latitude: " + location.getLatitude());
        Log.d(MainActivity.TAG, "Accuracy: " + location.getAccuracy());
    }
}
