package ca.nait.wteljega1.gpssimple;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView tvCurrentLat;
    TextView tvCurrentLong;
    TextView tvHomeLat;
    TextView tvHomeLong;
    TextView tvMeters;

    double homeLat = 53.56792656339122;
    double homeLong = 113.50055138580501;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCurrentLat = findViewById(R.id.tv_current_latitude);
        tvCurrentLong = findViewById(R.id.tv_current_longitude);
        tvHomeLat = findViewById(R.id.tv_home_latitude);
        tvHomeLong = findViewById(R.id.tv_home_longitude);
        tvMeters = findViewById(R.id.tv_meters);

        LocationManager location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
    }


    public class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            double meters = 33;
            double dLong = location.getLongitude() * -1;
            double dLat = location.getLatitude();
            meters = calculateDifferentInMeters(dLat, dLong);

            NumberFormat formatter = new DecimalFormat("0.00");
            String strMeters = formatter.format(meters);

            tvHomeLat.setText("Home Latitude = " + homeLat);
            tvHomeLong.setText("Home Longitude = " + homeLong);
            tvCurrentLat.setText("" + dLat);
            tvCurrentLong.setText("" + dLong);
            tvMeters.setText("Distance from Home = " + strMeters);

//            DO NOT DO IT LIKE THIS
//            int x = 3;
//            tvHomeLat.setText("Home Latitude = " + homeLat);
//            tvHomeLong.setText("Home Longitude = " + homeLong);
//            tvCurrentLat.setText(x);
//            tvCurrentLong.setText("" + dLong);
//            tvMeters.setText("Distance from Home = " + strMeters);
        }

        private double calculateDifferentInMeters(double dLat,  double dLong)
        {
            double distanceInMeters = 45;

            double earthRadius = 3958.75;
            double dLatitude = Math.toRadians(dLat - homeLat);
            double dLongitude = Math.toRadians(dLong - homeLong);
            double sindLat = Math.sin(dLatitude / 2);
            double sindLon = Math.sin(dLongitude / 2);
            double a = Math.pow(sindLat, 2) + Math.pow(sindLon, 2) * Math.cos(dLat) * Math.cos(homeLat);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double distInMiles = earthRadius * c;

            distanceInMeters = (distInMiles * 1.609344 * 1000);
            return distanceInMeters;
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "GPS is enabled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider)
        {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    }
}
