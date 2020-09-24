package Model;

public class State {
    String state;
    int numStateAppointments;
    static String[] allStates = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

    public State(String state) {
        this.state = state;
        this.numStateAppointments = 0;
    }
    public void incrementStateAppointment() {
        this.numStateAppointments++;
    }

    public String getState() {
        return state;
    }
    public static String[] getStates() {
        return allStates;
    }
    public int getNumStateAppointments() {
        return numStateAppointments;
    }
}
