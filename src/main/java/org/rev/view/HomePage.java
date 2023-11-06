package org.rev.view;

import org.rev.utils.StringUtils;

import static org.rev.utils.Utils.println;

public class HomePage {
    public void printHomeMenu ( ) {
        println( StringUtils.WELCOME_MESSAGE );
        println ( StringUtils.HOME_MENU );
    }
}
