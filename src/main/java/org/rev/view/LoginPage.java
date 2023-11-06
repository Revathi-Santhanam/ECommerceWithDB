package org.rev.view;

import static org.rev.utils.Utils.println;

public class LoginPage {
    public void printInvalidCredentials ( ) {
        try {
            println("=====================");
            println("Invalid Credentials!!!");
            println("=====================");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
