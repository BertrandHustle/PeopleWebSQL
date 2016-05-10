import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PeopleWeb {

    public static void main(String[] args) throws IOException{

        //todo make this separate method

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
                    hash.put("people", people);
                    return new ModelAndView(hash, "people.mustache");
                },

        new MustacheTemplateEngine()

        );

        //get route for showing single person on a page

        Spark.get(
                "/person",
                (request, response) -> {

                    response.redirect("/");

                    return "test";

                }

        );

    }
}
