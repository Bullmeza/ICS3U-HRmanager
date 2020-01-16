import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Genderize {

    public static void main(String args[]){
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("https://api.genderize.io/?name=robertmuresan").ignoreContentType(true).get();
            Elements bod = document.getElementsByTag("body"); //Get title

            String data = bod.toString();
            System.out.println(data);





            if(data.contains("male")){
                Double probability = Double.parseDouble(data.substring(data.indexOf("0"), data.indexOf("0.") + 4));
                System.out.print(probability);
            }
            else if (data.contains("female")){
                Double probability = Double.parseDouble(data.substring(data.indexOf("0"), data.indexOf("0.") + 4));
                System.out.print(probability);

            }
            else if(data.contains("null")){

            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
