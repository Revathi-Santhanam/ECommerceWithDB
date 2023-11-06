package org.rev.view;


import org.rev.utils.StringUtils;

import static org.rev.utils.Utils.println;

public class AdminPage {
    public void printAdminPage ( ) {
        println( StringUtils.WELCOME_ADMIN_MESSAGE);
        println ( StringUtils.ADMIN_MENU );
    }


    public void productDeletedMessage ( ) {
        println ( StringUtils.PRODUCT_DELETED );
    }

    public void printCategoryMenu(){
        println(StringUtils.CATEGORY_ADMIN_MENU);
    }

    public void printProductMenu(){
        println(StringUtils.PRODUCT_ADMIN_MENU);
    }
}
