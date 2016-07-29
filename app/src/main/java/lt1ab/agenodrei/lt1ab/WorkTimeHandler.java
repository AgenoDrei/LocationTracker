package lt1ab.agenodrei.lt1ab;

import android.content.Context;
import android.util.Log;

/**
 * Created by smuel on 22.06.2016.
 */
public class WorkTimeHandler {
    private static WorkPeriod current = null;

    public WorkTimeHandler() {}

    public static void startTime() {
        if(current != null) {
            Log.d(MainActivity.TAG, "Complete last Period first!");
            return;
        }
        current = new WorkPeriod();
        current.startWorkPeriod();
    }

    public static void stopTime(Context applicationContext) {
        current.stopWorkPeriod();
        long passedTime = current.getPeriodInMillis();

        //Write Period to database
        StorageHandler handler = new StorageHandler(applicationContext);
        handler.newWorkEntry(passedTime);

        //Get all Times
        handler.getNumberOfRows();
        handler.getWorkTimeToday();

        current = null;
    }
}
