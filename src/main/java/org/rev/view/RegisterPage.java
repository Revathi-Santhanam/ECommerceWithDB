package org.rev.view;


import org.rev.utils.StringUtils;

import static org.rev.utils.Utils.println;

public class RegisterPage {
    public void printRegisterSuccess ( ) {
        try {

            println("#---------------------#");
            println( StringUtils.REGISTRATION_SUCCESSFUL );
            println("#---------------------#");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void passwordMisMatch ( ) {
        try {
            println("#---------------------#");
            println( StringUtils.PASSWORD_MISMATCH);
            println("#---------------------#");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
