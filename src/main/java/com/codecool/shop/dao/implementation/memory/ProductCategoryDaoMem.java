package com.codecool.shop.dao.implementation.memory;


import com.codecool.shop.dao.interfaces.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
    }

    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        if (this.find(category.getName()) == null) {
            category.setId(data.size() + 1);
            data.add(category);
        }

    }

    @Override
    public ProductCategory find(String category) {
        return data.stream().filter(t -> t.getName().equals(category)).findFirst().orElse(null);
    }

    @Override
    public ProductCategory find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return Collections.unmodifiableList(data);
    }
}
