package weather.sample;

import java.util.ArrayList;

/**
 *Ez az osztály JSON-ből tárolja az adatokat az időjárás előrejelzésből.
 */
public class WeatherForecast {
    private ArrayList<Weather> list;

    public ArrayList<Weather> getList() {
        return list;
    }
}
