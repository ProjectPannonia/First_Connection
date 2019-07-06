package firstconnection;

public class FirstConnection {
    public static void main(String[] args) {
        DB db = new DB();
        db.addUser("Ádám","Bakonyszombathely");
        db.addUserP("Béla","Tapolca");
        db.showAllUsers();
    }
}
