package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Idojaras;
import model.IdojarasDao;
import weather.sample.ForeCastMan;
import weather.sample.WeatherManage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Label day0, day1, day2, day3, day4, day5;
    @FXML
    public Label temp1, temp2, temp3, temp4, temp5;
    @FXML
    public ImageView day1img, day2img, day3img, day4img, day5img;
    @FXML
    public ImageView recentImg;
    @FXML
    public TextField locationInput;
    @FXML
    public Label locationCity;
    @FXML
    public Button change;
    @FXML
    public Label temp0;
    @FXML
    public Label description;
    @FXML
    public Label windSpeed;
    @FXML
    public Label clouds;
    @FXML
    public Label pressure;
    @FXML
    public Label humidity;
    @FXML
    public Button tempGraf;
    @FXML
    public Button tempGrafnapi;
    WeatherManage weatherManage;

    IdojarasDao idojarasDao;

    public void handleButton(ActionEvent actionEvent) throws IOException {
        String requestCity = locationInput.getText();
        setter(requestCity);
        locationInput.setText("");
        locationInput.requestFocus();
        locationInput.setText("");
        locationInput.requestFocus();
        double temp = WeatherManage.fahrenToCelsius2(weatherManage.getTemp());
        Idojaras idojaras = Idojaras.builder()
                .hely(requestCity)
                .homerseklet(temp)
                .nap(day0.getText())
                .build();
        idojarasDao.persist(idojaras);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Injector injector = Guice.createInjector(new PersistenceModule("jpa-persistence-unit-1"));
        idojarasDao = injector.getInstance(IdojarasDao.class);
        Idojaras idojaras = idojarasDao.lastLocation();
        weatherManage = new WeatherManage(idojaras.getHely());
        setter(idojaras.getHely());
    }

    public void setter(String requestCity) {
        WeatherManage weatherManage = new WeatherManage(requestCity);
        locationCity.setText(requestCity);
        temp0.setText(WeatherManage.fahrenToCelsius(weatherManage.getTemp()));
        description.setText(weatherManage.getWeatherDesciption());
        windSpeed.setText(WeatherManage.decor(weatherManage.getWindSpeed(), " m/s"));
        clouds.setText(WeatherManage.decor(weatherManage.getClouds(), " %"));
        pressure.setText(WeatherManage.decor(weatherManage.getMainPressure(), " hpa"));
        humidity.setText(WeatherManage.decor(weatherManage.getMainHumidity(), " %"));
        day0.setText(weatherManage.getDays().get(0));
        day1.setText(weatherManage.getDays().get(0));
        day2.setText(weatherManage.getDays().get(1));
        day3.setText(weatherManage.getDays().get(2));
        day4.setText(weatherManage.getDays().get(3));
        day5.setText(weatherManage.getDays().get(4));

        Image image = new Image("/images/" + weatherManage.getIconCode() + ".png");
        recentImg.setImage(image);
        ForeCastMan foreCastMan = new ForeCastMan(requestCity);
        foreCastTemp(foreCastMan);
        foreCastIcons(foreCastMan);
    }

    private void foreCastIcons(ForeCastMan foreCastMan) {
        int counter2 = 0;
        ImageView[] imgarr = {day1img, day2img, day3img, day4img, day5img};
        foreCastMan.setIconCode12();
        for (int i = 5; i <= foreCastMan.getIconCodes().size(); i += 8) {
            Image image2 = new Image("/images/" + foreCastMan.getIconCodes().get(i) + ".png");
            imgarr[counter2].setImage(image2);
            counter2++;
        }
    }

    private void foreCastTemp(ForeCastMan foreCastMan) {
        foreCastMan.setDailyAverange();
        Label[] arr = {temp1, temp2, temp3, temp4, temp5};
        int counter = 0;
        for (double i : foreCastMan.getDailyTemp()) {
            arr[counter].setText(WeatherManage.fahrenToCelsius(i));
            counter++;
        }
    }

    public void showTempgraf(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/grafikon.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("5 napos előrejelzés");
        stage.show();
    }

    public void showTempgrafnapi(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/grafikon2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Napi előrejelzés");
        stage.show();
    }
}
