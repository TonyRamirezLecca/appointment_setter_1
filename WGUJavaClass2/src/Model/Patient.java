package Model;

public class Patient {
    int patientId;
    String patientName;
    String insuranceProvider;
    String address1;
    String address2;
    String city;
    String state;
    String postalCode;
    String phone;

    public Patient(int patientId, String patientName, String insuranceProvider, String address1, String address2, String city, String state, String postalCode, String phone) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.insuranceProvider = insuranceProvider;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getPatientId() {
        return patientId;
    }
    public String getPatientName() {
        return patientName;
    }
    public String getInsuranceProvider() { return insuranceProvider; }
    public String getAddress1() {
        return address1;
    }
    public String getAddress2() {
        return address2;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getPhone() {
        return phone;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
