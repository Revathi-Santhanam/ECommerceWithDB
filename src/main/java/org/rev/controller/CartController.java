package org.rev.controller;

import org.rev.controller.implemantation.ICartController;
import org.rev.database.DbConnection;
import org.rev.models.Cart;
import org.rev.models.CartProduct;
import org.rev.models.Product;
import org.rev.models.User;
import org.rev.utils.AppException;
import org.rev.utils.QueryUtils;
import org.rev.utils.StringUtils;
import org.rev.view.CartPage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.rev.utils.AppInput.enterInt;
import static org.rev.utils.LoadUtils.getProducts;
import static org.rev.utils.UserUtils.*;
import static org.rev.utils.UserUtils.loggedInUser;
import static org.rev.utils.Utils.println;

public class CartController implements ICartController {

    private final HomeController homeController;
    private final OrderController orderController;
    private final CartPage cartPage;
    private Connection connection;

    public CartController (HomeController homeController) {
        this.homeController = homeController;
        orderController = new OrderController ( homeController );
        cartPage = new CartPage ( );
    }


    @Override
    public void addToCart(int productId){
        User loggedInUser = getLoggedInUser ( );
        ArrayList<Product> products = getProducts ( );

        Product userProduct = null;
        for (Product product : products) {
            if (product.getId ( ) == productId) {
                userProduct = product;
                break;
            }
        }

        if (loggedInUser.getUserCart ( ) != null) {
            Cart cart = loggedInUser.getUserCart ( );

            boolean isFound = false;

            for (CartProduct cartProduct : cart.getCartProducts ( )) {
                if (cartProduct.getProduct ( ).getId ( ) == productId) {
                    cartProduct.setCount ( cartProduct.getCount ( ) + 1 );
                    isFound = true;
                }

            }

            if (!isFound) {
                cart.getCartProducts ( ).add ( new CartProduct ( userProduct, 1 ) );
            }

            loggedInUser.setUserCart ( cart );
        } else {
            Cart cart = new Cart ( );
            ArrayList<CartProduct> cartProducts = new ArrayList<> ( );
            cartProducts.add ( new CartProduct ( userProduct, 1 ) );
            cart.setCartProducts ( cartProducts );
            loggedInUser.setUserCart ( cart );
        }
        connection= DbConnection.getConnection();
        setLoggedInUser ( loggedInUser );
        int uid = loggedInUser.getId();
        int pid = 0;
        String pname = null;
        int pprice = 0;
        int count = 0;
        for (CartProduct cartProduct: loggedInUser.getUserCart().getCartProducts()) {
            pid = cartProduct.getProduct().getId();
            pname = cartProduct.getProduct().getTitle();
            count = cartProduct.getCount();
            pprice = (int) cartProduct.getProduct().getPrice();
        }


        try {
            PreparedStatement statement = connection.prepareStatement(QueryUtils.INSERT_CART);
            statement.setInt(1, uid);
            statement.setInt(2, pid);
            statement.setInt(3, count);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    private void checkout ( ) {

        orderController.checkout ( );
    }

    private void invalidChoice (AppException appException) throws IOException {
        println ( appException.getMessage ( ) );
        printCart ( );
    }

    @Override
    public void printCart() throws IOException {
        connection=DbConnection.getConnection();

        User loggedInUser = getLoggedInUser ( );
        if (loggedInUser.getUserCart ( ) == null) {
            cartPage.printEmptyCart ( );
            homeController.printHomeMenu ( );
        } else {
            //

            ArrayList<CartProduct> cartProducts = new ArrayList<>();
            try {
                PreparedStatement statement=connection.prepareStatement(QueryUtils.PRINT_CART);
                statement.setInt(1,loggedInUser.getId());
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    CartProduct cartProduct=new CartProduct();
                    Product product=new Product();
                    product.setId(rs.getInt("c.p_id"));
                    product.setTitle(rs.getString("p.product_name"));
                    product.setPrice(rs.getInt("p.price"));
                    cartProduct.setCount(rs.getInt("c.count"));
                    cartProduct.setProduct(product);
                    cartProducts.add(cartProduct);

                }

            } catch (SQLException | AppException e) {
                throw new RuntimeException(e);
            }

            cartPage.printCart ( cartProducts );
            cartPage.printCheckout ( );
            cartPage.printBack ( );


            try {
                int choice = enterInt ( StringUtils.ENTER_CHOICE );
                if (choice == 88) {
                    checkout ( );
                } else if (choice == 00) {
                    homeController.printHomeMenu ( );
                } else {
                    invalidChoice ( new AppException ( StringUtils.INVALID_CHOICE ) );
                }
            } catch (AppException appException) {
                invalidChoice ( appException );
            }

        }
    }

    private void insertInToCartTable(){

    }


}
