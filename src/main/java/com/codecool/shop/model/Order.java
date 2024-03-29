package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.memory.ProductDaoMem;
import com.codecool.shop.processes.CheckoutProcess;
import com.codecool.shop.processes.PaymentProcess;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements Orderable {
    private static AtomicInteger uniqueId = new AtomicInteger();
    private int id;
    private String status;
    private CheckoutProcess checkoutProcess;
    private PaymentProcess paymentProcess;

    private List<Product> getItemList() {
        return itemList;
    }

    private List<Product> itemList = new ArrayList<>();

    private HashMap<Integer, Integer> getOrderQuantity() {
        return orderQuantity;
    }

    private HashMap<Integer, Integer> orderQuantity = new HashMap<>();

    public Order() {
        this.id = uniqueId.getAndIncrement();
        this.status = "new";
    }

    public int getId() {
        return id;
    }

    public CheckoutProcess getCheckoutProcess() {
        return checkoutProcess;
    }

    private String getStatus() {
        return status;
    }

    public void addProduct(int id) {
        if (!(itemList.contains(ProductDaoMem.getInstance().find(id)))) {
            itemList.add(ProductDaoMem.getInstance().find(id));
        }
        orderQuantity.merge(id, 1, Integer::sum);
    }

    public void removeProduct(int id) {
        itemList.remove(ProductDaoMem.getInstance().find(id));
        orderQuantity.remove(id);
    }

    public void decrementQuantityOfProduct(int id) {
        if (orderQuantity.get(id) == 1) {
            itemList.remove(ProductDaoMem.getInstance().find(id));
            orderQuantity.remove(id);
        } else {
            orderQuantity.put(id, orderQuantity.get(id) - 1);
        }
    }


    public boolean checkout(CheckoutProcess checkoutProcess) {
        if (this.getStatus().equals("new")) {
            this.status = "checked";
            this.checkoutProcess = checkoutProcess;
        }
        return false;
    }

    public boolean pay(PaymentProcess paymentProcess) {
        if (this.getStatus().equals("checked")) {
            this.status = "payed";
            this.paymentProcess = paymentProcess;

        }
        return false;
    }

    public JSONArray getCartItems() {
        JSONArray productList = new JSONArray();
        JSONObject jsonObject;
        for (Product product1 : this.getItemList()) {
            jsonObject = new JSONObject();
            jsonObject.put("id", product1.getId());
            jsonObject.put("name", product1.getName());
            jsonObject.put("defaultPrice", product1.getDefaultPrice());
            jsonObject.put("quantity", this.getOrderQuantity().get(product1.getId()));
            jsonObject.put("price", product1.getPrice());
            productList.add(jsonObject);

        }
        return productList;
    }

    public JSONObject getProductQuantity(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", this.getOrderQuantity().get(id));
        return jsonObject;
    }

    public float getTotalPriceOfOrder() {
        Iterator<Product> productsIterator = getItemList().iterator();
        float totalPrice = 0;
        while (productsIterator.hasNext()) {
            Product product = productsIterator.next();
            totalPrice += product.getDefaultPrice();
        }
        return totalPrice;
    }
}