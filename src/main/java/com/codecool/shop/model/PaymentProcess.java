package com.codecool.shop.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.codecool.shop.controller.AdminLog;

import java.time.LocalDateTime;

public class PaymentProcess extends AbstractProcess {
    private int processId;
    private static AtomicInteger idCounter = new AtomicInteger(10000);

    public PaymentProcess() {
        this.processId = idCounter.getAndIncrement();
    }

    protected void action(Orderable item) {
        item.pay(this);
    protected void action(Order order) {
        AdminLog cartLog = new AdminLog(order.getId());
        cartLog.getOrderLog().put(LocalDateTime.now(), order.getItemList());
        cartLog.logToFile();
    }
}
