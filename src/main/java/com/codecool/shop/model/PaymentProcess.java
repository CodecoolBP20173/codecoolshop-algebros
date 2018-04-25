package com.codecool.shop.model;

import com.codecool.shop.controller.AdminLog;

import java.time.LocalDateTime;

public class PaymentProcess extends AbstractProcess {

    protected void action(Order order) {
        AdminLog cartLog = new AdminLog(order.getId());
        cartLog.getOrderLog().put(LocalDateTime.now(), order.getItemList());
        cartLog.logToFile();
    }
}
