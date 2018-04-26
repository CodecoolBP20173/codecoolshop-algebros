package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.codecool.shop.controller.AdminLog;
import java.time.LocalDateTime;

public class CheckoutProcess extends AbstractProcess {
    private int processId;
    private static AtomicInteger idCounter = new AtomicInteger(10000);
    private static AdminLog cartLog;


    public static AdminLog getCartLog() {
        return cartLog;
    }

    public Map getBuyerInfo() {
        return buyerInfo;
    }

    private Map buyerInfo;

    public CheckoutProcess(Map buyerInfo) {
        this.processId = idCounter.getAndIncrement();
        this.buyerInfo = buyerInfo;
    }

    protected void action(Order order) {
        order.checkout(this);
        cartLog = new AdminLog(order.getId());
        cartLog.logToAdminLog("Order with ID: " + order.getId() + " checked out, proceeds to payment." );
    }
}
