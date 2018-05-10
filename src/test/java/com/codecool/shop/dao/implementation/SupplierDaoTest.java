package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {
    private static SupplierDao supplierDao;

    @BeforeEach
    void setup() {
        supplierDao = SupplierDaoMem.getInstance();
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        Supplier lenovo = new Supplier("Lenovo", "Computers and Phones");
        Supplier samsung = new Supplier("Samsung", "Tablets, Phones and Computers");
        supplierDao.add(amazon);
        supplierDao.add(lenovo);
        supplierDao.add(samsung);
    }

    @Test
    void gettingInstance() {
        if (supplierDao.getClass().equals(SupplierDaoMem.class)) {
            assertEquals(SupplierDaoMem.class, SupplierDaoMem.getInstance().getClass());
        } else {
            //assertEquals(SupplierDaoJdbc.class,SupplierDaoJdbc.getInstance().getClass());
        }
    }

    @Test
    void addingSupplier() {
        Supplier nokia = new Supplier("Nokia", "For the food old times");
        supplierDao.add(nokia);
        assertEquals(nokia, supplierDao.find("Nokia"));
    }

    @Test
    void gettingValidSupplierByString() {
        Supplier nokia = new Supplier("Nokia", "For the food old times");
        supplierDao.add(nokia);
        assertEquals(nokia, supplierDao.find("Nokia"));
    }

    @Test
    void gettingInvalidSupplierByString() {
        assertNull(supplierDao.find("null"));
    }

    @Test
    void gettingValidSupplierById() {
        assertEquals(Supplier.class, supplierDao.find(1).getClass());
    }

    @Test
    void gettingInvalisSupplierById() {
        assertNull(supplierDao.find(-100));
    }

    @Test
    void checkingIfFindByIdGivesBackDifferentResults() {
        assertNotEquals(supplierDao.find(2), supplierDao.find(3));
    }

    @Test
    void removingValidSupplier() {
        Supplier nokia = new Supplier("Nokia", "For the good old times");
        supplierDao.add(nokia);
        int id = supplierDao.find("Nokia").getId();
        supplierDao.remove(id);
        assertNull(supplierDao.find(id));
    }

    @Test
    void removingInvalidSupplier() {
        int sizeBeforeRemoval = supplierDao.getAll().size();
        supplierDao.remove(-10);
        int sizeAfterRemoval = supplierDao.getAll().size();
        assertEquals(sizeBeforeRemoval,sizeAfterRemoval);
    }

    @Test
    void addingExistingSuppliers(){
        Supplier makita = new Supplier("Makita", "Smash it");
        supplierDao.add(makita);
        int sizeAfterFirstAdd = supplierDao.getAll().size();
        supplierDao.add(makita);
        int sizeAfterSecondAdd=supplierDao.getAll().size();
        assertEquals(sizeAfterFirstAdd,sizeAfterSecondAdd);

    }

}
