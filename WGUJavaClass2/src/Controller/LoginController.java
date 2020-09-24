package Controller;

import java.net.URL;
import java.util.*;
import Database.DatabaseCounselor;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.IOException;

public class LoginController implements Initializable {

    @FXML Label usernameLabel;
    @FXML Label passwordLabel;
    @FXML Label pinLabel;
    @FXML Label titleLabel;
    @FXML Label languageLabel;
    @FXML Button loginButton;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML PasswordField pin;

    String errorTitle;
    String errorContent;

    @FXML public void handleLogin(ActionEvent event) throws IOException {
        String usernameForLogin = username.getText();
        String passwordForLogin = password.getText();
        String pinForLogin = pin.getText();

        if(DatabaseCounselor.login(usernameForLogin, passwordForLogin, pinForLogin)) {
            Parent nextView = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            Scene scene = new Scene(nextView);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        else {
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle(errorTitle);
            loginError.setContentText(errorContent);
            loginError.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("LanguageResources/language", locale);
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        pinLabel.setText(rb.getString("pin"));
        loginButton.setText(rb.getString("login"));
        languageLabel.setText(rb.getString("language"));
        errorTitle = rb.getString("errorTitle");
        errorContent = rb.getString("errorContent");

    }
}
