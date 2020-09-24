package Database;

import java.sql.*;
import javafx.collections.*;
import Model.*;

public class DatabasePatient {
    static ObservableList<Patient> allPatients = FXCollections.observableArrayList();

    public static Patient getPatient(Patient patient) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String getAllPatientsQuery = "SELECT patient.pt_id, patient.pt_name, patient.INS_PR, address.addressline_1, address.addressline_2, address.city, address.state, address.postal_code, address.phone FROM patient INNER JOIN address ON patient.address_id = address.address_id WHERE patient.pt_id = " + patient.getPatientId() + ";";
            ResultSet resultSet = statement.executeQuery(getAllPatientsQuery);

            if (resultSet.next()) {
                Patient returnPatient = new Patient(resultSet.getInt("pt_id"), resultSet.getString("pt_name"), resultSet.getString("INS_PR"), resultSet.getString("addressline_1"), resultSet.getString("addressline_2"), resultSet.getString("city"), resultSet.getString("state"), resultSet.getString("postal_code"), resultSet.getString("phone"));
                statement.close();
                return returnPatient;
            }
            statement.close();
            return null;
        }
        catch (SQLException e){
            System.out.println("Error getting all patients from DB: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<Patient> getAllPatients() {
        allPatients.clear(); //Clear and query from db again;
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String getAllPatientsQuery = "SELECT patient.pt_id, patient.pt_name, patient.INS_PR, address.addressline_1, address.addressline_2, address.city, address.state, address.postal_code, address.phone FROM patient INNER JOIN address ON patient.address_id = address.address_id;";
            ResultSet resultSet = statement.executeQuery(getAllPatientsQuery);

            while (resultSet.next()) {
                Patient patient = new Patient(resultSet.getInt("pt_id"), resultSet.getString("pt_name"), resultSet.getString("INS_PR"), resultSet.getString("addressline_1"), resultSet.getString("addressline_2"), resultSet.getString("city"), resultSet.getString("state"), resultSet.getString("postal_code"), resultSet.getString("phone"));
                allPatients.add(patient);
            }
            statement.close();
            return allPatients;
        }
        catch (SQLException e){
            System.out.println("Error getting all patients from DB: " + e.getMessage());
            return null;
        }
    }

    public static boolean addPatient(String name, String insuranceProvider, String address1, String address2, String city, String state, String postal_code, String phone) {
        try {
            int addressForeignKey = 1;
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String getMaxAddressIdQuery = "SELECT MAX(address_id) from U07ow8.address";
            ResultSet resultSetMaxId = statement.executeQuery(getMaxAddressIdQuery);
            if (resultSetMaxId.next()) {
                addressForeignKey = resultSetMaxId.getInt(1) + 1; //returns int at index 1 which is the current highest Id in address
            }

            String addAddressQuery = "INSERT INTO address(address_id, addressline_1, addressline_2, city, state, postal_code, phone, created_at, created_by, updated_at, updated_by) VALUES(" + addressForeignKey + ", '" + address1 + "', '" + address2 + "', '" + city +"', '" + state + "', '" + postal_code + "', '" + phone + "', NOW(), '" + DatabaseCounselor.getCurrentCounselor().getName() + "', NOW(), '" + DatabaseCounselor.getCurrentCounselor().getName() + "');";
            String addPatientQuery = "INSERT INTO patient(pt_id, pt_name, address_id, INS_PR, created_at, created_by, updated_at, updated_by, address_address_id) VALUES(" + addressForeignKey + ",'" + name + "', " + addressForeignKey + ", '" + insuranceProvider + "', NOW(), '" + DatabaseCounselor.getCurrentCounselor().getName() + "', NOW(), '" + DatabaseCounselor.getCurrentCounselor().getName() + "'," + addressForeignKey + ");";
            if (statement.executeUpdate(addAddressQuery) > 0)
                return statement.executeUpdate(addPatientQuery) > 0;

            return false; //If executeUpdate doesn't affect any rows in the address and patient tables
        }
        catch (SQLException err) {
            System.out.println("Error adding Patient: " + err.getMessage());
            return false;
        }
    }

    public static boolean updatePatient(int ID, String name, String insuranceProvider, String address1, String address2, String city, String state, String postal_code, String phone) {
       try {
           Statement statement = DatabaseConnection.getConnection().createStatement();
           String updateAddressQuery = "UPDATE address SET addressline_1 = '" + address1 + "', addressline_2 = '" + address2 + "', city = '" + city + "', state = '" + state + "', postal_code = '" + postal_code + "', phone = '" + phone + "', updated_at = NOW(), updated_by = '" + DatabaseCounselor.getCurrentCounselor().getName() + "' WHERE address_id = " + ID + ";";
           String updatePatientQuery = "UPDATE patient SET pt_name = '" + name + "', INS_PR = '" + insuranceProvider + "', updated_at = NOW(), updated_by = '" + DatabaseCounselor.getCurrentCounselor().getName() + "' WHERE pt_id = " + ID + ";";

           if (statement.executeUpdate(updateAddressQuery) > 0)
               return statement.executeUpdate(updatePatientQuery) > 0;

           return false; //If executeUpdate doesn't affect any rows in the address and patient tables
       }
       catch(SQLException err) {
           System.out.println("Error updating patient: " + err.getMessage());
           return false;
        }
    }

    public static boolean deletePatient(Patient patient) {
        try {
           Statement statement = DatabaseConnection.getConnection().createStatement();
           String deletePatientQuery = "DELETE FROM patient WHERE pt_id = " + patient.getPatientId() + ";";
           String deleteAddressQuery = "Delete FROM address WHERE address_id = " + patient.getPatientId() + ";";
           if (statement.executeUpdate(deletePatientQuery) > 0)
               return statement.executeUpdate(deleteAddressQuery) > 0;

           return false; //If executeUpdate doesn't affect any rows in the address and patient tables
        }
        catch(SQLException err) {
            System.out.println("Error deleting patient: " + err.getMessage());
            return false;
        }
    }
}
