package org.rev.view;

import org.rev.models.CartProduct;
import org.rev.utils.StringUtils;

import java.util.ArrayList;

import static org.rev.utils.Utils.println;

public class CartPage {
    public void printEmptyCart ( ) {
        println( StringUtils.EMPTY_CART);
    }

    public void printCart (ArrayList<CartProduct> cartProducts) {
        println(StringUtils.CART);
        double total = 0;
        for (CartProduct cartProduct : cartProducts) {
            total += cartProduct.getCount() * cartProduct.getProduct().getPrice();
            println(cartProduct.getProduct().getTitle() + " x " + cartProduct.getCount());
        }
        println(StringUtils.TOTAL_PRICE + total);

    }
    public void printCheckout ( ) {
        println("#---------------------#");
        println(StringUtils.PRINT_CHECKOUT);

    }
    public void printBack ( ) {
        println(StringUtils.BACK_OPTION);
    }
}
