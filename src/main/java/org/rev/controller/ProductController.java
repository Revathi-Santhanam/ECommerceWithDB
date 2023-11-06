package org.rev.controller;

import org.rev.controller.implemantation.IProductController;
import org.rev.models.Product;
import org.rev.utils.AppException;
import org.rev.utils.StringUtils;
import org.rev.view.ProductsPage;

import java.io.IOException;
import java.util.ArrayList;

import static org.rev.utils.AppInput.enterInt;
import static org.rev.utils.LoadUtils.getProducts;
import static org.rev.utils.Utils.println;

public class ProductController implements IProductController {
    private int categoryId = 0;
    private final ProductsPage productsPage;
    private final CartController cartController;
    private final HomeController homeController;

    public ProductController ( HomeController homeController) {
        productsPage=new ProductsPage ();
        this.homeController=homeController;
        cartController=new CartController (homeController);

    }

    @Override
    public void showProducts(int categoryId) throws IOException{
        this.categoryId = categoryId;
        ArrayList<Product> products = getProducts();
        if(categoryId!=0){
            ArrayList<Product> categoryProducts = new ArrayList<> (  );
            for (Product product:products) {
                if (product.getCategoryId()==categoryId){
                    categoryProducts.add ( product );
                }
            }
            products=categoryProducts;
        }
        productsPage.printProductsMenu(products);
        try {
            int choice = enterInt( StringUtils.ENTER_CHOICE );
            int validProductId=0;
            if (choice==00){
                homeController.printHomeMenu ();
            }else {
                for (Product product:products){
                    if(product.getId ()==choice){
                        validProductId=product.getId ();
                        break;
                    }
                }
                if (validProductId != 0) {
                    cartController.addToCart(validProductId);
                    productsPage.printSuccess();
                    showProducts(categoryId);
                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }

            }
        } catch (AppException appException) {
            try {
                invalidChoice(appException);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void invalidChoice (AppException appException) throws AppException, IOException {
        println(appException.getMessage ());
        showProducts(categoryId);
    }

}
