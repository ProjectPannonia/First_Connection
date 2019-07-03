package firstconnection;

import java.sql.*;

public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDb; create = true";
    final String USERNAME = "";
    final String PASSWORD = "";
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dmbd = null;
    ResultSet rs1 = null;
    public DB(){
        // Megrpóbáljuk létrehozni a hidat
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött!");
        } catch (SQLException ex) {
            System.out.println("Probléma van a híd létrehozásakor.");
            System.out.println("" + ex);
        }
        //Ha létrejött.Készítünk egy megpakolható tehereautót.
        if(conn != null) {
            try {
                createStatement = conn.createStatement();
                System.out.println("A Statement létrejött!");
            } catch (SQLException ex) {
                System.out.println("A Statement nem jött létre!");
                System.out.println("" + ex);
            }
        }
        //Megnézzük, hogy üres-e az adatbázis?
        try {
             dmbd = conn.getMetaData();
        } catch (SQLException e) {
            System.out.println("" + e);
        }
        try {
            rs1 = dmbd.getTables(null,"APP","USERS",null);
            if(!rs1.next()){
                createStatement.execute("create table users(name varchar(20), age varchar(20))");
            }
        } catch (SQLException e) {
            System.out.println("Probléma van az adattáblák létrehozásakor!");
            System.out.println("" + e);
        }

    }
}
