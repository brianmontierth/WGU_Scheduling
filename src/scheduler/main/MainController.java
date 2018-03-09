package scheduler.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    public Accordion accordion = new Accordion();

    @Override @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TitledPane calendar = FXMLLoader.load(getClass().getResource("scheduler/calendar/CalendarView.fxml"));
            TitledPane customer = FXMLLoader.load(getClass().getResource("scheduler/customer/CustomerView.fxml"));
            TitledPane report = FXMLLoader.load(getClass().getResource("scheduler/report/ReportView.fxml"));

            accordion.getPanes().addAll(calendar,customer,report);
            accordion.setExpandedPane(customer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
