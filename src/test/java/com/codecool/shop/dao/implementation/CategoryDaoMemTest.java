package com.codecool.shop.dao.implementation;


import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoMemTest {
    private static ProductCategoryDaoMem productCategoryDaoMem = ProductCategoryDaoMem.getInstance();

    @BeforeAll
    static void setup() {
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
    void addingAndRetrievingCategoryTest() {
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDaoMem.add(tablet);
        assertEquals(tablet.getName(), productCategoryDaoMem.find("Tablet").getName());
    }

    @Test
    void gettingValidCategoryByStringTest() {
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        assertEquals(notebook.getName(), productCategoryDaoMem.find("Notebook").getName());
    }

    @Test
    void gettingInvalidCategoryByStringTest() {
        assertNull(productCategoryDaoMem.find("null"));
    }
}
