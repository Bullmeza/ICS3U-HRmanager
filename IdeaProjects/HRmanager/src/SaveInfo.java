import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SaveInfo {

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> HRs = new ArrayList<String>();
    private ArrayList<Integer> points = new ArrayList<Integer>();
    private ArrayList<String> gender = new ArrayList<String>();

    private ArrayList<Info> data = new ArrayList<Info>();


    public void add(String name, String HR, int point) {
        String name_formatted = name.strip();
        if (name.contains("-")) {
            name_formatted = name.substring(0, name.indexOf("-"));
        }
        if (name.contains(" ")) {
            name_formatted = name.substring(0, name.indexOf(" "));
        }


        names.add(name.strip());
        genderize(name_formatted);
        HRs.add(HR);
        points.add(point);

        Info person = new Info(point, name.strip(), HR, genderize(name_formatted));
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

            String data = bod.toString();
            System.out.println(data);
            data = data.replace("{","");
            data = data.replace("}","");
            double probability = Double.parseDouble(data.split(",")[2].split(":")[1]);
            System.out.println(probability);
            String gndr = data.split(",")[1].split(":")[1].replaceAll("\"", "");

            if (probability < 0.7){
                String gender = input.nextLine();
                if (gender.toLowerCase().contains("m")){
                    return "M";
                }else if (gender.toLowerCase().contains("f")){
                    return "F";
                }
            }

            if (gndr.equalsIgnoreCase("male")){
                return "M";
            }else{
                return "F";
            }

//            if (data.contains("female")) {
//                if (probability < 0.7) {
//                    System.out.println("Uncertain Gender, what gender is " + name + "? (M/F)");
//                    userinput = input.nextLine();
//                    if (userinput.equalsIgnoreCase("M")) {
//                        gender.add("M");
//                        return "M";
//                    } else if (userinput.equalsIgnoreCase("F")) {
//                        gender.add("F");
//                        return "F";
//                    }
//                } else {
//                    gender.add("F");
//                    return "F";
//                }
//            } else if (data.contains("male")) {
//                Double probability = Double.parseDouble(data.substring(data.indexOf("0"), data.indexOf("0.") + 4));
//                System.out.print(probability);
//                if (probability < 0.7) {
//                    System.out.println("Uncertain Gender, what gender is " + name + "? (M/F)");
//                    userinput = input.nextLine();
//                    if (userinput.equalsIgnoreCase("M")) {
//                        gender.add("M");
//                        return "M";
//                    } else if (userinput.equalsIgnoreCase("F")) {
//                        gender.add("F");
//                        return "F";
//                    }
//                } else {
//                    gender.add("M");
//                    return "M";
//                }
//            } else if (data.contains("null")) {
//                System.out.println("Uncertain Gender, what gender is " + name + "? (M/F)");
//                userinput = input.nextLine();
//                if (userinput.equalsIgnoreCase("M")) {
//                    gender.add("M");
//                    return "M";
//                } else if (userinput.equalsIgnoreCase("F")) {
//                    gender.add("F");
//                    return "F";
//                }
//
//            }
//
//
        } catch (IOException e) {
            String gender = input.nextLine();
            if (gender.toLowerCase().contains("m")){
                return "M";
            }else if (gender.toLowerCase().contains("f")){
                return "F";
            }
        }
        return null;
    }

    public String[] searchST(String name) {
        String[] ret = new String[3];

        int id = names.indexOf(name.strip());
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equalsIgnoreCase(name)) {
                id = i;
                break;
            }
        }
        if (id == -1) {
            return new String[] {"NOID"};
        }
        Info person = data.get(id);
        return new String[] {person.getHomeroom(), person.getGender(), Integer.toString(person.getPoints())};
//        if (id == -1) {
//            String[] noId = new String[1];
//            noId[0] = "NOID";
//            return noId;
//        }
//        ret[0] = HRs.get(id);
//        ret[1] = gender.get(id);
//        ret[2] = points.get(id).toString();

    }
}

