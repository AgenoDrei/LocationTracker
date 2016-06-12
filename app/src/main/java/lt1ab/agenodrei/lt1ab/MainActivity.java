package lt1ab.agenodrei.lt1ab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String  TAG = "DEBUG";

    //UI Elements
    TextView timer;
    EditText longitudeField;
    EditText latitudeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView)findViewById(R.id.textLocationTime);
        longitudeField = (EditText)findViewById(R.id.editLongitude);
        latitudeField = (EditText)findViewById(R.id.editLatitude);
        timer.setText("99 : 99");
    }

    public void startGeoWatcher(View view) {
        Double longitude;
        Double latitude;
        try {
            longitude = Double.parseDouble(longitudeField.getText().toString());
            latitude = Double.parseDouble(latitudeField.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this.getApplicationContext(), "Longitude and Latitude Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "Me was clicked!" + longitude);

        Intent intent = new Intent(this.getApplicationContext(), TrackingService.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);

        this.startService(intent);
    }
}
