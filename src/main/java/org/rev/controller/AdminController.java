package org.rev.controller;

import org.rev.controller.implemantation.IAdminController;
import org.rev.database.DbConnection;
import org.rev.models.Category;
import org.rev.models.Product;
import org.rev.utils.AppException;
import org.rev.utils.QueryUtils;
import org.rev.utils.StringUtils;
import org.rev.view.AdminPage;
import org.rev.view.CategoryPage;
import org.rev.view.ProductsPage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.rev.utils.AppInput.enterInt;
import static org.rev.utils.AppInput.enterString;
import static org.rev.utils.LoadUtils.getCategories;
import static org.rev.utils.LoadUtils.getProducts;
import static org.rev.utils.Utils.println;

public class AdminController implements IAdminController {
    private final AdminPage adminPage;
    private final ProductsPage productsPage;
    private final CategoryPage categoryPage;
  private static Connection connection;

    public AdminController ( ) {
        adminPage = new AdminPage ( );
        productsPage = new ProductsPage( );
        categoryPage=new CategoryPage();
    }
    @Override
    public void printAdminMenu(){
        adminPage.printAdminPage ( );
        try {
            int choice = enterInt ( StringUtils.ENTER_CHOICE );
            if (choice == 1) {
                adminPage.printCategoryMenu();
                 choice = enterInt ( StringUtils.ENTER_CHOICE );
                 if (choice==00){
                     adminPage.printAdminPage ( );
                 }else if (choice == 1) {
                    addCategory();
                     adminPage.printCategoryMenu();
                 }else if (choice == 2) {
                     deleteCategory();
                     adminPage.printCategoryMenu();
                 }else if (choice == 3) {
                     editcategory();
                     adminPage.printCategoryMenu();
                 }else if (choice == 3) {
                     viewcategory();
                     adminPage.printCategoryMenu();
                 } else {
                     invalidChoice ( new AppException ( StringUtils.INVALID_CHOICE ) );
                     printAdminMenu ( );
                 }

            } else if (choice == 2) {
                adminPage.printProductMenu();
                choice = enterInt ( StringUtils.ENTER_CHOICE );
                if (choice==00){
                    adminPage.printAdminPage ( );
                }else if (choice == 1) {
                    addProduct();
                    adminPage.printProductMenu();
                }else if (choice == 2) {
                    deleteProduct();
                    adminPage.printProductMenu();
                }else if (choice == 3) {
                    editProduct();
                    adminPage.printProductMenu();
                }else if (choice == 3) {
                    viewProduct();
                    adminPage.printProductMenu();
                } else {
                    invalidChoice ( new AppException ( StringUtils.INVALID_CHOICE ) );
                    printAdminMenu ( );
                }

            } else if (choice == 3) {
                viewUserDetails();
                printAdminMenu ( );
            } else if (choice == 4) {
               viewOrderDetails();
                printAdminMenu ( );
            } else {
                invalidChoice ( new AppException ( StringUtils.INVALID_CHOICE ) );
                printAdminMenu ( );
            }

        } catch (AppException appException) {
            throw new RuntimeException ( appException );
        }
    }

    private void viewOrderDetails(){
        connection= DbConnection.getConnection();
        try {
            PreparedStatement statement=connection.prepareStatement(QueryUtils.SELECT_ALL_ORDERS);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                println ( "User:" + rs.getInt("u_id") + "\n" );
                println(rs.getString("p.product_name")+"x"+rs.getInt("od.count")+"= RS. "+rs.getInt("od.count")*rs.getInt("p.price"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void viewUserDetails(){
        connection= DbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QueryUtils.SELECT_ALL_USERS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                if(rs.getInt("r_id")==2){
                    println("User: "+rs.getInt("u_id"));
                    println("Name: "+rs.getString("u_name"));
                    println("email: "+rs.getString("email"));
                }
            }
        } catch (SQLException e) {

        }

    }

    private void viewProduct(){
        try {
            ArrayList<Product> products = getProducts ( );
            productsPage.printProductsMenu ( products );
            int choice = enterInt ( StringUtils.ENTER_CHOICE );
            if (choice == 00) {
                printAdminMenu ( );
            }
        } catch (AppException e) {
            throw new RuntimeException ( e );
        }
    }

    private void editProduct() throws AppException{
        connection=DbConnection.getConnection();
        int id;
        id = enterInt ( StringUtils.ID_TO_EDIT );
        String productName = enterString ( StringUtils.PRODUCT_EDIT );
        int price=enterInt ( StringUtils.EDIT_PRICE );
        int categoryId=enterInt ( StringUtils.EDIT_CATEGORY_ID );
        try {
            PreparedStatement statement=connection.prepareStatement(QueryUtils.EDIT_PRODUCT);
            statement.setString(1,productName);
            statement.setInt(2,price);
            statement.setInt(3,categoryId);
            statement.setInt(4,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteProduct(){
        connection=DbConnection.getConnection();
        try {
            int id = enterInt ( StringUtils.ID_TO_DELETE );
            PreparedStatement statement=connection.prepareStatement(QueryUtils.DELETE_PRODUCT);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (AppException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addProduct(){
        connection=DbConnection.getConnection();
        String productTitle, categoryId;
        int id,price;

        try {

            id = enterInt ( StringUtils.ENTER_PRODYCT_ID );
            productTitle = enterString ( StringUtils.ENTER_PRODUCT_TITLE );
            price = enterInt ( StringUtils.ENTER_PRICE );
            categoryId = enterString ( StringUtils.ENTER_CATEGORY_ID );
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement statement=connection.prepareStatement(QueryUtils.ADD_PRODUCT);
            statement.setInt(1,id);
            statement.setString(2,productTitle);
            statement.setInt(3,price);
            statement.setInt(4, Integer.parseInt(categoryId));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void viewcategory(){
        try {
            ArrayList<Category> categories = getCategories();
            categoryPage.printCategoryMenu(categories);
            int choice = enterInt ( StringUtils.ENTER_CHOICE );
            if (choice == 00) {
                printAdminMenu ( );
            }
        } catch (AppException e) {
            throw new RuntimeException ( e );
        }

    }

    private void editcategory() throws AppException{
        connection=DbConnection.getConnection();
        int categoryId=enterInt ( StringUtils.EDIT_CATEGORY_ID );
        String category=enterString(StringUtils.EDIT_CATEGORY_NAME);
        try {
            PreparedStatement statement=connection.prepareStatement(QueryUtils.EDIT_CATEGORY);
            statement.setString(1,category);
            statement.setInt(2,categoryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCategory(){
        connection=DbConnection.getConnection();
        try {
            int id = enterInt ( StringUtils.ID_TO_DELETE );
            PreparedStatement statement=connection.prepareStatement(QueryUtils.DELETE_CATEGORY);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (AppException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCategory(){
        connection=DbConnection.getConnection();
        String category;

        category=enterString(StringUtils.EDIT_CATEGORY_NAME);

        try {
            PreparedStatement statement=connection.prepareStatement(QueryUtils.ADD_CATEGORY);
            statement.setInt(1, Integer.parseInt(category));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void invalidChoice (AppException appException) {
        println ( appException.getMessage ( ) );
        printAdminMenu ( );
    }
}
