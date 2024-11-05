package org.example.Classes;

public class Inventory {

    private int _mealId;
    private int _quantityAvailable;
    private float _price;
    private int _quantitySold;

    Inventory() {
        _mealId = 0;
        _quantityAvailable = 0;
        _price = 0;
        _quantitySold = 0;
    }

    Inventory(int mealId, int quantityAvailable, float price, int quantitySold) {
        _mealId = mealId; _quantityAvailable = quantityAvailable; _price = price; _quantitySold = quantitySold;
    }

    public int getMealId() { return _mealId; }
    public int getQuantityAvailable() { return _quantityAvailable; }
    public float getPrice() { return _price; }
    public int getQuantitySold() { return _quantitySold; }

    public void setMealId(int mealId) { _mealId = mealId; }
    public void setQuantityAvailable(int quantityAvailable) { _quantityAvailable = quantityAvailable; }
    public void setPrice(float price) { _price = price; }
    public void setQuantitySold(int quantitySold) { _quantitySold = quantitySold; }
}
