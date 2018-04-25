package com.codecool.shop.model;

public class PaymentProcess extends AbstractProcess {

    protected void action(Orderable item) {
        item.pay();
    }
}
