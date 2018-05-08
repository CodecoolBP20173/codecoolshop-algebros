package com.codecool.shop.dao.implementation;


import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoMemTest {
    private static ProductCategoryDaoMem productCategoryDaoMem;

    @BeforeEach
    void setup() {
        productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
        ProductCategory smartPhone = new ProductCategory("Smart Phone", "Hardware", "A smart phone is a small mobile computer, with the additional feature of making phone calls.");
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        productCategoryDaoMem.add(smartPhone);
        productCategoryDaoMem.add(notebook);
    }

    @Test
    void gettingInstanceTest() {
        assertEquals(ProductCategoryDaoMem.class, ProductCategoryDaoMem.getInstance().getClass());
    }

    @Test
    void addingCategoryTest() {
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDaoMem.add(tablet);
        assertEquals(tablet, productCategoryDaoMem.find("Tablet"));
    }

    @Test
    void gettingValidCategoryByStringTest() {
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        assertEquals(notebook, productCategoryDaoMem.find("Notebook"));
    }

    @Test
    void gettingInvalidCategoryByStringTest() {
        assertNull(productCategoryDaoMem.find("null"));
    }

    @Test
    void gettingValidCategoryByIdTest() {
        assertEquals(ProductCategory.class, productCategoryDaoMem.find(1).getClass());
    }

    @Test
    void gettingInvalidCategoryByIdTest() {
        assertNull(productCategoryDaoMem.find(-10));
    }

    @Test
    void removingCategoryTest() {
        productCategoryDaoMem.remove(1);
        assertNull(productCategoryDaoMem.find(1));

    }

    @Test
    void removingInvalidCategory(){
        int sizeBeforeRemoving = productCategoryDaoMem.getAll().size();
        productCategoryDaoMem.remove(1234567);
        int sizeAfterRemoving = productCategoryDaoMem.getAll().size();
        assertEquals(sizeBeforeRemoving,sizeAfterRemoving);
    }

    @Test
    void addingExistingCategoryTest() {
        ProductCategory testing = new ProductCategory("Testing", "Testing", "Testing Testing");
        productCategoryDaoMem.add(testing);
        int listSizeAfterFirstAdding = productCategoryDaoMem.getAll().size();
        productCategoryDaoMem.add(testing);
        int listSizeAfterSecondAdding = productCategoryDaoMem.getAll().size();
        assertEquals(listSizeAfterFirstAdding, listSizeAfterSecondAdding);
    }
}
