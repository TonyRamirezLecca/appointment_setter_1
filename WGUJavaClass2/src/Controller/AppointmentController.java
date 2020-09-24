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

public class AppointmentController implements Initializable {
    @FXML TableView<Patient> patientTable;
    @FXML TableColumn<Patient, Integer> patientId;
    @FXML TableColumn<Patient, String> patientName;

    @FXML TableView<Appointment> appointmentTable;
    @FXML TableColumn<Appointment, String> appointmentDate;
    @FXML TableColumn<Appointment, String> appointmentTime;
    @FXML TableColumn<Appointment, String> appointmentCounselor;
    @FXML TableColumn<Appointment, String> appointmentType;
    @FXML TableColumn<Appointment, String> appointmentNotes;

    @FXML ToggleGroup calendar;
    @FXML Toggle monthlyToggle;
    @FXML Toggle weeklyToggle;
    @FXML Toggle biWeeklyToggle;

    Patient selectedPatient;
    Appointment selectedAppointment;

    @FXML public void selectPatientHandler(MouseEvent event) {

        selectedPatient = patientTable.getSelectionModel().getSelectedItem();

        if (monthlyToggle.isSelected()) { //If monthly radio is selected
            appointmentTable.setItems(DatabaseAppointment.getMonthlyAppointments(selectedPatient));
        }
        else if (weeklyToggle.isSelected()) { //If weekly radio is selected
            appointmentTable.setItems(DatabaseAppointment.getWeeklyAppointments(selectedPatient));
        }
        else if (biWeeklyToggle.isSelected()) { //If weekly radio is selected
            appointmentTable.setItems(DatabaseAppointment.getBiWeeklyAppointments(selectedPatient));
        }

    }
    @FXML public void monthlyToggleHandler(ActionEvent event) {
        if (selectedPatient != null)
            appointmentTable.setItems(DatabaseAppointment.getMonthlyAppointments(selectedPatient));
    }
    @FXML public void weeklyToggleHandler(ActionEvent event) {
        if (selectedPatient != null)
            appointmentTable.setItems(DatabaseAppointment.getWeeklyAppointments(selectedPatient));
    }
    @FXML public void biWeeklyToggleHandler(ActionEvent event) {
        System.out.println("Bi weekly clicked");
        if (selectedPatient != null)
            appointmentTable.setItems(DatabaseAppointment.getBiWeeklyAppointments(selectedPatient));
    }

    @FXML public void addAppointmentHandler(ActionEvent event) throws IOException {
        if (selectedPatient != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AppointmentAdd.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            AppointmentAddController controller = fxmlLoader.<AppointmentAddController>getController();
            controller.setPatient(selectedPatient);
            Scene scene = new Scene(root);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a patient first");
            alert.showAndWait();
        }
    }
    @FXML public void modifyAppointmentHandler(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AppointmentUpdate.fxml"));

            Parent root = (Parent)fxmlLoader.load();
            AppointmentUpdateController controller = fxmlLoader.<AppointmentUpdateController>getController();
            controller.setAppointment(selectedAppointment);
            Scene scene = new Scene(root);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();


        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an appointment to update.");
            alert.showAndWait();
        }

    }
    @FXML public void deleteAppointmentHandler(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure?");
            alert.setContentText("Are you sure you want to delete " + selectedPatient.getPatientName() + "'s appointment?");
            alert.showAndWait().ifPresent(res -> {
                if(res == ButtonType.OK) {
                    DatabaseAppointment.deleteAppointment(selectedAppointment);
                    refreshCalendar();
                }
            });
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        }
    }

    @FXML public void backHandler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void refreshCalendar() {
        DatabaseAppointment.getAllAppointments();
        if (monthlyToggle.isSelected()) { //If monthly radio is selected
            appointmentTable.setItems(DatabaseAppointment.getMonthlyAppointments(selectedPatient));
        }
        else if (weeklyToggle.isSelected()) { //If weekly radio is selected
            appointmentTable.setItems(DatabaseAppointment.getWeeklyAppointments(selectedPatient));
        }
        else if (biWeeklyToggle.isSelected()) { //If weekly radio is selected
            appointmentTable.setItems(DatabaseAppointment.getBiWeeklyAppointments(selectedPatient));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DatabaseAppointment.getAllAppointments();

        patientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        patientTable.setItems(DatabasePatient.getAllPatients());

        appointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appointmentCounselor.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentTypeId"));
        appointmentNotes.setCellValueFactory(new PropertyValueFactory<>("appointmentNotes"));

    }
}
