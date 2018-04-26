package com.codecool.shop.model;

public interface Orderable {
    boolean checkout(CheckoutProcess checkoutProcess);
    boolean pay(PaymentProcess paymentProcess);
}
