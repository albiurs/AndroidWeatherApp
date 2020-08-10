package ch.zhaw.androidweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    // == Constants ==
    public static final int REQUEST_CODE = 123;
    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    public static final String API_KEY = "60d819cb54eb39eea4acbe37553a63e0";     // OpenWeather API key Urs
//    public static final String API_KEY = "a523d6a0fec60928cb0db13f0b555336";     // OpenWeather API key Mark
    public static final long MIN_TIME = 5000;   // min. time between location updates = 5000 ms
    public static final float MIN_DISTANCE = 1000;  // min. distance between locations before update = 1000m



    // == fields ==
    // geolocation
    private LocationManager locationManager;    //
    private LocationListener locationListener;
    private String locationProvider = locationManager.GPS_PROVIDER;
    private static String latitude;
    private static String longitude;

    // city search
    private static boolean isCitySearch;
    private static String city;



    // == main public methods ==
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // TODO: Add onResume() here:
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "onResume() called");
//        Intent myIntent = getIntent();
//        String city = myIntent.getStringExtra("City");
        if(this.city != null) {
//            getWeatherForNewCity(city);
        }
        else {
            Log.d("Debug", "Getting weather for current location");
            getCurrentLocation();
        }
    }


    // TODO: Add getWeatherForCurrentLocation() here:
    private void getCurrentLocation() {
        Log.d("Debug", "getWeatherForCurrentLocation() called");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("Debug", "mLocationManager initialized");
        Log.d("Debug", "start initialization of mLocationListener");

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Debug", "onLocationChanged() callback received");
                longitude =  String.valueOf(location.getLongitude());
                latitude =  String.valueOf(location.getLatitude());
                Log.d("Debug", "Current geolocation updated!");
                Log.d("Debug", "Latitude is " + latitude);
                Log.d("Debug", "Longitude is "+ longitude);
                Log.d("Debug", "APP_ID is "+ API_KEY);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                // do nothing
            }

            @Override
            public void onProviderEnabled(String s) {
                // do nothing
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Debug", "onProviderDisabled callback received");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE );
            return;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }

        locationManager.requestLocationUpdates(locationProvider, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "onRequestPermissionsResult() : Permission granted ");
                getCurrentLocation();
            } else{
                Log.d("Debug", "Permission Denied =( ");
            }

        }

    }




    // == getter & setter ==
    public static String getLongitude() {
        return longitude;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        MainActivity.city = city;
    }

    public static boolean isIsCitySearch() {
        return isCitySearch;
    }

    public static void setIsCitySearch(boolean isCitySearch) {
        MainActivity.isCitySearch = isCitySearch;
    }
}