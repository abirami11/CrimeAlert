package com.example.arwen.crimealert;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {
    TextView txtLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        txtLocation = (TextView) findViewById(R.id.incidents);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                newLocationChange(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            txtLocation.setText(("Lat" + permissionCheck));
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                txtLocation.setText("Latitude:");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                Log.d("Network", "Network");
                if (locationManager != null) {
                    Location loc = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(loc != null)
                        txtLocation.setText("Latitude:" + loc.getLatitude() + ", Longitude:" + loc.getLongitude());
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                txtLocation.setText("Longitude:");

            }


    }


    private void newLocationChange(Location location) {

        txtLocation = (TextView) findViewById(R.id.incidents);
        txtLocation.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        Log.d("STATE", "New Location Change");
    }

}
