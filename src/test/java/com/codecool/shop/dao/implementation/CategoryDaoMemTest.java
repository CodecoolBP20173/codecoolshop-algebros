package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoMemTest {
    private static ProductCategoryDao productCategoryDao;

    @BeforeEach
    void setup() {
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        ProductCategory smartPhone = new ProductCategory("Smart Phone", "Hardware", "A smart phone is a small mobile computer, with the additional feature of making phone calls.");
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        productCategoryDao.add(smartPhone);
        productCategoryDao.add(notebook);
    }

    @Test
    void gettingInstanceTest() {
        if (productCategoryDao.getClass().equals(ProductCategoryDaoMem.class)) {
            assertEquals(productCategoryDao.getClass(),ProductCategoryDaoMem.getInstance().getClass());
        } else {
            // assertEquals(productCategoryDao.getClass(),ProductCategoryDaoJdbc.getInstance().getClass());
        }
    }

    @Test
    void addingCategoryTest() {
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDao.add(tablet);
        assertEquals(tablet, productCategoryDao.find("Tablet"));
    }

    @Test
    void gettingValidCategoryByStringTest() {
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        assertEquals(notebook, productCategoryDao.find("Notebook"));
    }

    @Test
    void gettingInvalidCategoryByStringTest() {
        assertNull(productCategoryDao.find("null"));
    }

    @Test
    void gettingValidCategoryByIdTest() {
        assertEquals(ProductCategory.class, productCategoryDao.find(1).getClass());
    }

    @Test
    void checkingIfFindByIdGivesBackDifferentResults() {
        assertNotEquals(productCategoryDao.find(1),productCategoryDao.find(2));
    }

    @Test
    void gettingInvalidCategoryByIdTest() {
        assertNull(productCategoryDao.find(-10));
    }

    @Test
    void removingCategoryTest() {
        productCategoryDao.remove(1);
        assertNull(productCategoryDao.find(1));

    }

    @Test
    void removingInvalidCategory() {
        int sizeBeforeRemoving = productCategoryDao.getAll().size();
        productCategoryDao.remove(1234567);
        int sizeAfterRemoving = productCategoryDao.getAll().size();
        assertEquals(sizeBeforeRemoving, sizeAfterRemoving);
    }

    @Test
    void addingExistingCategoryTest() {
        ProductCategory testing = new ProductCategory("Testing", "Testing", "Testing Testing");
        productCategoryDao.add(testing);
        int listSizeAfterFirstAdding = productCategoryDao.getAll().size();
        productCategoryDao.add(testing);
        int listSizeAfterSecondAdding = productCategoryDao.getAll().size();
        assertEquals(listSizeAfterFirstAdding, listSizeAfterSecondAdding);
    }
}
