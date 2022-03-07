package com.elkhattam.springbootecommerce.service;

import com.elkhattam.springbootecommerce.dto.Purchase;
import com.elkhattam.springbootecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
