import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PeopleWeb {

    public static void main(String[] args) throws IOException{

        //init to read file
        File file = new File("people.csv");
        Scanner scanner = new Scanner(file);

        //init for People arrayList
        ArrayList<Person> people = new ArrayList<>();

        //skips first line
        scanner.nextLine();

        //reading loop, splits on commas. constructs Persons, adds to arraylist
        while (scanner.hasNext()){

            //Person builder
            String[] thisLine = scanner.nextLine().split(",");
            int id = Integer.parseInt(thisLine[0]);
            String firstName = thisLine[1];
            String lastName = thisLine[2];
            String email = thisLine[3];
            String country = thisLine[4];
            String ipAddress = thisLine[5];

            Person person = new Person (
                    id,
                    firstName,
                    lastName,
                    email,
                    country,
                    ipAddress);

            people.add(person);

        }

        System.out.println(people.get(88));

    }
}
