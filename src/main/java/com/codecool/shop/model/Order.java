package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements Orderable {
    private static AtomicInteger uniqueId = new AtomicInteger();
    private int id;
    private String status;

    public List<Product> getItemList() {
        return itemList;
    }

    private List<Product> itemList = new ArrayList<>();

    public HashMap<Integer, Integer> getOrderQuantity() {
        return orderQuantity;
    }

    private HashMap<Integer , Integer> orderQuantity = new HashMap<>();

    public Order() {
        this.id = uniqueId.getAndIncrement();
        this.status = "new";
    }

    public String getStatus() {
        return status;
    }

    public void addProduct(int id) {
        if (!(itemList.contains(ProductDaoMem.getInstance().find(id)))) {
            itemList.add(ProductDaoMem.getInstance().find(id));
        }
        orderQuantity.merge(id, 1,Integer::sum);
    }

    public void removeProduct(int id) {
        if (orderQuantity.get(id) == 1) {
            itemList.remove(ProductDaoMem.getInstance().find(id));
            orderQuantity.remove(id);
        } else {
            orderQuantity.put(id, orderQuantity.get(id) - 1);
        }
    }

    
    public boolean checkout() {
        if (this.getStatus().equals("new")) {
            this.status = "checked";
            return true;
        }
        return false;
    }

    public boolean pay() {
        if (this.getStatus().equals("checked")) {
            this.status = "payed";
            return true;
        }
        return false;
    }

    public JSONArray getCartItems(){
        JSONArray productList = new JSONArray();
        JSONObject jsonObject;
        Iterator<Product> iterator = this.getItemList().iterator();
        while (iterator.hasNext()){
            jsonObject = new JSONObject();
            Product product = iterator.next();
            jsonObject.put("id", product.getId());
            jsonObject.put("name", product.getName());
            jsonObject.put("defaultPrice", product.getDefaultPrice());
            jsonObject.put("quantity", this.getOrderQuantity().get(product.getId()));
            productList.add(jsonObject);
        }
        return productList;
    }

    public JSONObject getProductQuantity(int id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", this.getOrderQuantity().get(id));
        return jsonObject;
    }
}