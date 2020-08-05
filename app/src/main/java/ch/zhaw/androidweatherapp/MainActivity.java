package ch.zhaw.androidweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.provider.UserDictionary.Words.APP_ID;

public class MainActivity extends AppCompatActivity {

    // == Constants ==
    public static final int REQUEST_CODE = 123;
    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    public static final String APP_ID = "60d819cb54eb39eea4acbe37553a63e0";
    // Time between location updates (5000 milliseconds or 5 seconds)
    public static final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    public static final float MIN_DISTANCE = 1000;

    // == fields ==
    // TODO: Declare a LocationManager and a LocationListener here:
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private static String latitude;
    private static String longitude;

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    // Member Variables:
//    TextView mCityLabel;
//    ImageView mWeatherImage;
//    TextView mTemperatureLabel;



    // == main public methods ==
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FirstFragment fragment  = new FirstFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.nav_graph, fragment);
//        fragmentTransaction.commit();


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
        Log.d("Clima", "onResume() called");
        Intent myIntent = getIntent();
        String city = myIntent.getStringExtra("City");
        if(city!= null)
            getWeatherForNewCity(city);
        else {
            Log.d("Clima", "Getting weather for current location");
            getWeatherForCurrentLocation();



            // Reload current fragment
//            Fragment frg = null;
//            Fragment firstFragment = getSupportFragmentManager().findFragmentByTag("first_fragment_tag");
//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.detach(frg);
//            ft.attach(frg);
//            ft.commit();


//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .detach(firstFragment)
//                    .commitNowAllowingStateLoss();
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .attach(firstFragment)
//                    .commitAllowingStateLoss();
        }
    }


    // TODO: Add getWeatherForNewCity(String city) here:
    private void getWeatherForNewCity(String city){
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
//        letsdosomenetworking(params);

    }



    // TODO: Add getWeatherForCurrentLocation() here:
    private void getWeatherForCurrentLocation() {
        Log.d("Clima", "getWeatherForCurrentLocation() called");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("Clima", "mLocationManager initialized");
        Log.d("Clima", "start initialization of mLocationListener");
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Clima", "onLocationChanged() callback received");
                longitude =  String.valueOf(location.getLongitude());
                latitude =  String.valueOf(location.getLatitude());
                Log.d("Clima", "Current geolocation updated!");
                Log.d("Clima", "Latitude is " + latitude);
                Log.d("Clima", "Longitude is "+ longitude);
                Log.d("Clima", "APP_ID is "+ APP_ID);


//                RequestParams params = new RequestParams();
//                params.put("lat", constants.getLatitude());
//                params.put("lon", constants.getLongitude());
//                params.put("appid", constants.APP_ID);
//                letsdosomenetworking(params);
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Clima", "onProviderDisabled callback received");
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

        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Log.d("Clima", "onRequestPermissionsResult() : Permission granted ");
                getWeatherForCurrentLocation();
            } else{
                Log.d("Clima", "Permission Denied =( ");
            }

        }

    }

    // TODO: Add letsDoSomeNetworking(RequestParams params) here:
    /*private void letsdosomenetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(constants.WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Clima", "Success JSON " +response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJSon(response);
                firstFragment.updateUI(weatherData);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e , JSONObject response){
                Log.e("Clima", "Fail: " + e.toString());
                Log.d("Clima", "status code: " +statusCode);
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT);
            }

        });
    }*/


    // TODO: Add updateUI() here:
//    private void updateUI(WeatherDataModel weather){
//        mTemperatureLabel.setText(weather.getTemperature());
//        mCityLabel.setText(weather.getCity());
//        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
//        mWeatherImage.setImageResource(resourceID);
//    }


    // == getter & setter ==
    public static String getLongitude() {
        return longitude;
    }

    public static String getLatitude() {
        return latitude;
    }
}