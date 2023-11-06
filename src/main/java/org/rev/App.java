package org.rev;

import org.rev.controller.AppController;
import org.rev.database.DbConnection;
import org.rev.utils.LoadUtils;

import java.sql.Connection;

public class App {
    public static void main(String[] args){
        AppController appController = new AppController ();
        LoadUtils.load();
        appController.init ();

    }
}