package com.codecool.shop.model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckoutProcess extends AbstractProcess {
    private int processId;
    private static AtomicInteger idCounter = new AtomicInteger(10000);

    public Map getBuyerInfo() {
        return buyerInfo;
    }

    private Map buyerInfo;

    public CheckoutProcess(Map buyerInfo) {
        this.processId = idCounter.getAndIncrement();
        this.buyerInfo = buyerInfo;
    }

    protected void action(Orderable item) {
        item.checkout(this);
    }
}
