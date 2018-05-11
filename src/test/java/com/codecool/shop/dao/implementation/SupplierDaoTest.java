package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.interfaces.SupplierDao;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.memory.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {
    private static SupplierDao supplierDao;
    private static boolean isDatabase = false;

    @BeforeEach
    void setup() {
        if (isDatabase) {
            supplierDao = SupplierDaoJdbc.getInstance();
            System.out.println("Database");
            isDatabase = !isDatabase;
        } else {
            supplierDao = SupplierDaoMem.getInstance();
            System.out.println("Memory");
            isDatabase = !isDatabase;
        }
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        Supplier lenovo = new Supplier("Lenovo", "Computers and Phones");
        Supplier samsung = new Supplier("Samsung", "Tablets, Phones and Computers");
        supplierDao.add(amazon);
        supplierDao.add(lenovo);
        supplierDao.add(samsung);
    }

    @RepeatedTest(2)
    void gettingInstance() {
        if (supplierDao.getClass().equals(SupplierDaoMem.class)) {
            assertEquals(SupplierDaoMem.class, SupplierDaoMem.getInstance().getClass());
        } else {
            assertEquals(SupplierDaoJdbc.class, SupplierDaoJdbc.getInstance().getClass());
        }
    }

    @RepeatedTest(2)
    void addingSupplier() {
        Supplier nokia = new Supplier("Nokia", "For the food old times");
        supplierDao.add(nokia);
        assertEquals(nokia, supplierDao.find("Nokia"));
    }

    @RepeatedTest(2)
    void gettingValidSupplierByString() {
        Supplier nokia = new Supplier("Nokia", "For the food old times");
        supplierDao.add(nokia);
        assertEquals(nokia, supplierDao.find("Nokia"));
    }

    @RepeatedTest(2)
    void gettingInvalidSupplierByString() {
        assertNull(supplierDao.find("null"));
    }

    @RepeatedTest(2)
    void gettingValidSupplierById() {
        assertEquals(Supplier.class, supplierDao.find(1).getClass());
    }

    @RepeatedTest(2)
    void gettingInvalisSupplierById() {
        assertNull(supplierDao.find(-100));
    }

    @RepeatedTest(2)
    void checkingIfFindByIdGivesBackDifferentResults() {
        assertNotEquals(supplierDao.find(2), supplierDao.find(3));
    }

    @RepeatedTest(2)
    void removingValidSupplier() {
        Supplier nokia = new Supplier("Nokia", "For the good old times");
        supplierDao.add(nokia);
        int id = supplierDao.find("Nokia").getId();
        supplierDao.remove(id);
        assertNull(supplierDao.find(id));
    }

    @RepeatedTest(2)
    void removingInvalidSupplier() {
        int sizeBeforeRemoval = supplierDao.getAll().size();
        supplierDao.remove(-10);
        int sizeAfterRemoval = supplierDao.getAll().size();
        assertEquals(sizeBeforeRemoval, sizeAfterRemoval);
    }

    @RepeatedTest(2)
    void addingExistingSuppliers() {
        Supplier makita = new Supplier("Makita", "Smash it");
        supplierDao.add(makita);
        int sizeAfterFirstAdd = supplierDao.getAll().size();
        supplierDao.add(makita);
        int sizeAfterSecondAdd = supplierDao.getAll().size();
        assertEquals(sizeAfterFirstAdd, sizeAfterSecondAdd);

    }

}
