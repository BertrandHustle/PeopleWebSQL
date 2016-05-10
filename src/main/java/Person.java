import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Scott on 5/10/16.
 */
public class Person {

    int id;
    String firstName;
    String lastName;
    String email;
    String country;
    String ipAddress;

    public Person(int id, String firstName, String lastName, String email, String country, String ipAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        //todo clean up toString method

        return
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

}
