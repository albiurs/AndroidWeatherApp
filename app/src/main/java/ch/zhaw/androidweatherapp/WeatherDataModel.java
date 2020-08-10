package ch.zhaw.androidweatherapp;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {

    // TODO: Declare the member variables here
    private String mTemperature;
    private String mCity;
    private String mIconName;
    private int mCondition;

    // TODO: Create a WeatherDataModel from a JSON:
    public static WeatherDataModel fromJSon(JSONObject jsonObject) {
        try {
            WeatherDataModel weatherData = new WeatherDataModel();
            weatherData.mCity = jsonObject.getString("name");
            weatherData.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
            double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
            int roundedResult = (int) Math.rint(tempResult);
            weatherData.mTemperature = Integer.toString(roundedResult);

            return weatherData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    // TODO: Uncomment to this to get the weather image name from the condition:
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "ic_day_rain_thunder";
        } else if (condition >= 300 && condition < 500) {
            return "ic_day_rain";
        } else if (condition >= 500 && condition < 600) {
            return "ic_rain";
        } else if (condition >= 600 && condition <= 700) {
            return "ic_day_snow";
        } else if (condition >= 701 && condition <= 771) {
            return "ic_fog";
        } else if (condition >= 772 && condition < 800) {
            return "ic_rain_thunder";
        } else if (condition == 800) {
            return "ic_day_clear";
        } else if (condition >= 801 && condition <= 804) {
            return "ic_day_partial_cloud";
        } else if (condition >= 900 && condition <= 902) {
            return "ic_rain_thunder";
        } else if (condition == 903) {
            return "ic_snow";
        } else if (condition == 904) {
            return "ic_day_clear";
        } else if (condition >= 905 && condition <= 1000) {
            return "ic_rain_thunder";
        }

        return "dunno";
    }

    // TODO: Create getter methods for temperature, city, and icon name:


    public String getTemperature() {
        return mTemperature + "°";
    }

    public String getCity() {
        return mCity;
    }

    public String getIconName() {
        return mIconName;
    }
}
