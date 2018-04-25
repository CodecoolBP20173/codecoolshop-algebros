package com.codecool.shop.model;

public class CheckoutProcess extends AbstractProcess {

    protected void action(Orderable item) {
        item.checkout();
    }
}
