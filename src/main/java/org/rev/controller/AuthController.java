package org.rev.controller;


import org.rev.controller.implemantation.IAuthController;
import org.rev.database.DbConnection;
import org.rev.models.Role;
import org.rev.models.User;
import org.rev.utils.AppException;
import org.rev.utils.QueryUtils;
import org.rev.utils.StringUtils;
import org.rev.view.AuthPage;
import org.rev.view.LoginPage;
import org.rev.view.RegisterPage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.rev.utils.AppInput.enterInt;
import static org.rev.utils.AppInput.enterString;
import static org.rev.utils.UserUtils.setLoggedInUser;
import static org.rev.utils.Utils.println;

public class AuthController implements IAuthController {


    private final HomeController homeController;
    private final AuthPage authPage;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;
    private final AdminController adminController;
    private Connection connection;

    public AuthController () {
        authPage=new AuthPage ();
        homeController = new HomeController ( this);
        loginPage = new LoginPage ( );
        registerPage = new RegisterPage ( );
        adminController=new AdminController ();
        connection=DbConnection.getConnection();
    }

    @Override
    public void authMenu() {
        authPage.printAuthMenu ( );
        int choice = 0;
        try {
            choice = enterInt ( StringUtils.ENTER_CHOICE );
            if (choice==99){
                authPage.printThankYou();
                System.exit(0);
            }else{
                if (choice == 1) {
                    login ( );
                } else if (choice == 2) {
                    register ( );
                } else {
                    invalidChoice ( new AppException ( StringUtils.INVALID_CHOICE ) );

                }
            }


        } catch (AppException appException) {
            invalidChoice ( appException );
        }

    }
    @Override
    public void login() {
        String email, password;
        email = enterString ( StringUtils.ENTER_EMAIL );
        password = enterString ( StringUtils.ENTER_PASSWORD );
        User user = validateUser ( email, password );
        if (user != null) {
            if(user.getRole ()==Role.USER){
                setLoggedInUser(user);
                homeController.printHomeMenu ( );
            }else {
                adminController.printAdminMenu();
            }
        } else {
            loginPage.printInvalidCredentials ( );
            authMenu ( );
        }
    }



    @Override
    public void register() {
        String name, email, password, confirmPassword;
        name = enterString ( StringUtils.ENTER_NAME );
        email = enterString ( StringUtils.ENTER_EMAIL );
        password = enterString ( StringUtils.ENTER_PASSWORD );
        confirmPassword = enterString ( StringUtils.ENTER_PASSWORD_AGAIN );
        if (password.equals ( confirmPassword )) {
            try {
                PreparedStatement statement=connection.prepareStatement(QueryUtils.REGISTER_QUERY);
                statement.setString(1,email);
                statement.setString(2,password);
                statement.setString(3,name);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            registerPage.passwordMisMatch ( );
        }
        authMenu ( );

    }



    private User validateUser (String email, String password) {
        User user=null;
        try {
            PreparedStatement statement=connection.prepareStatement(QueryUtils.LOGIN_QUERY);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                user = new User ( );
                user.setId(rs.getInt("u_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("u_password"));
                user.setRoleId(rs.getInt("r_id"));
                if(user.getRoleId() == 1){
                    user.setRole(Role.ADMIN);
                }else {
                    user.setRole(Role.USER);
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private void invalidChoice (AppException e) {
        println ( e.getMessage ( ) );
        authMenu ( );
    }


}
