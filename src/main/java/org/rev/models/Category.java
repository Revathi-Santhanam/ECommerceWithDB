package org.rev.models;

import java.sql.Timestamp;

public class Category {

    private int id;
    private String categoryName;

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Category(){

    }

    public int getId ( ) {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getCategoryName ( ) {
        return categoryName;
    }

    public void setCategoryName (String categoryName) {
        this.categoryName = categoryName;
    }
}