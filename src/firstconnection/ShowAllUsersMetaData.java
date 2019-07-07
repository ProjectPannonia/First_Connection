package firstconnection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ShowAllUsersMetaData {
    DB db = new DB();

    public void showAllUsersMetaData(){
        ResultSet rs = null;
        String sql = "select * from users";
        ResultSetMetaData rsmd = null;

        try {
            rs = db.createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 1; i <= columnCount; i++){
                System.out.println(" | " + rsmd.getColumnName(i) + " | ");
            }
        } catch (SQLException e) {
            System.out.println("Valami probléma van a ShowAllUsersMetaData-val!");
            System.out.println("" + e);
        }
    }
}
