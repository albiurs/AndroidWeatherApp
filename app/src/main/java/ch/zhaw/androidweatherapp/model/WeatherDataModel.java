package ch.zhaw.androidweatherapp.model;

import org.json.JSONObject;

/**
 * WeatherDataModel
 *
 * @author created by Urs Albisser, Mark Zurfluh on 2020-08-17
 * @version 1.0
 */
public interface WeatherDataModel {

    void parseWeatherDataFromJson(JSONObject jsonObject);

    String getTemperature();

    String getCity();

    String getIconName();
}
