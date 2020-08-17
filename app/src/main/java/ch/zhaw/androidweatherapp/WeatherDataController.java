package ch.zhaw.androidweatherapp;

public class WeatherDataController {


    // == fields ==



    // == constructors ==

    public WeatherDataController() {
        // -
    }



    // == public methods ==

    // TODO: Uncomment to this to get the weather image name from the condition:
    public String updateWeatherIcon(int condition) {

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
}
