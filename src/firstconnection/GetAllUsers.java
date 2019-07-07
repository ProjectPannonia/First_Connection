package firstconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//Userek tábla lekérdezése, majd ArrayListként visszaadása
public class GetAllUsers {
    DB db = new DB();
    public ArrayList<User> getAllUsers(){
        String sql = "select * from users";
        ArrayList<User> users = null;
        users = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = db.createStatement.executeQuery(sql);

            while (rs.next()){
                User actualUser = new User(rs.getString("name"),rs.getString("address"));
                users.add(actualUser);
            }
        }catch (SQLException e) {
            System.out.println("Valami baj van a getAllUsers működése közben!");
            System.out.println("" + e);
        }
        return users;
    }
}
