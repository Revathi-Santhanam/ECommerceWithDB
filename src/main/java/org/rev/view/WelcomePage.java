package org.rev.view;


import org.rev.utils.StringUtils;

import static org.rev.utils.Utils.println;

public class WelcomePage {
    public void welcome ( ) {
        try {
            println( StringUtils.WELCOME_MESSAGE );
            Thread.sleep ( 1000 );
        } catch (InterruptedException e) {
            throw new RuntimeException ( e );
        }
    }
}
