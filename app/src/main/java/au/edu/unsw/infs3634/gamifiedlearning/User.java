package au.edu.unsw.infs3634.gamifiedlearning;

public class User {

    public String firstName, lastName, email;
    public int avatar, points;
    public boolean first, second, third;

    public User(){

    }

    public User(String fName, String lName, String email, int avatar, int points, boolean first, boolean second, boolean third){
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.avatar = avatar;
        this.points = points;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isSecond() {
        return second;
    }

    public void setSecond(boolean second) {
        this.second = second;
    }

    public boolean isThird() {
        return third;
    }

    public void setThird(boolean third) {
        this.third = third;
    }
}
