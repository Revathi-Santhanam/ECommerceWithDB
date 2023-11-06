package org.rev.controller;

import org.rev.controller.implemantation.IAppController;
import org.rev.view.WelcomePage;

public class AppController implements IAppController {

    private final WelcomePage welcomePage;
    private final AuthController authController;
    public AppController(){
        welcomePage=new WelcomePage();
        authController=new AuthController ();
    }
    @Override
    public void init ( ) {
        welcomePage.welcome();
        authController.authMenu ();
    }
}
