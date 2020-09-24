package Controller;

import java.net.URL;
import java.util.*;
import Database.*;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.IOException;

public class AlertAppointmentsController implements Initializable {
    @FXML Button closeButton;

    @FXML TableView<Appointment> appointmentTable;
    @FXML TableColumn<Appointment, String> appointmentDate;
    @FXML TableColumn<Appointment, String> appointmentTime;
    @FXML TableColumn<Appointment, String> appointmentCounselor;
    @FXML TableColumn<Appointment, String> appointmentType;
    @FXML TableColumn<Appointment, String> appointmentNotes;

    @FXML ToggleGroup counselors;
    @FXML Toggle jonathonToggle;
    @FXML Toggle rachelToggle;
    @FXML Toggle marcoToggle;
    @FXML Toggle sammyToggle;

    @FXML public void jonathonToggleHandler(ActionEvent event) {
            appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(2));
    }
    @FXML public void rachelToggleHandler(ActionEvent event) {
        appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(3));
    }
    @FXML public void marcoToggleHandler(ActionEvent event) {
        appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(4));
    }
    @FXML public void sammyToggleHandler(ActionEvent event) {
        appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(5));

    }

    public void refreshCalendar() {
        DatabaseAppointment.getAllAppointments();
        if (jonathonToggle.isSelected()) {
            appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(2));
        }
        else if (rachelToggle.isSelected()) {
            appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(3));
        }
        else if (marcoToggle.isSelected()) {
            appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(4));
        }
        else if (sammyToggle.isSelected()) {
            appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(5));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DatabaseAppointment.getAllAppointments();

        appointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appointmentCounselor.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentTypeId"));
        appointmentNotes.setCellValueFactory(new PropertyValueFactory<>("appointmentNotes"));

        if (!(DatabaseAppointment.getCounselorAppointments(2).size() > 0) && !(DatabaseAppointment.getCounselorAppointments(3).size() > 0) && !(DatabaseAppointment.getCounselorAppointments(4).size() > 0) && !(DatabaseAppointment.getCounselorAppointments(5).size() > 0)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No appointments");
            alert.setContentText("None of the counselors have appointments in the next 4 hours.");
            alert.showAndWait().ifPresent(res -> {
                if(res == ButtonType.OK) {
                    Stage stage = (Stage) closeButton.getScene().getWindow();
                    stage.close();
                }
            });
        }
        else {
            appointmentTable.setItems(DatabaseAppointment.getCounselorAppointments(2));
        }

    }
}
