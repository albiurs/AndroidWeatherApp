package ch.zhaw.androidweatherapp.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import ch.zhaw.androidweatherapp.MainActivity;
import ch.zhaw.androidweatherapp.R;
import ch.zhaw.androidweatherapp.model.WeatherDataModel;
import ch.zhaw.androidweatherapp.model.WeatherDataModelImpl;
import cz.msebera.android.httpclient.Header;

/**
 * SecondFragmentController
 * onCreateView()
 * onViewCreated()
 * getWeatherForNewCity()
 * fetchJson()
 * updateView()
 *
 * Controls the view of the second fragment layout.
 * Implements the service methods to fetch the JSON files.
 *
 * @author created by Urs Albisser, Mark Zurfluh on 2020-08-17
 * @version 1.0
 */
public class SecondFragmentController extends Fragment {


    // == fields ==
    // static MainActivity constants & variables
    private String APP_ID = MainActivity.API_KEY;
    private String WEATHER_URL = MainActivity.WEATHER_URL;
    private boolean isCitySearch = MainActivity.isIsCitySearch();
    private String latitude = MainActivity.getLatitude();
    private String longitude = MainActivity.getLongitude();

    // view labels
    private TextView cityLabel;
    private ImageView weatherImageLabel;
    private TextView temperatureLabel;

    // objects
    private WeatherDataModel weatherDataModel;



    // == public methods ==
    /**
     * onCreateView()
     * This method is called when the view gets created.
     * Inflates the xml layout.
     * @param inflater  LayoutINflater
     * @param container ViewGroup container
     * @param savedInstanceState Bundle savedInstanceState
     * @return  attacheToRoot: true/false
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout "R.layout.fragment_second" for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }


    /**
     * onViewCreated()
     * This method is called after the view is created.
     * Handles the trigger of the weather search (geolocation or manual city search).
     * Handles the back button.
     * @param view view
     * @param savedInstanceState saved instance status
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the view labels
        this.cityLabel = view.findViewById(R.id.locationTV);
        this.weatherImageLabel = view.findViewById(R.id.weatherSymbolIV);
        this.temperatureLabel = view.findViewById(R.id.tempTV);

        // initialize objects
        this.weatherDataModel = new WeatherDataModelImpl();

        /*
        Handle trigger of the weather search (geolocation or manual city search)
         */
        if(isCitySearch == false) {

            // search for the current geolocation
            Log.d("Debug", "FirstFragmentController.onViewCreated() called");

            Log.d("Debug", "Latitude is " + latitude);
            Log.d("Debug", "Longitude is "+ longitude);
            Log.d("Debug", "APP_ID is "+ APP_ID);

            RequestParams requestParams = new RequestParams();
            requestParams.put("lat", latitude);
            requestParams.put("lon", longitude);
            requestParams.put("appid", APP_ID);
            fetchJson(requestParams);

        } else {

            // search for entered city
            getWeatherForNewCity(MainActivity.getCity());

        }

        /*
        Handle back button
         */
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragmentController.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }


    /**
     * getWeatherForNewCity()
     * Triggers the fetch of new JSON data.
     * @param city
     */
    private void getWeatherForNewCity(String city) {

        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
        fetchJson(params);
    }


    /**
     * fetchJson()
     * Fetches the JSON file from the source @ openweathermap.org and
     * triggers the UI update.
     * @param params
     */
    private void fetchJson(RequestParams params) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.d("Debug", "Success JSON " + jsonObject.toString());
                weatherDataModel.parseWeatherDataFromJson(jsonObject);
                Log.d("Debug", "UI update will be triggered...");
                updateView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Debug", "JSON fetch failed with exception: " + e.toString());
                Log.d("Debug", "status code: " + statusCode);
            }
        });
    }


    /**
     * updateView()
     * Updates the fragment view with current weather data.
     */
    private void updateView() {

        // set text labels
        temperatureLabel.setText(weatherDataModel.getTemperature()); // set temp
        cityLabel.setText(weatherDataModel.getCity());               // set city

        // set image label dynamically by resource id
        int resourceID = getResources().getIdentifier(weatherDataModel.getIconName(), "drawable", getContext().getPackageName());
        weatherImageLabel.setImageResource(resourceID);
    }
}