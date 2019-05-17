package weather.sample;

import java.util.ArrayList;
import java.util.Map;

/**
 *Ez az osztály JSON-ből tárolja az alapvető adatokat a jelenlegi időjárásról.
 */
public class Weather {
    private ArrayList<Map<String, Object>> weather;
    private Map<String, Object> main;
    private int visibility;
    private Map<String, Object> wind;
    private Map<String, Object> clouds;
    private String dt_txt;

    public String getName() {
        return name;
    }

    private String name;

    public ArrayList<Map<String, Object>> getWeather() {
        return weather;
    }

    public Map<String, Object> getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Map<String, Object> getWind() {
        return wind;
    }

    public Map<String, Object> getClouds() {
        return clouds;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
