package simplesolutions.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author JFS
 */

@SuppressWarnings("StaticNonFinalUsedInInitialization")
public class Conexion implements Configuracion {
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error en conexión : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en conexión : " + e.getMessage());
        }
        return connection;
    }
    
    public boolean ejecutar(String sql) {
        boolean var;
        try {
            Statement sentencia = getConnection().createStatement();
            var = sentencia.executeUpdate(sql) != 0;
        } catch (SQLException error) {
            System.out.println("Error en ejecución: " + error.getMessage());
            var = false;
        }
        return var;
    }

    public ResultSet consultar(String sql) {
        ResultSet resultado = null;
        try {
            Statement sentencia = getConnection().createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException error) {
            System.out.println("Error en la consulta: " + error.getMessage());
        }
        return resultado;
    }

}
