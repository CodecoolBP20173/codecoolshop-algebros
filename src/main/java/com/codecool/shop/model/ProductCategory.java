package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductCategory extends BaseModel {
    private String department;
    private transient List<Product> products;

    public ProductCategory(String name, String department, String description) {
        super(name);
        this.department = department;
        this.products = new ArrayList<>();
        this.description = description;
    }

    void addProduct(Product product) {
        this.products.add(product);
    }

    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)){
            return false;
        }
        ProductCategory category = (ProductCategory) o;
        return Objects.equals(name, category.name) && Objects.equals(description, category.description) && Objects.equals(department, category.department);
    }


}