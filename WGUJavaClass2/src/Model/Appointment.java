package Model;

public class Appointment {

    int appointmentId;
    int patientId;
    int counselorId;
    int appointmentTypeId;
    String counselorName;
    String appointmentDescription;
    String appointmentNotes;
    String appointmentStart;
    String appointmentDate;

    public Appointment(int appointmentId, int patientId, int counselorId, int appointmentTypeId, String counselorName, String appointmentDescription, String appointmentNotes, String appointmentStart, String appointmentDate) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.counselorId = counselorId;
        this.appointmentTypeId = appointmentTypeId;
        this.counselorName = counselorName;
        this.appointmentDescription = appointmentDescription;
        this.appointmentNotes = appointmentNotes;
        this.appointmentStart = appointmentStart;
        this.appointmentDate = appointmentDate;
    }

    public int getAppointmentId() {
        return appointmentId;
    }
    public int getPatientId() {
        return patientId;
    }
    public int getCounselorId() {
        return counselorId;
    }
    public int getAppointmentTypeId() {
        return appointmentTypeId;
    }
    public String getCounselorName() { return counselorName; }
    public String getAppointmentDescription() {
        return appointmentDescription;
    }
    public String getAppointmentNotes() {
        return appointmentNotes;
    }
    public String getAppointmentStart() {
        return appointmentStart;
    }
    public String getAppointmentDate() { return appointmentDate; }
    public static int getAppointmentType(String appointmentDescription) {
        switch(appointmentDescription) {
            case "Family Counseling":
                return 1;
            case "Personal Counseling":
                return 2;
            case "Marriage Counseling":
                return 3;
            default:
                return 0;
        }
    }
    public String getAppointmentName(int appointmentId) {
        switch (appointmentId) {
            case 1:
                return "Family Counseling";
            case 2:
                return "Personal Counseling";
            case 3:
                return "Marriage Counseling";
            default:
                return "Prefer not to say";
        }
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public void setCounselorId(int counselorId) {
        this.counselorId = counselorId;
    }
    public void setAppointmentTypeId(int appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }
    public void setCounselorName(String counselorName) { this.counselorName = counselorName; }
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }
    public void setAppointmentNotes(String appointmentNotes) {
        this.appointmentNotes = appointmentNotes;
    }
    public void setAppointmentStart(String appointmentStart) {
        this.appointmentStart = appointmentStart;
    }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

}
