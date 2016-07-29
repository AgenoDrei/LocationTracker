package lt1ab.agenodrei.lt1ab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by smuel on 22.06.2016.
 */
public class StorageHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lt1ab.db";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ELAPSED = "elapsed";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String TABLE_NAME = "times";

    public StorageHandler(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(  "create table " + TABLE_NAME +
                " (id integer primary key, elapsed integer,timestamp integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean newWorkEntry(long elapsedTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ELAPSED, elapsedTime);
        contentValues.put(COLUMN_TIMESTAMP, new Date().getTime());
        db.insert(TABLE_NAME, null, contentValues);
        Log.d(MainActivity.TAG, "New Entry in DB created!");
        return true;
    }

    public int getNumberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+"", null );
        Log.d(MainActivity.TAG, "Number of Rows: " + res.getCount());
        return res.getCount();
    }

    public long getWorkTimeToday() {
        long worktTimeToday = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" WHERE timestamp BETWEEN "+midnightTonight()+" AND "+midnightTommorrow()+"", null );
        Log.d(MainActivity.TAG, "Number of Rows: " + res.getCount());

        try {
            while (res.moveToNext()) {
                worktTimeToday += res.getLong(res.getColumnIndex(COLUMN_ELAPSED));
                //Log.d(MainActivity.TAG, "Retrivied Value " + worktTimeToday);
            }
        } finally {
            res.close();
        }
        Log.d(MainActivity.TAG, "Passed Time Today " + worktTimeToday);
        return worktTimeToday;
    }

    private long midnightTonight() {
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        Log.d(MainActivity.TAG, "Today " + date.getTime().getTime());
        return date.getTime().getTime();
    }

    private long midnightTommorrow() {
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);
        Log.d(MainActivity.TAG, "Tomorrow " + date.getTime().getTime());
        return date.getTime().getTime();
    }
}
