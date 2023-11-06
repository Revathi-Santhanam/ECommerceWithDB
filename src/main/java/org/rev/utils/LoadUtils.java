package org.rev.utils;

import org.rev.database.DbConnection;
import org.rev.models.Category;
import org.rev.models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadUtils {

    private static ArrayList<Category> categories = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    private static Connection connection;



    public static void load(){
        connection = DbConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QueryUtils.GET_CATEGORY_QUERY);
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("cat_id"));
                category.setCategoryName(rs.getString("cat_name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement statement1 = connection.createStatement();
            ResultSet rs1 = statement1.executeQuery(QueryUtils.GET_PRODUCT_QUERY);
            while (rs1.next()) {
                Product product=new Product();
                product.setId(rs1.getInt("p_id"));
                product.setTitle(rs1.getString("product_name"));
                product.setPrice(rs1.getInt("price"));
                product.setCategoryId(rs1.getInt("cat_id"));
                products.add(product);
            }
        } catch (SQLException | AppException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<Category> getCategories(){
        return categories;
    }

    public static ArrayList<Product> getProducts(){
        return products;
    }

}
