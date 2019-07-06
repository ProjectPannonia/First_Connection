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
    //Felturbozott teherauto
    private PreparedStatement ps = null;

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
            // Az rs1 maga a whiteboard, ezen érkeznek vissza az adatok.Ellenőrzi, hogy létezik-e ilyen tábla
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
            System.out.println("Adatküldés sikertelen a statement-el");
            System.out.println("" + e);
        }
    }

    //Felhasználó hozzáadása a táblához a "felturbozott" kocsival
    public void addUserP(String name, String address){
        try {
            //Paraméterek átadása nem közvetlen, így véd a hibás adatbeviteltől
            String sql = "insert into users values(?, ?)";
            ps = conn.prepareStatement(sql);
            //Típusellenőrzés
            ps.setString(1,name);
            ps.setString(2,address);
            ps.execute();
            System.out.println("Adatküldés sikeres a preparedStatement-el");
        } catch (SQLException e) {
            System.out.println("Adatküldés sikertelen a preparedStatement-el");
            System.out.println("" + e);
        }
    }
    public void showAllUsers(){
        String sql = "select * from users";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
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
    public void showAllUsersMetaData(){
        ResultSet rs = null;
        String sql = "select * from users";
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 1; i <= columnCount; i++){
                System.out.println(" | " + rsmd.getColumnName(i) + " | ");
            }
        } catch (SQLException e) {
            System.out.println("A showAllUsersMetaData lekérdezés sikertelen!");
            System.out.println("" + e);
        }
    }
}
