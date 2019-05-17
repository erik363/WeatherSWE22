package weather.sample;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//http://api.openweathermap.org/data/2.5/weather?q=London&appid=0ebf562b1a5e5f75c21af768a55fd800&units=imperial

/**
 * A jelenlegi időjárás-t kéri le a webservice-től egy URL segítségével.
 * A Weather osztályba GSON segítségével tárolja
 * További adatokat dolgoz fel.
 */
public class WeatherManage {
    public static final String KEY = "0ebf562b1a5e5f75c21af768a55fd800";
    private String urlStr;
    private String location;
    private double temp;
    private ArrayList<String> days;
    private String iconCode;
    private double windSpeed;
    private double clouds;
    private String weatherDesciption;
    private double mainPressure;
    private double mainHumidity;
    Weather weather;
    static Logger logger = LoggerFactory.getLogger(WeatherManage.class);

    private WeatherManage(){}

    /**
     * A jelenlegi hőmérsékletről állít be adatokat
     * @param location Egy várost kér
     */
    public WeatherManage(String location){
        this.location = location;
        urlBuilder(location);
        testmeth();
        this.iconCode = (String) weather.getWeather().get(0).get("icon");
        this.weatherDesciption = String.valueOf(weather.getWeather().get(0).get("description"));
        this.windSpeed = (double) weather.getWind().get("speed");
        this.clouds = (double) weather.getClouds().get("all");
        this.mainHumidity = (double) weather.getMain().get("humidity");
        this.mainPressure = (double) weather.getMain().get("pressure");
        this.temp = (double) weather.getMain().get("temp");
        next5Day();
    }



    /**
     * A fahrenheitot konvetál át Celziusszá, majd dekorálja. (°C)
     *
     * @param fahrenheit értéket
     * @return String
     */
    public static String fahrenToCelsius(double fahrenheit) {
        double celsius;
        celsius = (fahrenheit - 32) / 1.8;
        return decor(celsius, "°C");
    }

    /**
     * JSON-ből olvas be URL alapján GSON segítségével a Weather osztályba
     */
    private void testmeth() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String resultStr = result.toString();
            Gson gson = new Gson();
            Weather w = gson.fromJson(resultStr, Weather.class);
            weather = w;
            logger.info("Sikeres lekérés a webszervertől");
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private void next5Day() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        this.days = new ArrayList<>();
        days.add(0, df2.format(c.getTime()));
        for (int i = 1; i < 6; i++) {
            c.add(Calendar.DATE, 1);
            this.days.add(i, df2.format(c.getTime()));
        }
    }

    /**Fahrenheitot vált át Celsius-ra
     * @param fahrenheit
     * @return double
     */
    public static double fahrenToCelsius2(double fahrenheit) {
        double celsius;
        celsius = (fahrenheit-32)/1.8;
        return Math.round(celsius * 100.0) / 100.0;
    }

    /**Egy dekorátor függvény
     * @param celsius
     * @param unit
     * @return String
     */
    public static String decor(double celsius, String unit) {
        DecimalFormat df2 = new DecimalFormat("##.##");
        return df2.format(celsius) + unit;
    }

    /**
     * URL előállító függvény, mely a JSON-höz kell
     * @param location
     */
    private void urlBuilder(String location) {
        this.urlStr = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + KEY + "&lang=hu&units=imperial";
    }

    public double getTemp() {
        return temp;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public String getIconCode() {
        return iconCode;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getClouds() {
        return clouds;
    }

    public String getWeatherDesciption() {
        return weatherDesciption;
    }

    public double getMainPressure() {
        return mainPressure;
    }

    public double getMainHumidity() {
        return mainHumidity;
    }
}
