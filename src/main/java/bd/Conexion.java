package bd;


import util.Util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by user on 28/03/2017.
 */
public class Conexion {

    Connection conn;

    private static Conexion ourInstance = new Conexion();

    public static Conexion getInstance() {
        return ourInstance;
    }

    private Conexion() {
    }

    public String conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.setProperty("javax.net.ssl.keyStore", "keystore");
            //System.setProperty("javax.net.ssl.keyStorePassword", "rock55241795");
            //System.setProperty("javax.net.ssl.trustStore", "truststore");
            //System.setProperty("javax.net.ssl.trustStorePassword", "rock55241795");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia", "root", "");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia?autoReconnect=true&useSSL=true","admin","admin");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","admin","admin");
            //conn = DriverManager.getConnection("jdbc:mysql://138.68.16.145:3306/farmacia","admin","admin");
            /*DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Server name: "
                    + metaData.getDatabaseProductName());
            System.out.println("Server version: "
                    + metaData.getDatabaseProductVersion());
            System.out.println("Driver name: "
                    + metaData.getDriverName());
            System.out.println("Driver version: "
                    + metaData.getDriverVersion());
            System.out.println("JDBC major version: "
                    + metaData.getJDBCMajorVersion());
            System.out.println("JDBC minor version: "
                    + metaData.getJDBCMinorVersion());*/
            return "Se ha conectado a la base de datos";
        } catch (Exception e) {
            String titulo = "Error al Conectar a la base de datos";
            String mensaje = e.getLocalizedMessage();
            Util.makeError(titulo, mensaje);
            return "Ocurrió un error al conectar con la base de datos " + e.getLocalizedMessage();
        }
    }

    public Connection getConexion()
    {
        return conn;
    }
}
