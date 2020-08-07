package ch.zhaw.androidweatherapp;

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

import cz.msebera.android.httpclient.Header;

public class SecondFragment extends Fragment {

    // == fields ==
    private String latitude = MainActivity.getLatitude();
    private String longitude = MainActivity.getLongitude();
    private String APP_ID = MainActivity.APP_ID;
    private String WEATHER_URL = MainActivity.WEATHER_URL;

    // Member Variables:
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;


    // == main public methods ==

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("Clima", "FirstFragment.onViewCreated() called");
        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) view.findViewById(R.id.locationTV);
        mWeatherImage = (ImageView) view.findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = (TextView) view.findViewById(R.id.tempTV);

        Log.d("Clima", "Latitude is " + latitude);
        Log.d("Clima", "Longitude is "+ longitude);
        Log.d("Clima", "APP_ID is "+ APP_ID);

        RequestParams params = new RequestParams();
        params.put("lat", latitude);
        params.put("lon", longitude);
        params.put("appid", APP_ID);
        letsDoSomeNetworking(params);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment);
            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }



    // TODO: Add letsDoSomeNetworking(RequestParams params) here:
    private void letsDoSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Clima", "Success JSON " + response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJSon(response);
                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Clima", "Fail: " + e.toString());
                Log.d("Clima", "status code: " + statusCode);
//                Toast.makeText(FirstFragment.this, "Request Failed", Toast.LENGTH_SHORT);
            }

        });
    }

    // TODO: Add updateUI() here:
    private void updateUI(WeatherDataModel weather){
        mTemperatureLabel.setText(weather.getTemperature());
        mCityLabel.setText(weather.getCity());
//        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getContext().getPackageName());
        mWeatherImage.setImageResource(resourceID);
    }
}