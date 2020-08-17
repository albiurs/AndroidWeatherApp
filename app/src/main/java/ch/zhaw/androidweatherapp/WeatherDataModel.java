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

        this.temperature = null;
        this.city = null;
        this.iconName = null;
        this.weatherCondition = -1;
    }



    // == public methods ==

    public void fromJSon(JSONObject jsonObject) {
        try {
            city = jsonObject.getString("name");
            weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            iconName = weatherDataController.updateWeatherIcon(weatherCondition);
            double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
            int roundedResult = (int) Math.rint(tempResult);
            temperature = Integer.toString(roundedResult);

        } catch (JSONException e) {
            e.printStackTrace();
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
