package firstconnection;

import java.util.ArrayList;

public class FirstConnection {
    public static void main(String[] args) {
        DB db = new DB();
        AddUser au = new AddUser();
        ShowAllUsersMetaData saumd = new ShowAllUsersMetaData();
        GetAllUsers gau = new GetAllUsers();
        ShowAllUsers sau = new ShowAllUsers();
        db.addUser("Ádám","Bakonyszombathely");
        db.addUserP("Béla","Tapolca");
        sau.showAllUsers();
        saumd.showAllUsersMetaData();
        ArrayList<User> users = gau.getAllUsers();
        for(User u : users){
            System.out.println(u.getName());
        }
    }
}
