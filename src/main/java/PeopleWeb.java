import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PeopleWeb {

    public static void main(String[] args) throws IOException{

        //todo make this separate method

        //init to read file
        File file = new File("people.csv");
        Scanner scanner = new Scanner(file);

        PersonBuilder pb = new PersonBuilder();
        ArrayList<Person> people = pb.PersonBuilder(scanner);

        System.out.println(people.get(75));

    }
}
