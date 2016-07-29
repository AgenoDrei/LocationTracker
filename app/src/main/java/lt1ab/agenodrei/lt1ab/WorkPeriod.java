package lt1ab.agenodrei.lt1ab;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by smuel on 22.06.2016.
 */
public class WorkPeriod {
    private Pair<Date,Date> startEndTime;
    WorkPeriod() {
        Log.d(MainActivity.TAG, "Time Handler created!");
        startEndTime = new Pair<>(new Date(0), new Date(0));
    }

    public void startWorkPeriod() {
        startEndTime.first.setTime(new Date().getTime());
    }

    public void stopWorkPeriod() {
        startEndTime.second.setTime(new Date().getTime());
    }

    public long getPeriodInMillis() {
        if(startEndTime.first.equals(new Date(0)) || startEndTime.second.equals(new Date(0))) {
            Log.d(MainActivity.TAG, "Invalid Work Period, has to be started and stopped!");
            return -1;
        }
        long timePassed = startEndTime.second.getTime() - startEndTime.first.getTime();
        Log.d(MainActivity.TAG, "Time passed: " + timePassed);
        return timePassed;
    }
}
