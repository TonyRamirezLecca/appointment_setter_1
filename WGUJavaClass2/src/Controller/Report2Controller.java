package Controller;

import java.net.URL;
import java.util.*;

import Database.DatabaseAppointment;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.*;
import java.io.IOException;

public class Report2Controller implements Initializable {

    @FXML Label jonathon;
    @FXML Label rachel;
    @FXML Label marco;
    @FXML Label sammy;

    int year = Calendar.YEAR;
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public void setJonathon() {
        int appointments = allAppointments.filtered(appointment -> appointment.getCounselorId() == 2).size();
        jonathon.setText(Integer.toString(appointments));
    }
    public void setRachel() {
        int appointments = allAppointments.filtered(appointment -> appointment.getCounselorId() == 3).size();
        rachel.setText(Integer.toString(appointments));
    }
    public void setMarco() {
        int appointments = allAppointments.filtered(appointment -> appointment.getCounselorId() == 4).size();
        marco.setText(Integer.toString(appointments));
    }
    public void setSammy() {
        int appointments = allAppointments.filtered(appointment -> appointment.getCounselorId() == 5).size();
        sammy.setText(Integer.toString(appointments));
    }

    @FXML public void backHandler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseAppointment.getAllAppointments();
        allAppointments = DatabaseAppointment.getCurrentYearAppointments();
        setJonathon();
        setRachel();
        setMarco();
        setSammy();
    }
}
