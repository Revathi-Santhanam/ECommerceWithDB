package org.rev.utils;


import org.rev.database.DbConnection;
import org.rev.models.User;

import java.sql.*;

public class UserUtils {

    public static User loggedInUser;
    private static Connection connection;
    public static User getLoggedInUser ( ) {
        return loggedInUser;
    }

    public static void setLoggedInUser (User loggedInUser) {
        connection= DbConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QueryUtils.SELECT_USER_QUERY);
            while (rs.next()){
                User user=new User();
                 user.setId(rs.getInt("u_id"));
                 user.setRoleId(rs.getInt("r_id"));
                 user.setEmail(rs.getString("u_name"));
                 UserUtils.loggedInUser=user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
