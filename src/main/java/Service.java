import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

    private final Connection connection;

    public Service(Connection connection) {
        this.connection = connection;
    }

    //init database
    public void initDatabase() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("DROP TABLE IF EXISTS people");
        statement.execute("CREATE TABLE people (id IDENTITY, firstName VARCHAR, lastName VARCHAR, email VARCHAR, country VARCHAR, ipAddress VARCHAR)");
    }

    public void populateDatabase() throws IOException, SQLException{

        //init to read file
        File file = new File("people.csv");
        Scanner scanner = new Scanner(file);

        //uses PersonBuilder method to scan csv and make arraylist
        PersonBuilder pb = new PersonBuilder();
        ArrayList<Person> peopleArray = pb.PersonBuilder(scanner);

        for (Person person : peopleArray) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO people VALUES (NULL, ?, ?, ?, ?, ?)");
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getCountry());
            statement.setString(5, person.getIpAddress());
            statement.executeUpdate();

            //sets person id to autogenerated id from SQL
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            person.setId(resultSet.getInt(1));

        }

    }

    public Person selectPerson (int id) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM people WHERE id=?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        ArrayList<Person> selectedPerson = new ArrayList<>();
        while(resultSet.next()){

            Person person = new Person(

                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("email"),
                    resultSet.getString("country"),
                    resultSet.getString("ipAddress")
            );

            selectedPerson.add(person);

        }

        return selectedPerson.get(0);

    }

    public void insertPerson() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO people VALUES (NULL, ?, ?, ?, ?, ?)");
        statement.setString(1, "Scott");
        statement.setString(2, "Greenberg");
        statement.setString(3, "email.com");
        statement.setString(4, "USA");
        statement.setString(5, "1.2.3.4");
        statement.executeUpdate();

    }

    //queries database and returns arraylist of people
    public ArrayList<Person> selectPeople() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM people");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Person> people = new ArrayList<>();

        while(resultSet.next()){

            Person person = new Person(

                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("email"),
                    resultSet.getString("country"),
                    resultSet.getString("ipAddress")
            );

            people.add(person);

        }

        return people;

    }


}
