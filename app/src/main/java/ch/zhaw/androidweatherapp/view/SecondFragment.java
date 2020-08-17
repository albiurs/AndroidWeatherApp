package ch.zhaw.androidweatherapp.view;

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
import cz.msebera.android.httpclient.Header;

public class SecondFragment extends Fragment {




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




    // == main public methods ==

    /**
     * onCreateView()
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
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the view labels
        cityLabel = view.findViewById(R.id.locationTV);
        weatherImageLabel = view.findViewById(R.id.weatherSymbolIV);
        temperatureLabel = view.findViewById(R.id.tempTV);

        // initialize objects
        weatherDataModel = new WeatherDataModel();

        /*
        Handle weather search
         */
        if(isCitySearch == false) {

            // search for the current geolocation
            Log.d("Debug", "FirstFragment.onViewCreated() called");

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
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }


    /**
     * getWeatherForNewCity()
     * Triggers the fetch of new JSON data.
     * @param city
     */
    private void getWeatherForNewCity(String city){
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
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.d("Debug", "Success JSON " + jsonObject.toString());
                weatherDataModel.parseWeatherDataFromJson(jsonObject);
                Log.d("Debug", "UI update will be triggered...");
                uiUpdate();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Debug", "JSON fetch failed with exception: " + e.toString());
                Log.d("Debug", "status code: " + statusCode);
            }

        });
    }

    /**
     * uiUpdate()
     */
    private void uiUpdate(){
        temperatureLabel.setText(weatherDataModel.getTemperature()); // set temp
        cityLabel.setText(weatherDataModel.getCity());               // set city

        // set image dynamically by resource id
        int resourceID = getResources().getIdentifier(weatherDataModel.getIconName(), "drawable", getContext().getPackageName());
        weatherImageLabel.setImageResource(resourceID);
    }
}