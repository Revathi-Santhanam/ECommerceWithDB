package org.rev.utils;

public class QueryUtils {
    public static final String LOGIN_QUERY = "SELECT u_id,email,u_password,r_id FROM user_table WHERE email=? AND u_password=?;";
    public static final String REGISTER_QUERY = "INSERT INTO user_table (email,u_password,u_name) VALUES (?,?,?);";
    public static final String GET_CATEGORY_QUERY = "SELECT * FROM category_table WHERE d_flag = false;";
    public static final String GET_PRODUCT_QUERY = "SELECT * FROM product_table WHERE d_flag = false;";
    public static final String INSERT_CART = "INSERT INTO cart_table (u_id,p_id,count) VALUES (?,?,?);";
    public static final String UPDATE_CART_COUNT = "UPDATE cart_table SET count = count+1 WHERE u_id=? AND p_id=?;";
    public static final String SELECT_USER_QUERY = "SELECT * FROM user_table WHERE d_flag = false;";
    public static final String PRINT_CART="SELECT p.p_id, p.product_name,p.price,c.c_id,c.p_id,c.u_id,c.count FROM product_table p, cart_table c WHERE p.p_id=c.p_id AND c.u_id=?;";
    public static final String SELECT_ALL_ORDERS = "SELECT o.u_id,p.product_name,p.price,od.count FROM order_table o,order_details_table od,product_table p WHERE p.p_id=od.p_id AND od.order_details_id=o.order_details_id ;";
    public static final String SELECT_ALL_USERS = "SELECT * FROM user_table;";
    public static final String ADD_PRODUCT = "INSERT INTO product_table (p_id, product_name,price,cat_id) VALUES (?,?,?,?);";
    public static final String ADD_CATEGORY = "INSERT INTO category_table (cat_name) VALUES (?);";
    public static final String DELETE_PRODUCT = "UPDATE product_table SET d_flag=1 WHERE p_id=?";
    public static final String DELETE_CATEGORY = "UPDATE category_table SET d_flag=1 WHERE cat_id=?";
    public static final String EDIT_PRODUCT = "UPDATE product_table SET product_name=?,price=?,cat_id=? WHERE p_id=?;";
    public static final String EDIT_CATEGORY = "UPDATE category_table SET cat_name=? WHERE cat_id=?";
    public static final String SELECT_CART = "SELECT * FROM cart_table WHERE u_id=? AND p_id=?;";
}

