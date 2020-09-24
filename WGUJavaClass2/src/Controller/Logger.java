package Controller;
import java.io.*;
import java.time.*;

public class Logger {
    public static void log (String username, boolean loggedIn) {
        try {
            FileWriter write;
            if (loggedIn) {
                write = new FileWriter("successful_login.txt", true);
                PrintWriter print_line = new PrintWriter(write);
                print_line.println(ZonedDateTime.now() + " Username: " + username);
            }
            else {
                write = new FileWriter("unsuccessful_login.txt", true);
                PrintWriter print_line = new PrintWriter(write);
                print_line.println(ZonedDateTime.now() + " Username: " + username);
            }
            write.close();
        } catch (IOException e) {
            System.out.println("Error logging file: " + e.getMessage());
        }
    }
}
