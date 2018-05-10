package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.interfaces.ProductCategoryDao;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.memory.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {
    private static ProductCategoryDao productCategoryDao;
    private static boolean isDatabase = false;

    @BeforeEach
    void setup() {
        if (isDatabase) {
            productCategoryDao = ProductCategoryDaoJdbc.getInstance();
            isDatabase = !isDatabase;
        } else {
            productCategoryDao = ProductCategoryDaoMem.getInstance();
            isDatabase = !isDatabase;
        }
        ProductCategory smartPhone = new ProductCategory("Smart Phone", "Hardware", "A smart phone is a small mobile computer, with the additional feature of making phone calls.");
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        productCategoryDao.add(smartPhone);
        productCategoryDao.add(notebook);
    }

    @RepeatedTest(2)
    void gettingInstanceTest() {
        if (productCategoryDao.getClass().equals(ProductCategoryDaoMem.class)) {
            assertEquals(productCategoryDao.getClass(), ProductCategoryDaoMem.getInstance().getClass());
        } else {
            assertEquals(productCategoryDao.getClass(), ProductCategoryDaoJdbc.getInstance().getClass());
        }
    }

    @RepeatedTest(2)
    void addingCategoryTest() {
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDao.add(tablet);
        assertEquals(tablet, productCategoryDao.find("Tablet"));
    }

    @RepeatedTest(2)
    void gettingValidCategoryByStringTest() {
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        assertEquals(notebook, productCategoryDao.find("Notebook"));
    }

    @RepeatedTest(2)
    void gettingInvalidCategoryByStringTest() {
        assertNull(productCategoryDao.find("null"));
    }

    @RepeatedTest(2)
    void gettingValidCategoryByIdTest() {
        assertEquals(ProductCategory.class, productCategoryDao.find(1).getClass());
    }

    @RepeatedTest(2)
    void checkingIfFindByIdGivesBackDifferentResults() {
        assertNotEquals(productCategoryDao.find(1), productCategoryDao.find(2));
    }

    @RepeatedTest(2)
    void gettingInvalidCategoryByIdTest() {
        assertNull(productCategoryDao.find(-10));
    }

    @RepeatedTest(2)
    void removingCategoryTest() {
        productCategoryDao.remove(1);
        assertNull(productCategoryDao.find(1));

    }

    @RepeatedTest(2)
    void removingInvalidCategory() {
        int sizeBeforeRemoving = productCategoryDao.getAll().size();
        productCategoryDao.remove(1234567);
        int sizeAfterRemoving = productCategoryDao.getAll().size();
        assertEquals(sizeBeforeRemoving, sizeAfterRemoving);
    }

    @RepeatedTest(2)
    void addingExistingCategoryTest() {
        ProductCategory testing = new ProductCategory("Testing", "Testing", "Testing Testing");
        productCategoryDao.add(testing);
        int listSizeAfterFirstAdding = productCategoryDao.getAll().size();
        productCategoryDao.add(testing);
        int listSizeAfterSecondAdding = productCategoryDao.getAll().size();
        assertEquals(listSizeAfterFirstAdding, listSizeAfterSecondAdding);
    }
}
