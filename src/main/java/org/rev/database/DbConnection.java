package org.rev.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String connectionUrl="jdbc:mysql://localhost:3306/e_commerce";

    public static final String username="root";
    public static final String password="root";

    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(connectionUrl,username,password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


        return connection;
    }
}
