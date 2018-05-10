package com.codecool.shop.model;

import java.util.Currency;
import java.util.Objects;

public class Product extends BaseModel {

    private float defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private Supplier supplier;


    public Product(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public Currency getDefaultCurrency() { return defaultCurrency; }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    private void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    private void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    private void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);


    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Float.compare(product.defaultPrice, defaultPrice) == 0 &&
                Objects.equals(defaultCurrency, product.defaultCurrency) &&
                Objects.equals(productCategory, product.productCategory) &&
                Objects.equals(supplier, product.supplier) &&
                Objects.equals(name,product.name) &&
                Objects.equals(description,product.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(defaultPrice, defaultCurrency, productCategory, supplier);
    }
}
