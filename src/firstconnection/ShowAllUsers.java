package firstconnection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ShowAllUsers {
    DB db = new DB();

    public void showAllUsers(){
        String sql = "select * from users";
        try {
            ResultSet rs = db.createStatement.executeQuery(sql);
            while (rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println(name + " | " + address);
            }
            System.out.println("Az adatbekérés sikeres!");
        } catch (SQLException e) {
            System.out.println("Probléma van az adatbekéréssel!");
            System.out.println("" + e);
        }
    }
}
