import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SaveInfo {

    private ArrayList<Student> data = new ArrayList<Student>();


    public void add(String name, String HR, int point) {
        String name_formatted = name.strip();
        if (name.contains("-")) {
            name_formatted = name.substring(0, name.indexOf("-"));
        }
        if (name.contains(" ")) {
            name_formatted = name.substring(0, name.indexOf(" "));
        }

        Student person = new Student(point, name.strip(), HR, genderize(name_formatted));
        data.add(person);

    }

    private String genderize(String name) {

        String userinput = "";
        Scanner input = new Scanner(System.in);
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("https://api.genderize.io/?name=" + name).ignoreContentType(true).get();
            Elements bod = document.getElementsByTag("body");

            String data = bod.toString();;
            data = data.replace("{","");
            data = data.replace("}","");
            double probability = Double.parseDouble(data.split(",")[2].split(":")[1]);
            String gndr = data.split(",")[1].split(":")[1].replaceAll("\"", "");

            if (probability < 0.7){
                System.out.println("Unable to determine gender, please enter: ");
                String gender = input.nextLine();
                if (gender.toLowerCase().contains("f")){
                    return "F";
                }else if (gender.toLowerCase().contains("m")){
                    return "M";
                }
            }

            if (gndr.equalsIgnoreCase("male")){
                return "M";
            }else {
                return "F";
            }
        } catch (IOException e) {
            System.out.println("Unable to connect to website, please enter gender: ");
            String gender = input.nextLine();
            if (gender.toLowerCase().contains("m")){
                return "M";
            }else if (gender.toLowerCase().contains("f")){
                return "F";
            }
        }
        return null;
    }

    public Student searchStudent(String name) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equalsIgnoreCase(name)) {
                return data.get(i);
            }
        }
        return null;
    }
}

