package weather.sample;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Időjárás előrejelző kezelő osztály
 */
public class ForeCastMan {
    private String urlStr;
    private ArrayList<Double> dailyTemp;
    private ArrayList<String> iconCodes;
    private ArrayList<Double> allTemp;
    private WeatherForecast wetF;
    private ArrayList<String> dates;
    static Logger logger = LoggerFactory.getLogger(WeatherManage.class);

    /**
     * Egy várost kér be
     * @param location a város, amit szeretnél megkapni
     */
    public ForeCastMan(String location) {
        this.urlStr = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + WeatherManage.KEY + "&units=imperial";
        logger.info("Az előrejelzés linkje elkészült");
        dates = new ArrayList<>();
        testmeth();
    }

    /**
     *
     * JSON-ből olvas be URL alapján GSON segítségével a Weatherforecast osztályba
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
            WeatherForecast w = gson.fromJson(resultStr, WeatherForecast.class);
            this.wetF = w;
            logger.info("Sikeres előrejelzés lekérés a webservicetől");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void setIconCode12(){
        iconCodes = new ArrayList<>();
        for (Weather w : wetF.getList()) {
            iconCodes.add(String.valueOf(w.getWeather().get(0).get("icon")));
        }
    }

    /**
     * Kiszámolja a napi átlaghőmérsékletet
     */
    public void setDailyAverange() {
        double sum = 0;
        this.dailyTemp = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        String datum;
        DateFormat format = new SimpleDateFormat("YYYY-MM-d kk:mm:ss", Locale.ENGLISH);
        DateFormat format2 = new SimpleDateFormat("EEE MMM d kk:mm:ss", Locale.ENGLISH);
        Date today = Calendar.getInstance().getTime();
        String todaystr = today.toString();
        Date date = null;
        try {
            date = format2.parse(todaystr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int actual = (int) calendar.get(Calendar.DAY_OF_WEEK)+2;
        int adat = 0;
        for (Weather w : wetF.getList()) {
            datum = w.getDt_txt();
            try {
                date = format.parse(datum);
            } catch (ParseException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
            calendar.setTime(date);
            sum += (double) w.getMain().get("temp");
            adat++;
            if (calendar.get(Calendar.DAY_OF_WEEK) != actual) {
                sum /= adat;
                adat = 0;
                this.dailyTemp.add(sum);
                sum = 0;
                if (actual == 7) {
                    actual = 1;
                } else {
                    actual++;
                }
            }
        }
    }

    /**
     * Összes hőmérséklet megkapása
     */
    public void setAllTemp() {
        allTemp = new ArrayList<>();
        for (Weather w : wetF.getList()) {
            double temp = (double) w.getMain().get("temp");
            allTemp.add(temp);
        }
    }

    public ArrayList<Double> getAllTemp() {
        return allTemp;
    }

    public void setDates(){
        for (Weather w : wetF.getList()) {
            dates.add(w.getDt_txt());
        }
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public ArrayList<Double> getDailyTemp() {
        return dailyTemp;
    }

    public ArrayList<String> getIconCodes() {
        return iconCodes;
    }




}
