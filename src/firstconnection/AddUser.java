package firstconnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {
    DB db = new DB();

    public void addUser(User user){
        String sql = "select * from users values(?,?)";
        try {
            PreparedStatement ps = db.conn.prepareStatement(sql);
            ps.setString(1,user.getName());
            ps.setString(2,user.getAddress());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Valami baj van az addUser methodal!");
            System.out.println("" + e);
        }
    }
}
