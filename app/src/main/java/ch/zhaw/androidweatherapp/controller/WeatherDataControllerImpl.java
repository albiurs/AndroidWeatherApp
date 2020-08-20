package ch.zhaw.androidweatherapp.controller;

/**
 * WeatherDataControllerImpl
 * parseWeatherIconName()
 *
 * Methods to control and manipulate the behaviour of weather data.
 *
 * @author created by Urs Albisser, Mark Zurfluh on 2020-08-17
 * @version 1.0
 */
public class WeatherDataControllerImpl implements WeatherDataController{


    // == fields ==
    // no fields

    // == constructors ==

    /**
     * WeatherDataControllerImpl()
     * Constructor
     */
    public WeatherDataControllerImpl() {
        // do nothing
    }



    // == public methods ==
    /**
     * parseWeatherIconName()
     * Parses the icon file name required based on the weather condition value.
     * @param condition weather condition value
     * @return  icon file name
     */
    public String parseWeatherIconName(int condition) {

        if (condition >= 200 && condition < 201) {
            return "ic_day_rain_thunder";
        } else if (condition >= 300 && condition < 500) {
            return "ic_day_rain";
        } else if (condition == 202) {
            return "ic_rain_thunder";
        } else if (condition == 210) {
            return "ic_day_rain_thunder";
        } else if (condition >= 211 && condition <= 221) {
            return "ic_thunder";
        } else if (condition >= 230 && condition <= 232) {
            return "ic_rain_thunder";
        } else if (condition >= 300 && condition < 501) {
            return "ic_day_rain";
        } else if (condition >= 502 && condition < 531) {
            return "ic_rain";
        } else if (condition >= 600 && condition <= 602) {
            return "ic_snow"; }
        else if (condition >= 611 && condition <= 613) {
            return "ic_sleet"; }
        else if (condition >= 615 && condition <= 622) {
            return "ic_day_sleet"; }
        else if (condition >= 701 && condition <= 771) {
            return "ic_fog";
        } else if (condition == 781) {
            return "ic_tornado";
        } else if (condition == 800) {
            return "ic_day_clear";
        } else if (condition >= 801 && condition <= 802) {
            return "ic_day_partial_cloud";
        } else if (condition >= 803 && condition <= 804) {
            return "ic_cloudy";
        }
        return "ic_question_mark";
    }
}
