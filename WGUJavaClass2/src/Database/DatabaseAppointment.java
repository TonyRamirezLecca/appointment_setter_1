package Database;

import java.sql.*;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;

import javafx.collections.*;
import Model.*;


public class DatabaseAppointment {
    static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    static ObservableList<State> stateAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() {
        allAppointments.clear();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String getAppointmentsQuery = "SELECT appointment.apt_id, appointment.pt_id, appointment.cr_id, apt_type_id, counselor.c_name, APTtype.description, appointment.notes, appointment.start_datetime FROM appointment INNER JOIN counselor ON counselor.c_id = appointment.cr_id INNER JOIN APTtype ON APTtype.APTtype_id = appointment.apt_type_id;";

            ResultSet resultSet = statement.executeQuery(getAppointmentsQuery);

            while (resultSet.next()) {
                String[] dateTime = resultSet.getString("start_datetime").split("\\s+");
                String start = dateTime[1]; //Grab the start time out of the dateTime;
                String date = dateTime[0]; //Grab the date out of the dateTime

                Appointment appointment = new Appointment(resultSet.getInt("apt_id"), resultSet.getInt("pt_id"), resultSet.getInt("cr_id"), resultSet.getInt("apt_type_id"), resultSet.getString("c_name"), resultSet.getString("description"),resultSet.getString("notes"), start , date);
                allAppointments.add(appointment);
            }
            statement.close();
            return allAppointments;
        }
        catch (SQLException err) {
            System.out.println("Error getting appointments from DB: " + err.getMessage());
            return null;
        }
    }

    public static ObservableList<State> getStateAppointments() {
        stateAppointments.clear();
        ObservableList<Appointment> currentYearAppointments = DatabaseAppointment.getCurrentYearAppointments();

        String[] allStates = State.getStates();
        ArrayList<Integer> appointmentPatientIds = new ArrayList<Integer>(currentYearAppointments.size());
        ObservableList<Patient> allPatients = DatabasePatient.getAllPatients();

        for (int i = 0; i < allStates.length; i++) {
            State stateToAdd = new State(allStates[i]);
            stateAppointments.add(stateToAdd);
        }

        currentYearAppointments.forEach((appointment) -> {
            appointmentPatientIds.add(appointment.getPatientId());
        });

        allPatients.forEach(patient -> {
            appointmentPatientIds.forEach(patientId -> {
                if (patientId == patient.getPatientId()) {
                    String patientState = patient.getState();
                    stateAppointments.forEach(state -> {
                        if(state.getState().equals(patientState)) {
                            state.incrementStateAppointment();
                        }
                    });
                }
            });
        });

        return stateAppointments;
    }

    public static ObservableList<Appointment> getCurrentYearAppointments() {
        //Lambda to filter appointments within a month
        ObservableList<Appointment> thisYearAppointments = allAppointments.filtered(appointment -> {
            LocalDate thisYear = LocalDate.parse("2021-01-01");
            LocalDate appointmentDate = LocalDate.parse(appointment.getAppointmentDate());

            if (appointmentDate.isBefore(thisYear))
                return true;
            return false;
        });

        return thisYearAppointments;
    }

    public static ObservableList<Appointment> getMonthlyAppointments(Patient patient) {
        //Lambda to filter appointments within a month
        ObservableList<Appointment> monthlyAppointments = allAppointments.filtered(appointment -> {
            LocalDate today = LocalDate.now();
            LocalDate appointmentDate = LocalDate.parse(appointment.getAppointmentDate());

            if (appointment.getPatientId() == patient.getPatientId() && appointmentDate.isBefore(today.plusMonths(1))) {
                return true;
            }
            return false;
        });

        return monthlyAppointments;
    }
    public static ObservableList<Appointment> getWeeklyAppointments(Patient patient) {
        //lamda to filter appointments within week
        ObservableList<Appointment> weeklyAppointments = allAppointments.filtered(appointment -> {
            LocalDate today = LocalDate.now();
            LocalDate appointmentDate = LocalDate.parse(appointment.getAppointmentDate());

            if (appointment.getPatientId() == patient.getPatientId() && appointmentDate.isBefore(today.plusWeeks(1).plusDays(1)))
                return true;
            return false;
        });

        return weeklyAppointments;
    }
    public static ObservableList<Appointment> getBiWeeklyAppointments(Patient patient) {
        //lamda to filter appointments within 2 weeks
        ObservableList<Appointment> biWeeklyAppointments = allAppointments.filtered(appointment -> {
            LocalDate today = LocalDate.now();
            LocalDate appointmentDate = LocalDate.parse(appointment.getAppointmentDate());

            if (appointment.getPatientId() == patient.getPatientId() && appointmentDate.isBefore(today.plusWeeks(2).plusDays(1)))
                return true;
            return false;
        });

        return biWeeklyAppointments;
    }

    public static ObservableList<Appointment> getCounselorAppointments(int counselorId) {
        //lamda to filter appointments within 2 weeks
        ObservableList<Appointment> next4HourAppointments = allAppointments.filtered(appointment -> {
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime appointmentDate = LocalDateTime.parse(appointment.getAppointmentDate() + " " + appointment.getAppointmentStart(), formatter);

            System.out.println(today + " " + appointmentDate);

            if (appointment.getCounselorId() == counselorId && appointmentDate.isBefore(today.plusHours(4)) && appointmentDate.isAfter(today))
                return true;
            return false;
        });

        return next4HourAppointments;
    }

    public static boolean newAppointment(int patientId, int counselorId, int appointmentTypeId, String notes, String start) {
        /*
            Expect start to look like:
            "start" is formatted to UTC time before it is pushed as parameter
            2020-9-26 20:00:00    <--- in UTC EVERYTHING IS SAVED IN UTC
         */
           try {
               Statement statement = DatabaseConnection.getConnection().createStatement();
               int appointmentId = 1;
               String getMaxAddressIdQuery = "SELECT MAX(apt_id) from U07ow8.appointment";
               ResultSet resultSetMaxId = statement.executeQuery(getMaxAddressIdQuery);
               if (resultSetMaxId.next()) {
                   appointmentId = resultSetMaxId.getInt(1) + 1; //returns int at index 1 which is the current highest Id in address and adds 1;
               }

               String addAppointmentQuery = "INSERT INTO appointment(apt_id, pt_id, cr_id,apt_type_id,notes,start_datetime,created_at,created_by,updated_at,updated_by,patient_pt_id,counselor_c_id,APTtype_APTtype_id) VALUES(" + appointmentId + ", " + patientId + ", " + counselorId + ", " + appointmentTypeId + ", '" + notes + "', '" + start + "', NOW(), '" + DatabaseCounselor.getCurrentCounselor().getName() + "', NOW(), '" + DatabaseCounselor.getCurrentCounselor().getName() + "', " + patientId + ", " + counselorId + ", " + appointmentTypeId + ");";

               if (statement.executeUpdate(addAppointmentQuery) > 0)
                   return true;

               return false; //If executeUpdate doesn't affect any rows in the appointment table
           }
           catch (SQLException err) {
               System.out.println("Error adding appointment to DB: " + err.getMessage());
               return false;
           }
    }

    public static boolean updateAppointment(int appointmentId, int counselorId, int appointmentTypeId, String notes, String start) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String updateAppointmentQuery = "UPDATE appointment SET cr_id = " + counselorId + ", apt_type_id = " + appointmentTypeId + ", notes = '" + notes + "', start_datetime = '" + start + "'  WHERE apt_id = " + appointmentId + ";";

            if (statement.executeUpdate(updateAppointmentQuery) > 0)
                return true;

            return false; //If executeUpdate doesn't affect any rows in the appointment table
        }
        catch (SQLException err) {
            System.out.println("Error updating appointment: " + err.getMessage());
            return false;
        }    }

    public static boolean deleteAppointment(Appointment appointment) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String deleteAppointmentQuery = "DELETE FROM appointment WHERE apt_id = " + appointment.getAppointmentId() + ";";
            if (statement.executeUpdate(deleteAppointmentQuery) > 0)
                return true;

            return false; //If executeUpdate doesn't affect any rows in the address and patient tables
        }
        catch(SQLException err) {
            System.out.println("Error deleting appointment: " + err.getMessage());
            return false;
        }
    }
}
