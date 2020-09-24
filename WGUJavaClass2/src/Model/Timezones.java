package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Timezones {

    /*
    public static void getTimeZone(String country) {
        String timeZone;


        switch(country) {
            case "Alabama":
                timeZone = "America/Chicago";
                break;
            case "Alaska":

                break;
            case "Arizona":

                break;
            case "Arkansas":
                timeZone = "America/Chicago";
                break;
            case "California":

                break;
            case "Colorado":

                break;
            case "Connecticut":

                break;
            case "Delaware":

                break;
            case "Florida":
                timeZone = "America/Chicago";
                break;
            case "Georgia":

                break;
            case "Hawaii":

                break;
            case "Idaho":

                break;
            case "Illinois":
                timeZone = "America/Chicago";
                break;
            case "Indiana":

                break;
            case "Iowa":

                break;
            case "Kansas":

                break;
            case "Kentucky":

                break;
            case "Louisiana":

                break;
            case "Maine":

                break;
            case "Maryland":

                break;
            case "Massachusetts":

                break;
            case "Michigan":

                break;
            case "Minnesota":

                break;
            case "Mississippi":

                break;
            case "Missouri":

                break;
            case "Montana":

                break;
            case "Nebraska":

                break;
            case "Nevada":

                break;
            case "New Hampshire":

                break;
            case "New Jersey":

                break;
            case "New Mexico":

                break;
            case "New York":

                break;
            case "North Carolina":

                break;
            case "North Dakota":

                break;
            case "Ohio":

                break;
            case "Oklahoma":

                break;
            case "Oregon":

                break;
            case "Pennsylvania":

                break;
            case "Rhode Island":

                break;
            case "South Carolina":

                break;
            case "South Dakota":

                break;
            case "Tennessee":

                break;
            case "Texas":

                break;
            case "Utah":

                break;
            case "Vermont":

                break;
            case "Virginia":

                break;
            case "Washington":

                break;
            case "West Virginia":

                break;
            case "Wisconsin":

                break;
            case "Wyoming":

                break;
        }

    }
    */

    public static String getTimeForAppointment(String time) {
        switch(time) {
            case "9 AM":
                return "09:00:00";
            case "10 AM":
                return "10:00:00";
            case "11 AM":
                return "11:00:00";
            case "12 PM":
                return "12:00:00";
            case "1 PM":
                return "13:00:00";
            case "2 PM":
                return "14:00:00";
            case "3 PM":
                return "15:00:00";
            case "4 PM":
                return "16:00:00";
            case "5 PM":
                return "17:00:00";
            case "6 PM":
                return "18:00:00";
            case "7 PM":
                return "19:00:00";
            case "8 PM":
                return "20:00:00";
            default:
                return "00:00:00";
        }
    }
    public static String createTimeForCounselor(String time) {
        switch(time) {
            case "09:00:00":
                return "9 AM";
            case "10:00:00":
                return "10 AM";
            case "11:00:00":
                return "11 AM";
            case "12:00:00":
                return "12 PM";
            case "13:00:00":
                return "1 PM";
            case "14:00:00":
                return "2 PM";
            case "15:00:00":
                return "3 PM";
            case "16:00:00":
                return "4 PM";
            case "17:00:00":
                return "5 PM";
            case "18:00:00":
                return "6 PM";
            case "19:00:00":
                return "7 PM";
            case "20:00:00":
                return "8 PM";
            default:
                return "00:00:00";
        }
    }

    public static String generateDateTimeForDB(String appointmentTime, String appointmentDate) {
        return "1996-11-18 02:00:00";
    }

    public static String generateDateTimeForCounselor() {
        return "1995-05-28 12:00:00";
    }
}
