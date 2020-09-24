package Controller;

import java.net.URL;
import java.util.*;
import Database.DatabasePatient;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.io.IOException;

import Model.*;

public class PatientController implements Initializable {

    @FXML TableView<Patient> patientTable;
    @FXML TableColumn<Patient, Integer> patientId;
    @FXML TableColumn<Patient, String> patientName;

    Patient selectedPatient;

    @FXML public void newPatientHandler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/PatientAdd.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }


    @FXML public void updatePatientHandler(ActionEvent event) throws IOException {

        selectedPatient = patientTable.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/PatientUpdate.fxml"));

            Parent root = (Parent)fxmlLoader.load();
            PatientUpdateController controller = fxmlLoader.<PatientUpdateController>getController();
            controller.setPatient(selectedPatient);
            Scene scene = new Scene(root);

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a patient to update.");
            alert.showAndWait();
        }

    }



    @FXML public void deletePatientHandler(ActionEvent event) throws IOException {
        selectedPatient = patientTable.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure?");
            alert.setContentText("Are you sure you want to delete " + selectedPatient.getPatientName() + "?");
            alert.showAndWait().ifPresent(res -> {
                if(res == ButtonType.OK) {
                    DatabasePatient.deletePatient(selectedPatient);
                    patientTable.setItems(DatabasePatient.getAllPatients());
                }
            });
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a patient to delete.");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        patientTable.setItems(DatabasePatient.getAllPatients());
    }
}
