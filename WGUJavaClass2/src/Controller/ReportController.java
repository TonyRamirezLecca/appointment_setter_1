package Controller;

import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.IOException;

public class ReportController implements Initializable {

    @FXML public void report1Handler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Report1.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
    @FXML public void report2Handler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Report2.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
    @FXML public void report3Handler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Report3.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML public void backHandler(ActionEvent event) throws IOException {
        Parent nextView = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
        Scene scene = new Scene(nextView);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {}
}
