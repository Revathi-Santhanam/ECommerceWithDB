package org.rev.view;

import org.rev.utils.StringUtils;

import static org.rev.utils.Utils.println;

public class AuthPage {
    public void printAuthMenu() {
        println( StringUtils.AUTH_MENU);
    }

    public void printThankYou() {
        println(StringUtils.THANK_YOU);
    }
}
