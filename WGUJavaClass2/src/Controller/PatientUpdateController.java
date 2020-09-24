package Controller;

import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import java.io.IOException;

import Database.DatabasePatient;
import Model.Patient;


public class PatientUpdateController implements Initializable {
    @FXML TextField name;
    @FXML TextField insurance;
    @FXML TextField address1;
    @FXML TextField address2;
    @FXML TextField city;
    @FXML ComboBox state;
    @FXML TextField postal_code;
    @FXML TextField phone;
    int patientId;

    final ObservableList<String> states = FXCollections.observableArrayList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");


    @FXML public void updatePatientHandler(ActionEvent event) throws IOException {
        String patientName = name.getText();
        String patientInsurance = insurance.getText();
        String patientAddress1 = address1.getText();
        String patientAddress2 = address2.getText();
        String patientCity = city.getText();
        String patientState = state.getSelectionModel().getSelectedItem().toString();
        String patientPostalCode = postal_code.getText();
        String patientPhone = phone.getText();

        if(errorHandler(patientName, patientInsurance, patientAddress1, patientAddress2, patientCity, patientState, patientPostalCode, patientPhone)) {
            DatabasePatient.updatePatient(patientId, patientName, patientInsurance, patientAddress1, patientAddress2, patientCity, patientState, patientPostalCode, patientPhone);

            Parent nextView = FXMLLoader.load(getClass().getResource("/View/Patients.fxml"));
            Scene scene = new Scene(nextView);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
    }

    public void setPatient(Patient patient) {
        patientId = patient.getPatientId();
        name.setText(patient.getPatientName());
        insurance.setText(patient.getInsuranceProvider());
        address1.setText(patient.getAddress1());
        address2.setText(patient.getAddress2());
        city.setText(patient.getCity());
        state.setValue(patient.getState());
        postal_code.setText(patient.getPostalCode());
        phone.setText(patient.getPhone());
    }
    public boolean errorHandler(String patientName, String patientInsurance, String patientAddress1, String patientAddress2, String patientCity, String patientState, String patientPostalCode, String patientPhone) {
        boolean validEntry = true;
        String errorContent = "";

        if (patientName.isEmpty() || patientName.matches(".*\\d.*")) {
            errorContent = "Please enter valid name";
            validEntry = false;
        }
        else if (patientInsurance.isEmpty()) {
            errorContent = "Please enter insurance provider";
            validEntry = false;
        }
        else if (patientAddress1.isEmpty()) {
            errorContent = "Please enter address1";
            validEntry = false;
        }
        else if (patientCity.isEmpty() || patientCity.matches(".*\\d.*")) {
            errorContent = "Please enter valid city";
            validEntry = false;
        }
        else if (patientState.isEmpty() || patientState.matches(".*\\d.*")) {
            errorContent = "Please enter valid state";
            validEntry = false;
        }
        else if (patientPostalCode.isEmpty() || !patientPostalCode.matches("[0-9]+")) {
            errorContent = "Please enter valid postal code";
            validEntry = false;
        }
        else if (patientPhone.isEmpty() || !patientPhone.matches("[0-9]+")) {
            errorContent = "Please enter valid phone number";
            validEntry = false;
        }

        if (!validEntry) {
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle("Error adding new patient");
            loginError.setContentText(errorContent);
            loginError.showAndWait();
        }

        return validEntry;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        state.setItems(states);
    }
}
