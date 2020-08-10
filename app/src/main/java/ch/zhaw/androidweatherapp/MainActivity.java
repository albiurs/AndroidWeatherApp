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
//    public static final String API_KEY = "60d819cb54eb39eea4acbe37553a63e0";     // OpenWeather API key Urs
    public static final String API_KEY = "a523d6a0fec60928cb0db13f0b555336";     // OpenWeather API key Mark
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

    /**
     * onCrate()
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);         // load main layout
        Toolbar toolbar = findViewById(R.id.toolbar);   // load toolbar

        /* Set a {@link android.widget.Toolbar Toolbar} to act as the
         * {@link androidx.appcompat.app.ActionBar} for this Activity window: */
        setSupportActionBar(toolbar);
    }


    /**
     * onCreateOptionsMenu()
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inject the menu. This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * onOptionsItemSelected()
     * @param item  Menu Item
     * @return      true/false dependent on the action settings
     */
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


    /**
     * onResume()
     * Action to be performed on app resume.
     * Triggers the call of the current geolocation on every resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "onResume() called");
        Log.d("Debug", "Get current location");
        getCurrentLocation();
    }




    // == geolocation-related methods ==
    /**
     * getCurrentLocation()
     * Call current GPS-based geolocation.
     */
    private void getCurrentLocation() {
        Log.d("Debug", "getCurrentLocation() called");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Log.d("Debug", "start initialization of LocationListener");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Debug", "Location changed!");
                longitude =  String.valueOf(location.getLongitude());
                latitude =  String.valueOf(location.getLatitude());
                Log.d("Debug", "Current geolocation updated!");
                Log.d("Debug", "Latitude is " + latitude);
                Log.d("Debug", "Longitude is "+ longitude);
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
                Log.d("Debug", "Provider is disabled");
            }
        };

        // manage location access permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /* If location access permission is not given,
             request the missing permissions.
             See the documentation for ActivityCompat#requestPermissions:
             https://developer.android.com/training/permissions/requesting
             https://developer.android.com/reference/androidx/core/app/ActivityCompat */
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE );
            return;
        }

        // request location updates
        locationManager.requestLocationUpdates(locationProvider, MIN_TIME, MIN_DISTANCE, locationListener);
    }


    /**
     * onRequestPermissionsResult()
     * Trigger getCrurrentLocation() in case the location update permission has previously been granted.
     * @param requestCode   request code
     * @param permissions   adroid.permission.ACCESS_FINE_LOCATION
     * @param grantResults  grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Debug", "onRequestPermissionsResult() called");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "onRequestPermissionsResult(): location update permission granted ");
                Log.d("Debug", "getCurrentLocation() is to be called!");
                getCurrentLocation();
            } else{
                Log.d("Debug", "location update permission denied!");
            }
        }
    }




    // == getters & setters ==
    /**
     * getLongitude()
     * @return longitude
     */
    public static String getLongitude() {
        return longitude;
    }


    /**
     * getLatitude()
     * @return latitude
     */
    public static String getLatitude() {
        return latitude;
    }


    /**
     * getCity()
     * @return city
     */
    public static String getCity() {
        return city;
    }


    /**
     * setCity()
     * @param city city
     */
    public static void setCity(String city) {
        MainActivity.city = city;
    }


    /**
     * isIsCitySearch()
     * @return true, if the current search is a city search; false if it's a long/lat search
     */
    public static boolean isIsCitySearch() {
        return isCitySearch;
    }


    /**
     * setIsCitySearch()
     * @param isCitySearch if it is a city search -> true; if it's a long/lat search -> false
     */
    public static void setIsCitySearch(boolean isCitySearch) {
        MainActivity.isCitySearch = isCitySearch;
    }
}