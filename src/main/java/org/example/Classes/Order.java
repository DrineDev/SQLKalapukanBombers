package org.example.Classes;

public class Order {

    private int _orderId;
    private int _mealId;
    private int _mealQuantity;
    private String _date;

    Order() {
        _orderId = 0;
        _mealId = 0;
        _mealQuantity = 0;
        _date = "";
    }

    Order(int orderId, int mealId, int mealQuantity, String date) {
        _orderId = orderId; _mealId = mealId; _mealQuantity = mealQuantity; _date = date;
    }

    public int getOrderId() { return _orderId; }
    public int getMealId() { return _mealId; }
    public int getMealQuantity() { return _mealQuantity; }
    public String getDate() { return _date; }

    public void setOrderId(int orderId) { _orderId = orderId; }
    public void setMealId(int mealId) { _mealId = mealId; }
    public void setMealQuantity(int mealQuantity) { _mealQuantity = mealQuantity; }
    public void setDate(String date) { _date = date; }
}
