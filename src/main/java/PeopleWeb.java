import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PeopleWeb {

    public static void main(String[] args) throws IOException, SQLException{

        //creates server
        Server server = Server.createTcpServer("-baseDir", "./data").start();

        //creates connection
        String jdbcUrl = "jdbc:h2:" + server.getURL() + "/main";
        System.out.println(jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, "", null);

        //creates/configures web service
        Service service =  new Service(connection);

        //init/populate database
        service.initDatabase();
        service.populateDatabase();

        /*
        //this should be its own method
        //init to read file
        File file = new File("people.csv");
        Scanner scanner = new Scanner(file);

        //uses PersonBuilder method to scan csv and make arraylist
        PersonBuilder pb = new PersonBuilder();
        ArrayList<Person> people = pb.PersonBuilder(scanner);
        */

        //GET route for webroot

        Spark.get(
                "/",
                (request, response) -> {

                    HashMap hash = new HashMap<>();

                    //init to keep track of offset
                    int offset = 0;

                    try {
                        String pageOffset = request.queryParams("offset");
                        offset = Integer.parseInt(pageOffset);
                    } catch (NumberFormatException nfe){
                        offset = 0;
                    }

                    ArrayList<Person>people = service.selectPeople(offset);

                    int backOffset = 0;

                    if (offset != 0) {
                        backOffset = offset - 20;
                        hash.put("backOffset", backOffset);
                    }

                    Integer nextOffSet = null;
                    int size = service.peopleSize();

                    //todo: fix this so it works past page 0
                    if (offset < size - 20){
                        nextOffSet = offset + 20;
                        hash.put("nextOffset", nextOffSet);
                    }

                    //List peopleList = people.subList(offset, offset + 20);
                    hash.put("people", people);

                    return new ModelAndView(hash, "people.mustache");
                },

        new MustacheTemplateEngine()

        );

        //get route for showing single person on a page

        Spark.get(
                "/person",
                (request, response) -> {

                    HashMap hash = new HashMap<>();

                    //get person from ID
                    int ID = Integer.parseInt(request.queryParams("id"));
                    Person selectedPerson = service.selectPerson(ID);

                    //put details of person in hash
                    //subtracts 1 to compensate for skipped first line in csv
                    //Person selectedPerson = people.get(ID - 1);

                    String firstName = selectedPerson.getFirstName();
                    String lastName = selectedPerson.getLastName();
                    String email = selectedPerson.getEmail();
                    String country = selectedPerson.getCountry();
                    String ipAddress = selectedPerson.getIpAddress();

                    hash.put("firstName", firstName);
                    hash.put("lastName", lastName);
                    hash.put("email", email);
                    hash.put("country", country);
                    hash.put("ipAddress", ipAddress);

                    return new ModelAndView(hash, "single_person.mustache");

                },

                new MustacheTemplateEngine()

        );

    }
}
