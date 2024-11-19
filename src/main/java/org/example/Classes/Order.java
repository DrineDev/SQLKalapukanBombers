package org.example.Classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int _orderId;
    private LocalDateTime _orderDate;
    private String _status;
    private double _totalAmount;
    private List<OrderItem> _orderItems;

    // Default constructor
    public Order() {
        _orderId = 0;
        _orderDate = LocalDateTime.now();
        _status = "Pending";
        _totalAmount = 0.0;
        _orderItems = new ArrayList<>();
    }

    // Constructor with basic info
    public Order(LocalDateTime orderDate, String status) {
        _orderId = 0;
        _orderDate = orderDate;
        _status = status;
        _totalAmount = 0.0;
        _orderItems = new ArrayList<>();
    }

    // Full constructor
    public Order(int orderId, LocalDateTime orderDate, String status, double totalAmount) {
        _orderId = orderId;
        _orderDate = orderDate;
        _status = status;
        _totalAmount = totalAmount;
        _orderItems = new ArrayList<>();
    }

    // Getters
    public int getOrderId() { return _orderId; }
    public LocalDateTime getOrderDate() { return _orderDate; }
    public String getStatus() { return _status; }
    public double getTotalAmount() { return _totalAmount; }
    public List<OrderItem> getOrderItems() { return _orderItems; }

    // Setters
    public void setOrderId(int orderId) { _orderId = orderId; }
    public void setOrderDate(LocalDateTime orderDate) { _orderDate = orderDate; }
    public void setStatus(String status) { _status = status; }
    public void setTotalAmount(double totalAmount) { _totalAmount = totalAmount; }

    // Order Items management
    public void addOrderItem(OrderItem item) {
        _orderItems.add(item);
        updateTotalAmount();
    }

    public void removeOrderItem(OrderItem item) {
        _orderItems.remove(item);
        updateTotalAmount();
    }

    // Helper method to update total amount
    private void updateTotalAmount() {
        _totalAmount = _orderItems.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }
}
