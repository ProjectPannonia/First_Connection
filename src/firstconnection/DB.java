package firstconnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    //Adatbázis elérési címe
    private final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    //Adatbázis létrehozása(ha még nincs)
    private final String URL = "jdbc:derby:sampleDb1;create = true";
    //Az adatbázishoz tartozó felhasználónév
    private final String USERNAME = "";
    //Az adatbázishoz tartozó jelszó
    private final String PASSWORD = "";

    //Létrehozunk egy null referenciájú connectiont
    private Connection conn = null;
    //Létrehozunk egy teherautót
    private Statement createStatement = null;
    //Létrehozunk a meta adatok lekérdezéséhez, egy objektumot
    private DatabaseMetaData dmbd = null;
    //Létrehoztunk egy whiteboardot, amin vissza érkezik az adat(ha van)
    private ResultSet rs1 = null;

    public DB() {
        // Megrpóbáljuk létrehozni a hidat
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött!");
        } catch (SQLException ex) {
            System.out.println("Probléma van a híd létrehozásakor.");
            System.out.println("" + ex);
        }
        //Ha létrejött.Készítünk egy megpakolható tehereautót.
        if (conn != null) {
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
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            // Az rs1 maga a whiteboard, ezen érkeznek vissza az adatok.
            //Ellenőrzi, hogy létezik-e ilyen tábla
            rs1 = dmbd.getTables(null, "APP", "USERS", null);
            //Azért kell a next, mert ha nincs már első eleme sem, akkor tudjuk, hogy nem létezik
            if (!rs1.next()) {
                //Ha nincs már első eleme se, akkor hívjuk a "teherautót". És sql parancsot küldünk az adatbázisnak.
                createStatement.execute("create table users(name varchar(20), address varchar(20))");
            }
        } catch (SQLException e) {
            System.out.println("Probléma van az adattáblák létrehozásakor!");
            System.out.println("" + e);
        }
    }

    //Felhasználó hozzáadása az adattáblához(adatküldés)
    public void addUser(String name,String address){
        String sql = "insert into users values('"+name+"','"+address+"')";
        try {
            createStatement.execute(sql);
            System.out.println("Adatküldés sikeres!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
