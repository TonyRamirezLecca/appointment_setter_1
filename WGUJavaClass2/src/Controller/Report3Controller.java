package Controller;

import java.net.URL;
import java.util.*;

import Database.DatabaseAppointment;
import Database.DatabasePatient;
import Model.Appointment;
import Model.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.io.IOException;

public class Report3Controller implements Initializable {
    @FXML TableView<State> stateTable;
    @FXML TableColumn<State, String> state;
    @FXML TableColumn<State, Integer> amount;


    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();


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

        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        amount.setCellValueFactory(new PropertyValueFactory<>("numStateAppointments"));
        stateTable.setItems(DatabaseAppointment.getStateAppointments());
    }
}
