package ch.zhaw.androidweatherapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import ch.zhaw.androidweatherapp.controller.WeatherDataController;
import ch.zhaw.androidweatherapp.controller.WeatherDataControllerImpl;

/**
 * WeatherDataModelImpl
 * WeatherDataModelImpl()
 * parseWeatherDataFromJson()
 * various getters
 *
 * Implements the model (data holding).
 *
 * @author created by Urs Albisser, Mark Zurfluh on 2020-08-17
 * @version 1.0
 */
public class WeatherDataModelImpl implements WeatherDataModel{


    // == fields ==
    // variables
    private String temperature;
    private String city;
    private String iconName;
    private int weatherCondition;

    // objects
    private WeatherDataController weatherDataController;



    // == constructors ==
    /**
     * WeatherDataModelImpl()
     * Constructor.
     */
    public WeatherDataModelImpl() {

        // init fields
        this.weatherDataController = new WeatherDataControllerImpl();

        this.temperature = null;
        this.city = null;
        this.iconName = null;
        this.weatherCondition = -1;
    }



    // == public methods ==
    /**
     * parseWeatherDataFromJson()
     * Parses the JSON object into variables.
     * @param jsonObject weather data JSON object
     */
    public void parseWeatherDataFromJson(JSONObject jsonObject) {
        try {
            this.city = jsonObject.getString("name");
            this.weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            this.iconName = weatherDataController.parseWeatherIconName(this.weatherCondition);
            double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
            int roundedResult = (int) Math.rint(tempResult);
            this.temperature = Integer.toString(roundedResult);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // == getter & setter ==
    /**
     * getTemperature()
     * @return temperature
     */
    public String getTemperature() {
        return temperature + "°";
    }

    /**
     * getCity()
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * getIconName()
     * @return icon name
     */
    public String getIconName() {
        return iconName;
    }
}
