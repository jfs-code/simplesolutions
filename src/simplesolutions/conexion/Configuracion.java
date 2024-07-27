package simplesolutions.conexion;

/**
 *
 * @author JFS
 */
public interface Configuracion {

//    static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    static String DATA_BASE = "databaseName=simplesolutions";   
//    static String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;"+DATA_BASE;
//    static String USERNAME = "SS01";
//    static String PASSWORD = "SS123456";
    
//    String DRIVER = "org.postgresql.Driver";
//    String DATA_BASE = "public";
//    String CONNECTION_URL = "jdbc:postgresql://localhost:5432/"+DATA_BASE;    
//    String USERNAME = "postgres";
//    String PASSWORD = "postgres";
    
    String DRIVER = "com.mysql.jdbc.Driver";
    String DATA_BASE = "simplesolutions";   
    String CONNECTION_URL = "jdbc:mysql://localhost:3306/"+DATA_BASE;
    String USERNAME = "root";
    String PASSWORD = "";

}

