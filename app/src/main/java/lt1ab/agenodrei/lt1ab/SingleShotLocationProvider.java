package lt1ab.agenodrei.lt1ab;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


public class SingleShotLocationProvider {

    public static interface LocationCallback {
        public void onNewLocationAvailable(Location location);
    }

    // calls back to calling thread, note this is for low grain: if you want higher precision, swap the
    // contents of the else and if. Also be sure to check gps permission/settings are allowed.
    // call usually takes <10ms
    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        try {
            if (isNetworkEnabled) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                locationManager.requestSingleUpdate(criteria, new SingleShotLocationListener(callback), null);
                Log.d(MainActivity.TAG, "Network Request");

            } else if (isGPSEnabled){
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                locationManager.requestSingleUpdate(criteria, new SingleShotLocationListener(callback), null);
                Log.d(MainActivity.TAG, "GPS Request");

            }
        } catch (SecurityException e) {
            Log.d(MainActivity.TAG, e.getMessage());
            System.exit(0);
        }
    }

        private static class SingleShotLocationListener implements LocationListener {
            private final LocationCallback callback;

            SingleShotLocationListener(LocationCallback callback) {
                this.callback = callback;
            }

            @Override
            public void onLocationChanged(Location location) {
                callback.onNewLocationAvailable(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        }
}