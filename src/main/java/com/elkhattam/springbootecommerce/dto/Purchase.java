package com.elkhattam.springbootecommerce.dto;

import com.elkhattam.springbootecommerce.entity.Address;
import com.elkhattam.springbootecommerce.entity.Customer;
import com.elkhattam.springbootecommerce.entity.Order;
import com.elkhattam.springbootecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;
}
