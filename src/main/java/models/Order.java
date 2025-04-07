package models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String customerEmail;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;
    private String deliveryAddress;
    private String phoneNumber;

    public Order(int id, String customerEmail, LocalDateTime orderDate, double totalAmount, String status, String deliveryAddress, String phoneNumber) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
    }

    public Order(int id, String customerEmail, String orderDate, String items, double totalAmount, String status) {

    }

    public int getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getPhoneNumber() { return phoneNumber; }
}