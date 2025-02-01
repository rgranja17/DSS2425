package AlocadabraDL;

import java.sql.*;

public class DAOconfig {
    static final String USERNAME = "root";
    static final String PASSWORD = "12345678";
    private static final String DATABASE = "Alocadabra";
    private static final String DRIVER = "jdbc:mysql";
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE+"?useSSL=false&serverTimezone=Europe/Lisbon";
    public static final Connection conexao = connect();

    private static Connection connect() {
        try {
            System.out.println("Connecting to database...");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed!" + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}
