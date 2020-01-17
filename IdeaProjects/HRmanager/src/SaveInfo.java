import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SaveInfo {

    private ArrayList<Info> data = new ArrayList<Info>();


    public void add(String name, String HR, int point) {
        name.strip();
        int counter = 0;
        String name_formatted = name;
        if (name.contains("-")) {
            name_formatted = name.substring(0, name.indexOf("-"));
        }
        if (name.contains(" ")) {
            name_formatted = name.substring(0, name.indexOf(" "));
        }

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equalsIgnoreCase(name)) {
                Info person = new Info(point, name, HR, genderize(name_formatted));
                data.set(i, person);
            } else {
                counter++;
            }
        }
        if (counter == data.size()) {
            Info person = new Info(point, name, HR, genderize(name_formatted));
            data.add(person);

        }

    }

    private String genderize(String name) {

        Scanner input = new Scanner(System.in);
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("https://api.genderize.io/?name=" + name).ignoreContentType(true).get();
            Elements bod = document.getElementsByTag("body");

            String data = bod.toString();
            data = data.replace("{", "");
            data = data.replace("}", "");
            double probability = Double.parseDouble(data.split(",")[2].split(":")[1]);
            String gndr = data.split(",")[1].split(":")[1].replaceAll("\"", "");

            if (probability < 0.7) {
                System.out.println("What gender is " + name + "? (M/F)");
                String gender = input.nextLine();
                if (gender.toLowerCase().equalsIgnoreCase("m")) {
                    return "M";
                } else if (gender.toLowerCase().equalsIgnoreCase("f")) {
                    return "F";
                }
            }
            if (gndr.equalsIgnoreCase("female")) {
                return "F";
            } else {
                return "M";
            }
        } catch (IOException e) {
            System.out.println("What gender is " + name + "? (M/F)");
            String gender = input.nextLine();
            if (gender.toLowerCase().contains("m")) {
                return "M";
            } else if (gender.toLowerCase().contains("f")) {
                return "F";
            }
        }
        return null;
    }

    public String[] searchST(String name) {
        String[] ret = new String[3];

        int id = name.indexOf(name.strip());
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equalsIgnoreCase(name)) {
                id = i;
                break;
            }
        }
        if (id == -1) {
            return new String[]{"NOID"};
        }
        Info person = data.get(id);
        return new String[]{person.getHomeroom(), person.getGender(), Integer.toString(person.getPoints())};

    }

    private void sort() {
        boolean sort = false;
        while (!sort) {
            sort = true;
            for (int i = 0; i < data.size() - 1; i++) {
                if (data.get(i).getPoints() < data.get(i + 1).getPoints()) {
                    Collections.swap(data, i, i + 1);
                    sort = false;
                }

            }
        }
    }

    public String[][] allData() {

        String[][] ret = new String[data.size()][4];
        sort();
        for (int i = 0; i < data.size(); i++) {
            ret[i][0] = data.get(i).getHomeroom().split("-")[0];
            ret[i][1] = data.get(i).getName();
            ret[i][2] = String.valueOf(data.get(i).getPoints());
            ret[i][3] = data.get(i).getGender();

        }

        return ret;

    }


}

