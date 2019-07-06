package firstconnection;

import java.util.ArrayList;

public class FirstConnection {
    public static void main(String[] args) {
        DB db = new DB();
        db.addUser("Ádám","Bakonyszombathely");
        db.addUserP("Béla","Tapolca");
        db.showAllUsers();
        db.showAllUsersMetaData();
        ArrayList<User> users = db.getAllUsers2();
        for(User u : users){
            System.out.println(u.getName());
        }
    }
}
