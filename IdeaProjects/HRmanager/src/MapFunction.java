import java.util.*;
import java.io.*;

public class MapFunction {

    static HashMap<String, String> students = new HashMap<String, String>();
    static HashMap<String, String> homerooms = new HashMap<String, String>();

    public static void main(String args[]) throws IOException {

        Scanner input = new Scanner(System.in);

        SaveInfo century_club = new SaveInfo();

        String[] names = new String[]{"Emiya", "Naoe", "Makoto", "Kiritsugu"};

        for (String name : names)
            century_club.add(name, "11-02", 10);

        for (String name : names) {
            Student student = century_club.searchStudent(name);
            System.out.println(student);
        }
    }

    public static String searchHR() {
        Set<Map.Entry<String, String>> st2 = homerooms.entrySet();
        Scanner input = new Scanner(System.in);
        System.out.println("Which Homeroom?");
        String HRsearch = input.nextLine();
        while (true) {
            for (Map.Entry<String, String> me : st2) {
                String key = me.getKey();
                if (key.equalsIgnoreCase(HRsearch)) {
                    return (me.getValue());
                }
            }
            System.out.println("Homeroom Not found.");
            HRsearch = input.nextLine();
        }
    }

    public static String searchST() {
        Set<Map.Entry<String, String>> st = students.entrySet();
        Scanner input = new Scanner(System.in);
        System.out.println("Which student?");
        String STsearch = input.nextLine();
        while (true) {

            for (Map.Entry<String, String> me : st) {
                String key = me.getKey();
                if (key.equalsIgnoreCase(STsearch)) {
                    return (me.getValue());
                }
            }
            System.out.println("Student Not found.");
            STsearch = input.nextLine();
        }
    }

    public static void addHR() {
        Scanner input = new Scanner(System.in);
        System.out.println("Add a homeroom, What is it called?");
        String HRname = input.nextLine();
        System.out.println("Who is in this homeroom? (Sepearate Names with a comma)");
        String HRstudents = input.nextLine();
        homerooms.put(HRname, "{" + HRstudents + "}");
    }

    public static void addST() {
        Scanner input = new Scanner(System.in);
        System.out.println("Add a student, Name?");
        String STname = input.nextLine();
        System.out.println("Clubs? (Sepearate Names with a comma)");
        String STclubs = input.nextLine();
        students.put(STname, "{" + STclubs + "}");
    }

    public static void readDatabase() throws IOException {
        try {
            FileReader fr = new FileReader("DATABASE.txt");
            BufferedReader br = new BufferedReader(fr);

            String HR = br.readLine();
            String HRDAT = HR.substring(1);
            HRDAT = HRDAT.substring(0, HRDAT.length() - 1);

            String[] HRpairs = HRDAT.split(", ");


            for (String pair : HRpairs) {
                String[] entry = pair.split("=");

                homerooms.put(entry[0].trim(), entry[1].trim());

            }

            String ST = br.readLine();
            String STDAT = ST.substring(1);
            STDAT = STDAT.substring(0, STDAT.length() - 1);
            String[] STpairs = STDAT.split(", ");

            for (String pair2 : STpairs) {
                int x = 0;
                String temp = "";
                while (pair2.charAt(x) != '=') {
                    temp += pair2.charAt(x);
                    x++;
                }
                String[] entry = pair2.split(temp.charAt(temp.length() - 1) + "=");
                students.put((entry[0] + temp.charAt(temp.length() - 1)).trim(), entry[1].trim());
            }
            br.close();
        } catch (IOException e) {
            System.out.println("NO DATABASE!");
        } catch (IndexOutOfBoundsException e) {

        }

    }

    public static void chartMaker() throws IOException {
        Set<Map.Entry<String, String>> st2 = homerooms.entrySet();

        for (Map.Entry<String, String> me : st2) {
            String HR = me.getKey();
            System.out.println(HR + ".html Created");
            BufferedWriter bw = new BufferedWriter(new FileWriter(HR + ".html"));
            String Students = me.getValue();
            Students = Students.substring(1);
            Students = Students.substring(0, Students.length() - 1);

            String[] ST = Students.split(",");
            ArrayList<String> HRclubs = new ArrayList<String>();
            HRclubs.add("");
            for (int i = 0; i < ST.length; i++) {

                if (students.get(ST[i]) == null) {
                    continue;
                }
                String STallClubs = students.get(ST[i]);
                STallClubs = STallClubs.substring(1);
                STallClubs = STallClubs.substring(0, STallClubs.length() - 1);
                String[] STclubs = STallClubs.split(",");
                for (int x = 0; x < STclubs.length; x++) {
                    String[] Cvalues = STclubs[x].split("=");
                    if (!HRclubs.contains(Cvalues[0])) {
                        HRclubs.add(Cvalues[0]);
                    }
                }

            }

//			System.out.println(HRclubs);
            HRclubs.add("Total");
            String[][] chart = new String[ST.length + 1][HRclubs.size()];

            for (int i = 0; i < HRclubs.size(); i++) {
                chart[0][i] = HRclubs.get(i);
            }
            for (int i = 0; i < ST.length; i++) {
                chart[i + 1][0] = ST[i];
            }

            for (int x = 1; x < chart.length; x++) {
                if (students.get(chart[x][0]) != null) {
                    int total = 0;
                    String STallClubs = students.get(chart[x][0]);
                    STallClubs = STallClubs.substring(1);
                    STallClubs = STallClubs.substring(0, STallClubs.length() - 1);
                    String[] STclubs = STallClubs.split(",");
                    for (int y = 1; y < chart[0].length; y++) {
                        for (int z = 0; z < STclubs.length; z++) {
                            String[] Cvalues = STclubs[z].split("=");
                            if (Cvalues[0].equalsIgnoreCase(chart[0][y])) {
                                try {
                                    chart[x][y] = Cvalues[1];
                                    total += Integer.parseInt(Cvalues[1]);
                                } catch (ArrayIndexOutOfBoundsException e) {

                                }
                            }
                        }

                    }
                    chart[x][chart[0].length - 1] = Integer.toString(total);

                }

            }
//			print2D(chart);

            bw.write("<html> <body>");

            bw.write("<style>\r\n" + "table {\r\n" + "  font-family: arial, sans-serif;\r\n"
                    + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n" + "\r\n" + "td, th {\r\n"
                    + "  border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n" + "  padding: 8px;\r\n" + "}\r\n"
                    + "\r\n" + "tr:nth-child(even) {\r\n" + "  background-color: #dddddd;\r\n" + "}\r\n" + "</style>");

            bw.write("<h1>" + HR + "</h1>");
            bw.write("<table>");
            for (int i = 0; i < chart.length; i++) {
                bw.write("<tr>");
                for (int j = 0; j < chart[0].length; j++) {

                    if (chart[i][j] == null) {
                        bw.write("<th>" + "  " + "</th>");
                    } else {
                        bw.write("<th>" + chart[i][j] + "</th>");
                    }
                }
                bw.write("</tr>");
            }
            bw.write("</table>");

            bw.write("</body> </html>");

            bw.close();

        }
    }

    public static void saveMap() throws IOException {

        FileWriter fw = new FileWriter("DATABASE.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(homerooms.toString() + "\n");
        bw.write(students.toString());

        bw.close();

    }

    public static void print2D(String mat[][]) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void deleteHR() {
        Scanner input = new Scanner(System.in);
        System.out.println("Delete a homeroom, What is it called?");
        String HRname = input.nextLine();
        homerooms.remove(HRname);

    }

}
