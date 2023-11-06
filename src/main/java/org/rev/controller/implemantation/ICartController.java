package org.rev.controller.implemantation;

import java.io.IOException;

public interface ICartController {
    void addToCart(int productId) throws IOException;

    void printCart() throws IOException;
}
