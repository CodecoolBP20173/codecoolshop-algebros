package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private static ProductDao productDao;
    private boolean isDatabase = false;

    @BeforeEach
    void setup() {
        if (isDatabase) {
            productDao = ProductDaoJdbc.getInstance();
            isDatabase = !isDatabase;
        } else {
            productDao = ProductDaoMem.getInstance();
            isDatabase = !isDatabase;
        }
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        Supplier lenovo = new Supplier("Lenovo", "Computers and Phones");
        Supplier samsung = new Supplier("Samsung", "Tablets, Phones and Computers");
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory smartPhone = new ProductCategory("Smart Phone", "Hardware", "A smart phone is a small mobile computer, with the additional feature of making phone calls.");
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");

        Product amazonFire = new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon);
        Product lenovoIdeaPad = new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo);
        Product amazonFireHd8 = new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon);
        Product lenovoX012 = new Product("Lenovo XO12", 500, "USD", "Best phone ever.", smartPhone, lenovo);
        Product SamsungS9 = new Product("Samsung S9+", 879, "USD", "Latest Sasmung smartphone with Octa Core processor.", smartPhone, samsung);
        Product SamsungNotebook9 = new Product("Samsung Notebook 9 15\"", 1500, "USD", "Lightweight notebook with the capabilities of a desktop PC ", notebook, samsung);

        productDao.add(amazonFire);
        productDao.add(lenovoIdeaPad);
        productDao.add(amazonFireHd8);
        productDao.add(lenovoX012);
        productDao.add(SamsungS9);
        productDao.add(SamsungNotebook9);

    }

    @RepeatedTest(2)
    void gettingInstanceTest() {
        if (productDao.getClass().equals(ProductDaoMem.getInstance().getClass())) {
            assertEquals(ProductDaoMem.class, ProductDaoMem.getInstance().getClass());
        } else {
            assertEquals(ProductDaoJdbc.class,ProductDaoJdbc.getInstance().getClass());
        }

    }

    @RepeatedTest(2)
    void checkIfGetAllDoesNotReturnReferenceTest() {
        List products = productDao.getAll();
        try {
            products.remove(1);
        } catch (UnsupportedOperationException e) {
            assertEquals(UnsupportedOperationException.class, e.getClass());
        }
    }

    @RepeatedTest(2)
    void gettingAllProductsTest() {
        int expectedListSize = productDao.getAll().size();
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        Product testProduct = new Product("Amazon Notebook Test", 12f, "USD", "none", notebook, amazon);
        productDao.add(testProduct);
        assertNotEquals(expectedListSize, productDao.getAll().size());
    }

    @RepeatedTest(2)
    void addValidProductTest() {
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
        Product testProduct = new Product("Amazon Notebook Test Number Two", 12f, "USD", "none", notebook, amazon);
        productDao.add(testProduct);
        List products = productDao.getAll();
        assertTrue(products.contains(testProduct));
    }

    @RepeatedTest(2)
    void addInvalidProductTest() {
        assertThrows(NullPointerException.class, () -> productDao.add(null));
    }

    @RepeatedTest(2)
    void findByValidIdTest() {
        assertEquals(Product.class, productDao.find(1).getClass());
    }

    @RepeatedTest(2)
    void findByInvalidIdTest() {
        assertThrows(NullPointerException.class, () -> productDao.add(null));
    }

    @RepeatedTest(2)
    void removeValidProductTest() {
        int expectedLength = productDao.getAll().size();
        productDao.remove(1);
        assertNotEquals(expectedLength, productDao.getAll().size());
    }

    @RepeatedTest(2)
    void removeInvalidProductTest() {
        int expectedLength = productDao.getAll().size();
        productDao.remove(-100);
        assertEquals(expectedLength, productDao.getAll().size());
    }

    @RepeatedTest(2)
    void getProductsByValidSupplier() {
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        List products = productDao.getBy(amazon);
        Product product = (Product) products.get(1);
        assertEquals(amazon, product.getSupplier());
    }

    @RepeatedTest(2)
    void getProductByValidCategory() {
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        List products = productDao.getBy(tablet);
        boolean isTablet = true;
        for (Object object : products) {
            Product product = (Product) object;
            if (!isTablet) {
                break;
            } else {
                if (!product.getProductCategory().equals(tablet)) {
                    isTablet = false;
                }
            }
        }
        assertTrue(isTablet);
    }
}
