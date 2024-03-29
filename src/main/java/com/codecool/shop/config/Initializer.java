package com.codecool.shop.config;

import com.codecool.shop.dao.interfaces.ProductCategoryDao;
import com.codecool.shop.dao.interfaces.ProductDao;
import com.codecool.shop.dao.interfaces.SupplierDao;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.memory.ProductDaoMem;
import com.codecool.shop.dao.implementation.memory.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    private boolean isDataAvailable = false;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStoreJdbc = SupplierDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDaoJdbc productCategoryDataStoreJdbc = ProductCategoryDaoJdbc.getInstance();
        ProductDao productDataStoreJdbc = ProductDaoJdbc.getInstance();

        if (productCategoryDataStoreJdbc.getAll().size() > 0) {
            this.isDataAvailable = true;
        }

        if (!this.isDataAvailable) {

            //setting up a new supplier
            Supplier amazon = new Supplier("Amazon", "Digital content and services");
            supplierDataStoreJdbc.add(amazon);
            supplierDataStore.add(amazon);
            Supplier lenovo = new Supplier("Lenovo", "Computers and Phones");
            supplierDataStore.add(lenovo);
            supplierDataStoreJdbc.add(lenovo);
            Supplier samsung = new Supplier("Samsung", "Tablets, Phones and Computers");
            supplierDataStore.add(samsung);
            supplierDataStoreJdbc.add(samsung);


            //setting up a new product category
            ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
            ProductCategory smartPhone = new ProductCategory("Smart Phone", "Hardware", "A smart phone is a small mobile computer, with the additional feature of making phone calls.");
            ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "Portable comupters");
            productCategoryDataStore.add(tablet);
            productCategoryDataStore.add(smartPhone);
            productCategoryDataStore.add(notebook);
            productCategoryDataStoreJdbc.add(tablet);
            productCategoryDataStoreJdbc.add(smartPhone);
            productCategoryDataStoreJdbc.add(notebook);


            //setting up products and printing it
            productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
            productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
            productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
            productDataStore.add((new Product("Lenovo XO12", 500, "USD", "Best phone ever.", smartPhone, lenovo)));
            productDataStore.add(new Product("Samsung S9+", 879, "USD", "Latest Sasmung smartphone with Octa Core processor.", smartPhone, samsung));
            productDataStore.add(new Product("Samsung Notebook 9 15\"", 1500, "USD", "Lightweight notebook with the capabilities of a desktop PC ", notebook, samsung));

            productDataStoreJdbc.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
            productDataStoreJdbc.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
            productDataStoreJdbc.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
            productDataStoreJdbc.add((new Product("Lenovo XO12", 500, "USD", "Best phone ever.", smartPhone, lenovo)));
            productDataStoreJdbc.add(new Product("Samsung S9+", 879, "USD", "Latest Sasmung smartphone with Octa Core processor.", smartPhone, samsung));
            productDataStoreJdbc.add(new Product("Samsung Notebook 9 15\"", 1500, "USD", "Lightweight notebook with the capabilities of a desktop PC ", notebook, samsung));
        }
    }
}
