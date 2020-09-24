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

public class Report1Controller implements Initializable {

    @FXML Label family;
    @FXML Label personal;
    @FXML Label marriage;

    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public void setFamilyReport() {
        int familyAppointments = allAppointments.filtered(appointment -> appointment.getAppointmentTypeId() == 1).size();
        family.setText(Integer.toString(familyAppointments));
    }
    public void setPersonalReport() {
        int personalAppointments = allAppointments.filtered(appointment -> appointment.getAppointmentTypeId() == 2).size();
        personal.setText(Integer.toString(personalAppointments));
    }
    public void setMarriageReport() {
        int marriageAppointments = allAppointments.filtered(appointment -> appointment.getAppointmentTypeId() == 3).size();
        marriage.setText(Integer.toString(marriageAppointments));
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
        setFamilyReport();
        setPersonalReport();
        setMarriageReport();
    }
}
