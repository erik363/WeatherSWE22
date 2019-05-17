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

public class Controller2 implements Initializable {
    @FXML public Button backButton;
    @FXML public CategoryAxis x;
    @FXML public NumberAxis y;
    @FXML private LineChart<?, ?> grafikon;

    IdojarasDao idojarasDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Injector injector = Guice.createInjector(new PersistenceModule("jpa-persistence-unit-1"));
        idojarasDao = injector.getInstance(IdojarasDao.class);
        Idojaras idojaras = idojarasDao.lastLocation();
        graphDraw(idojaras.getHely());
    }

    private void graphDraw(String requestCity) {
        ForeCastMan foreCastMan = new ForeCastMan(requestCity);
        foreCastMan.setAllTemp();
        foreCastMan.setDates();
        XYChart.Series series = new XYChart.Series();
        int counter = 0;
        for(double i : foreCastMan.getAllTemp()){
            double current;
            current = i;
            current = WeatherManage.fahrenToCelsius2(current);
            series.getData().add(new XYChart.Data("" + foreCastMan.getDates().get(counter), current));
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
