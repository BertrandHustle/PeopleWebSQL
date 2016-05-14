
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

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
