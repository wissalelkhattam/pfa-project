package com.elkhattam.springbootecommerce.service;

import com.elkhattam.springbootecommerce.dao.CustomerRepository;
import com.elkhattam.springbootecommerce.dto.Purchase;
import com.elkhattam.springbootecommerce.dto.PurchaseResponse;
import com.elkhattam.springbootecommerce.entity.Customer;
import com.elkhattam.springbootecommerce.entity.Order;
import com.elkhattam.springbootecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        //
        Order order = purchase.getOrder();

        String orderTackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTackingNumber);

        // populate order with order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billing and shipping address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null) {
            // we found them .. let's assign them accordingly
            customer = customerFromDB;
        }

        customer.add(order);

        // save to database
        customerRepository.save(customer);

        return new PurchaseResponse(orderTackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
