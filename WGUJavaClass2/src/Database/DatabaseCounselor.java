package Database;
import java.sql.*;

import Model.Appointment;
import Model.Counselor;
import Controller.Logger;
import Database.*;
import Model.Timezones;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseCounselor {
    static Counselor currentCounselor;

    public static Counselor getCurrentCounselor() {
        return currentCounselor;
    }

    public static Boolean login(String username, String password, String pin) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String loginQuery = "SELECT * FROM counselor WHERE c_name='" + username + "' AND c_password='" + password + "' AND c_pin='" + pin + "';";
            ResultSet resultSet = statement.executeQuery(loginQuery);

            if (resultSet.next()) {
                currentCounselor = new Counselor(username);
                statement.close();
                Logger.log(username, true);
                return true;
            }
            else {
                statement.close();
                Logger.log(username, false);
                return false;
            }
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    public static ObservableList<String> getCounselorAvailableTimes(int counselorId, String appointmentDate) {
        ObservableList<String> counselorBookedTimes = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = DatabaseAppointment.getAllAppointments();
        ObservableList<String> availableTimes = FXCollections.observableArrayList();

        allAppointments.forEach(appointment -> { //Lambda to add booked times according to counselor id and date. More effective than using for loop.
            if ((appointment.getCounselorId() == counselorId) && (appointment.getAppointmentDate().equals(appointmentDate))) {
                counselorBookedTimes.add(Timezones.createTimeForCounselor(appointment.getAppointmentStart()));
            }
        });

        availableTimes.addAll("9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM");

        counselorBookedTimes.forEach(bookedTime -> { //Lambda to remove the unavailable times from the combobox
            if (availableTimes.contains(bookedTime)) {
                availableTimes.remove(bookedTime);
            }
        });


        return availableTimes;
    }
}
