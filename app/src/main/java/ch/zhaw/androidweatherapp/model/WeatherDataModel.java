package ch.zhaw.androidweatherapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import ch.zhaw.androidweatherapp.controller.WeatherDataController;

public class WeatherDataModel {


    // == fields ==
    private String temperature;
    private String city;
    private String iconName;
    private int weatherCondition;

    // objects
    private WeatherDataController weatherDataController;



    // == constructors ==

    public WeatherDataModel() {

        this.weatherDataController = new WeatherDataController();

        this.temperature = null;
        this.city = null;
        this.iconName = null;
        this.weatherCondition = -1;
    }



    // == public methods ==

    public void parseWeatherDataFromJson(JSONObject jsonObject) {
        try {
            this.city = jsonObject.getString("name");
            this.weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            this.iconName = weatherDataController.updateWeatherIcon(this.weatherCondition);
            double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
            int roundedResult = (int) Math.rint(tempResult);
            this.temperature = Integer.toString(roundedResult);

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
