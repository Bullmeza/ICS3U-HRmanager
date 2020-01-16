public class Student {
    int points;
    String name;
    String homeroom;
    String gender;

    public Student(int points, String name, String homeroom, String gender) {
        this.points = points;
        this.name = name;
        this.homeroom = homeroom;
        this.gender = gender;
    }

    @Override
    public String toString() {
        String stringdata = "Student (" + getGender() + "), ";
        stringdata += getName() + ", ";
        stringdata += "Homeroom: " + getHomeroom() + ", ";
        stringdata += "Club points: " + Integer.toString(getPoints());
        return stringdata;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeroom() {
        return homeroom;
    }

    public void setHomeroom(String homeroom) {
        this.homeroom = homeroom;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
