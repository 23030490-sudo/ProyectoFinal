package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

        private static final String URL = "jdbc:mysql://localhost:3306/estacionamiento_db";
        private static final String USUARIO = "root";
        private static final String CLAVE = "1234";

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(URL, USUARIO, CLAVE);
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Driver JDBC no encontrado. Verifica la dependencia en pom.xml.");
                throw new SQLException("Driver JDBC no encontrado.", e);
            }
        }

}
