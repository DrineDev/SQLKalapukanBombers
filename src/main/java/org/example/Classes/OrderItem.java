package org.example.Classes;

public class OrderItem {
    private int _orderItemId;
    private int _orderId;
    private int _mealId;
    private int _quantity;
    private double _unitPrice;
    private double _subtotal;

    // Default constructor
    public OrderItem() {
        _orderItemId = 0;
        _orderId = 0;
        _mealId = 0;
        _quantity = 0;
        _unitPrice = 0.0;
        _subtotal = 0.0;
    }

    // Constructor for new order items (before DB insertion)
    public OrderItem(int orderId, int mealId, int quantity, double unitPrice) {
        _orderItemId = 0;
        _orderId = orderId;
        _mealId = mealId;
        _quantity = quantity;
        _unitPrice = unitPrice;
        calculateSubtotal();
    }

    // Full constructor (after DB retrieval)
    public OrderItem(int orderItemId, int orderId, int mealId, int quantity, double unitPrice, double subtotal) {
        _orderItemId = orderItemId;
        _orderId = orderId;
        _mealId = mealId;
        _quantity = quantity;
        _unitPrice = unitPrice;
        _subtotal = subtotal;
    }

    // Getters
    public int getOrderItemId() { return _orderItemId; }
    public int getOrderId() { return _orderId; }
    public int getMealId() { return _mealId; }
    public int getQuantity() { return _quantity; }
    public double getUnitPrice() { return _unitPrice; }
    public double getSubtotal() { return _subtotal; }

    // Setters
    public void setOrderItemId(int orderItemId) { _orderItemId = orderItemId; }
    public void setOrderId(int orderId) { _orderId = orderId; }
    public void setMealId(int mealId) { _mealId = mealId; }
    public void setQuantity(int quantity) { 
        _quantity = quantity;
        calculateSubtotal();
    }
    public void setUnitPrice(double unitPrice) { 
        _unitPrice = unitPrice;
        calculateSubtotal();
    }

    // Helper method to calculate subtotal
    private void calculateSubtotal() {
        _subtotal = _quantity * _unitPrice;
    }
}
