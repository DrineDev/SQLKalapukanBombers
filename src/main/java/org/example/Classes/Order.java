package org.example.Classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.SQLQueries.SQLOrder;

public class Order {
    private static int _orderId;
    private String _orderDate;
    private String _status;
    private double _totalAmount;
    private List<OrderItem> _orderItems;

    // Default constructor
    public Order() {
        _orderId = SQLOrder.getNextOrderId();
        _orderDate = LocalDateTime.now().toString();
        _status = "Pending";
        _totalAmount = 0.0;
        _orderItems = new ArrayList<>();
    }

    // Constructor with basic info
    public Order(LocalDateTime orderDate, String status) {
        _orderId = 0;
        _orderDate = orderDate.toString();
        _status = status;
        _totalAmount = 0.0;
        _orderItems = new ArrayList<>();
    }

    // Full constructor
    public Order(int orderId, String orderDate, String status, double totalAmount) {
        _orderId = orderId;
        _orderDate = orderDate;
        _status = status;
        _totalAmount = totalAmount;
        _orderItems = new ArrayList<>();
    }

    // Getters
    public static int getOrderId() { return _orderId; }
    public String getOrderDate() { return _orderDate; }
    public String getStatus() { return _status; }
    public double getTotalAmount() { return _totalAmount; }
    public List<OrderItem> getOrderItems() { return _orderItems; }

    // Setters
    public void setOrderId(int orderId) { _orderId = orderId; }
    public void setOrderDate(LocalDateTime orderDate) { _orderDate = orderDate.toString(); }
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

    public static class Builder {
        private int orderId;
        private String orderDate;
        private String status;
        private double totalAmount;
        private List<OrderItem> orderItems;

        public Builder() {
            this.orderDate = LocalDateTime.now().toString();
            this.status = "Pending";
            this.totalAmount = 0.0;
            this.orderItems = new ArrayList<>();
        }

        public Builder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate.toString();
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            Order order = new Order(orderId, orderDate, status, totalAmount);
            if (orderItems != null) {
                orderItems.forEach(order::addOrderItem);
            }
            return order;
        }
    }
}