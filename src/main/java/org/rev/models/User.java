package org.rev.models;

import java.sql.Timestamp;
import java.util.ArrayList;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private Role role;
    private int roleId;
    private Cart userCart;
    private ArrayList<Order> userOrders;

    public int getRoleId(){
        return roleId;
    }

    public void setRoleId(int roleId){
        this.roleId = roleId;
    }



    public Cart getUserCart ( ) {
        return userCart;
    }

    public void setUserCart (Cart userCart) {
        this.userCart = userCart;
    }

    public ArrayList<Order> getUserOrders ( ) {
        return userOrders;
    }

    public void setUserOrders (ArrayList<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public int getId ( ) {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getEmail ( ) {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword ( ) {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getName ( ) {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Role getRole ( ) {
        return role;
    }

    public void setRole (Role role) {
        this.role = role;
    }
}
