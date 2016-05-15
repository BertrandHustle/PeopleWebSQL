import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ServiceTests {


    Connection connection;
    Service service;

    @Before
    public void before() throws SQLException {
        // create a server
        Server server = Server.createTcpServer("-baseDir", "./data").start();

        // created our connection
        connection = DriverManager.getConnection("jdbc:h2:" + server.getURL() + "/test", "", null);

        service = new Service(connection);
    }
    /**
     * Given a person id
     * When person is selected by id
     * Then person is returned
     */

    @Test
    public void whenInsertPersonReturnsSelection() throws SQLException, IOException {

        //arrange
        service.initDatabase();

        //act
        service.insertPerson();
        Person testPerson = service.selectPerson(1);

        //assert

        assertThat(testPerson.getFirstName(), is("Scott"));

    }

    /**
     * Given a database table of people
     * When method is executed
     * Then returns arraylist of Person objects
     */

    @Test
    public void whenSelectPeopleThenReturnsPersonArraylist() throws SQLException, IOException {

        //arrange
        service.initDatabase();

        //act
        service.populateDatabase();
        ArrayList<Person> testPeople = service.selectPeople(1);

        //assert
        assertThat(testPeople.size(), is(20));
    }

    @After
    public void after() throws SQLException {

        connection.close();

        File dataFolder = new File("data");
        if(dataFolder.exists()) {
            for(File file : dataFolder.listFiles()){
                if(file.getName().startsWith("test.h2.")) {
                    file.delete();
                }
            }
        }
    }

}
