package ch.zhaw.androidweatherapp;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {


    // == fields ==
    private String temperature;
    private String city;
    private String iconName;
    private int weatherCondition;

    // objects
    WeatherDataController weatherDataController;



    // == constructors ==

    public WeatherDataModel() {

        weatherDataController = new WeatherDataController();
    }



    // == public methods ==

    // TODO: Create a WeatherDataModel from a JSON:
    public WeatherDataModel fromJSon(JSONObject jsonObject) {
        try {
            WeatherDataModel weatherData = new WeatherDataModel();
            weatherData.city = jsonObject.getString("name");
            weatherData.weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.iconName = weatherDataController.updateWeatherIcon(weatherData.weatherCondition);
            double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
            int roundedResult = (int) Math.rint(tempResult);
            weatherData.temperature = Integer.toString(roundedResult);

            return weatherData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



    // == getter & setter ==

    public String getTemperature() {
        return temperature + "°";
    }

    public String getCity() {
        return city;
    }

    public String getIconName() {
        return iconName;
    }
}
