package com.codecool.shop.processes;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.codecool.shop.controller.AdminLog;
import com.codecool.shop.model.Order;

public class CheckoutProcess extends AbstractProcess {
    private int processId;
    private static AtomicInteger idCounter = new AtomicInteger(10000);
    private static AdminLog cartLog;
    private HashMap<String, String> buyerInfo;

    public static AdminLog getCartLog() {
        return cartLog;
    }

    public HashMap<String, String> getBuyerInfo() {
        return buyerInfo;
    }

    public CheckoutProcess(HashMap<String, String> buyerInfo) {
        this.processId = idCounter.getAndIncrement();
        this.buyerInfo = buyerInfo;
    }

    protected void action(Order order) {
        order.checkout(this);
        cartLog = new AdminLog(order.getId());
        cartLog.logStringToAdminLog(order.getId(), "Order with ID: " + order.getId() + " checked out, proceeds to payment." );
    }
}
