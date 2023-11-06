package org.rev.controller;

import org.rev.controller.implemantation.ICategoryController;
import org.rev.models.Category;
import org.rev.utils.AppException;
import org.rev.utils.StringUtils;
import org.rev.view.CategoryPage;

import java.io.IOException;
import java.util.ArrayList;

import static org.rev.utils.AppInput.enterInt;
import static org.rev.utils.LoadUtils.getCategories;
import static org.rev.utils.Utils.println;

public class CategoryController implements ICategoryController {
    private final CategoryPage categoryPage;
    private final ProductController productController;
    private final HomeController homeController;

    public CategoryController(HomeController homeController){
        categoryPage=new CategoryPage ();
        productController= new ProductController (homeController);
        this.homeController=homeController;
    }


    @Override
    public void printCategoryMenu() throws IOException{
        ArrayList<Category> categories = getCategories();
        categoryPage.printCategoryMenu(categories);
        try {
            int choice = enterInt( StringUtils.ENTER_CHOICE);

            if (choice == 00) {
                homeController.printHomeMenu ();
            } else {
                int validCategoryId = 0;

                for (Category category : categories) {
                    if (category.getId() == choice) {
                        validCategoryId = category.getId();
                        break;
                    }
                }
                if (validCategoryId != 0) {
                    productController.showProducts(validCategoryId);
                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        }

    }




    private void invalidChoice (AppException appException) throws IOException {
        println(appException.getMessage ());
        printCategoryMenu ();
    }
}
