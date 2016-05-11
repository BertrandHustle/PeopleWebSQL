import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PeopleWeb {

    public static void main(String[] args) throws IOException{

        //init to read file
        File file = new File("people.csv");
        Scanner scanner = new Scanner(file);

        //uses PersonBuilder method to scan csv and make arraylist
        PersonBuilder pb = new PersonBuilder();
        ArrayList<Person> people = pb.PersonBuilder(scanner);

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

                    int backOffset = 0;

                    if (offset != 0) {
                        backOffset = offset - 20;
                        hash.put("backOffset", backOffset);
                    }

                    Integer nextOffSet = null;

                    if (offset < people.size() - 20){
                        nextOffSet = offset + 20;
                        hash.put("nextOffset", nextOffSet);
                    }

                    List peopleList = people.subList(offset, offset + 20);
                    hash.put("people", peopleList);

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

                    //put details of person in hash
                    Person selectedPerson = people.get(ID);

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

//todo: show person data on "single_person.mustache"