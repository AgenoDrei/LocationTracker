package lt1ab.agenodrei.lt1ab;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.TimerTask;

/**
 * Created by smuel on 09.06.2016.
 */
public class GeoTimer extends CountDownTimer {
    private final TrackingService parent;

    public GeoTimer(long millisInFuture, long countDownInterval, TrackingService parent) {
        super(millisInFuture, countDownInterval);
        this.parent = parent;
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        Log.d(MainActivity.TAG, "Still alive!!!");
        SingleShotLocationProvider.requestSingleUpdate(parent.getApplicationContext(), parent);
        this.start();
    }
}
