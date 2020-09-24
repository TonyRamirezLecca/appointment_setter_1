package Model;

public class Counselor {
    String name;
    public Counselor(String name) {
        this.name = name;
    }
    public static int getId(String name) {
        switch(name) {
            case "Jonathon Roberts":
                return 2;
            case "Rachel Summers":
                return 3;
            case "Marco Cortez":
                return 4;
            case "Sammy Ten":
                return 5;
            default:
                return 1;
        }
    }
    public String getName() {
        return name;
    }
    public void setName() {
        this.name = name;
    }
}
