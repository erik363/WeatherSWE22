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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Idojaras;
import model.IdojarasDao;
import weather.sample.ForeCastMan;
import weather.sample.WeatherManage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {
    public Button backButton;
    public LineChart grafika;
    public CategoryAxis x;
    public NumberAxis y;
    IdojarasDao idojarasDao;
    @FXML
    private LineChart<?, ?> grafikon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Injector injector = Guice.createInjector(new PersistenceModule("jpa-persistence-unit-1"));
        idojarasDao = injector.getInstance(IdojarasDao.class);
        Idojaras idojaras = idojarasDao.lastLocation();
        graphDraw(idojaras.getHely());
    }

    private void graphDraw(String requestCity) {
        WeatherManage weatherManage = new WeatherManage(requestCity);
        ForeCastMan foreCastMan = new ForeCastMan(requestCity);
        XYChart.Series series = new XYChart.Series();
        foreCastMan.setDailyAverange();
        System.out.println(weatherManage.getDays());
        System.out.println(foreCastMan.getDailyTemp());
        int counter = 0;
        for(int i=0; i<5; i++)
        {
            series.getData().add(new XYChart.Data(weatherManage.getDays().get(i) , WeatherManage.fahrenToCelsius2(foreCastMan.getDailyTemp().get(i))));
            counter++;
        }
        grafikon.getData().addAll(series);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Időjárás");
        stage.show();
    }
}
