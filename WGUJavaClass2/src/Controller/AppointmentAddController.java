package Controller;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Database.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.IOException;

public class AppointmentAddController implements Initializable {

    @FXML ComboBox counselor;
    @FXML DatePicker date;
    @FXML ComboBox time;
    @FXML ComboBox appointmentType;
    @FXML TextField notes;

    int patientId;

    final ObservableList<String> counselors = FXCollections.observableArrayList("Jonathon Roberts", "Rachel Summers", "Marco Cortez", "Sammy Ten");
    final ObservableList<String> appointmentTimes = FXCollections.observableArrayList("9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8PM");
    final ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Family Counseling", "Personal Counseling", "Marriage Counseling");

    @FXML public void selectCounselorHandler(ActionEvent event) throws IOException {
        int counselorId = Counselor.getId(counselor.getSelectionModel().getSelectedItem().toString());
        setTime(counselorId);
    }

    @FXML public void addAppointmentHandler(ActionEvent event) throws IOException {
        if (counselor.getSelectionModel().getSelectedItem() != null && appointmentType.getSelectionModel().getSelectedItem() != null && time.getSelectionModel().getSelectedItem() != null && date.getValue() != null) {
            int counselorId = Counselor.getId(counselor.getSelectionModel().getSelectedItem().toString());
            int appointmentTypeId = Appointment.getAppointmentType(appointmentType.getSelectionModel().getSelectedItem().toString());
            String appointmentTime = Timezones.getTimeForAppointment(time.getSelectionModel().getSelectedItem().toString());
            String appointmentDate = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String appointmentNotes = notes.getText();


            String start = appointmentDate + " " + appointmentTime;

            if (errorHandler(counselorId, appointmentTypeId, appointmentTime, appointmentDate)) {
                DatabaseAppointment.newAppointment(patientId, counselorId, appointmentTypeId, appointmentNotes, start);

                Parent nextView = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
                Scene scene = new Scene(nextView);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            }
        }
        else {
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle("Error adding appointment");
            loginError.setContentText("Please fill out all sections of form. (Notes are optional)");
            loginError.showAndWait();
        }
    }

    @FXML public void dateChangeHandler(ActionEvent event) throws IOException {
        if (counselor.getValue() != null) {
            int counselorId = Counselor.getId(counselor.getSelectionModel().getSelectedItem().toString());
            setTime(counselorId);
        }
    }

    @FXML public void backHandler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    public void setPatient(Patient patient) {
        patientId = patient.getPatientId();
    }

    public void setTime(int counselorId) {
        if (date.getValue() != null) {
            String appointmentDate = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ObservableList<String> newTimes = DatabaseCounselor.getCounselorAvailableTimes(counselorId, appointmentDate);
            time.setItems(newTimes);
            time.setValue(null);
        }
    }

    public boolean errorHandler(int counselorId, int appointmentTypeId, String time, String date) {
        String errorContent = "";

        try {
            if (!(counselorId > 0)) {
                errorContent = "Please select a counselor";
                throw new Exception(errorContent);
            } else if (!(appointmentTypeId > 0)) {
                errorContent = "Please select appointment type";
                throw new Exception(errorContent);
            } else if (time.isEmpty()) {
                errorContent = "Please select an appointment time";
                throw new Exception(errorContent);
            } else if (date.isEmpty()) {
                errorContent = "Please please select an appointment date";
                throw new Exception(errorContent);
            }
        }
        catch (Exception err) {
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle("Error adding appointment");
            loginError.setContentText(errorContent);
            loginError.showAndWait();
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        counselor.setItems(counselors);
        time.setItems(appointmentTimes);
        appointmentType.setItems(appointmentTypes);

        date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate MartinLutherKingDay = LocalDate.parse("2021-01-18");
                LocalDate MemorialDay = LocalDate.parse("2021-05-31");
                LocalDate VeteransDay = LocalDate.parse("2020-11-11");
                LocalDate VeteransDay2 = LocalDate.parse("2021-11-11");
                LocalDate Thanksgiving = LocalDate.parse("2020-11-26");
                LocalDate ThanksgivingAfter = LocalDate.parse("2020-11-27");
                LocalDate Thanksgiving2 = LocalDate.parse("2021-11-25");
                LocalDate ThanksgivingAfter2 = LocalDate.parse("2021-11-26");
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                || date.compareTo(today) < 0 || date.equals(MartinLutherKingDay) || date.equals(MemorialDay) || date.equals(VeteransDay)
                        || date.equals(VeteransDay2) || date.equals(Thanksgiving) || date.equals(Thanksgiving2) || date.equals(ThanksgivingAfter)
                        || date.equals(ThanksgivingAfter2)
                );
            }
        });
        date.setEditable(false);
    }
}
