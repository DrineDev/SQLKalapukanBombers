package org.example.Classes;

public class Sales {
    private int mealId;
    private String mealName;
    private int totalQuantitySold;
    private double totalRevenue;  // Assuming you will also track revenue (price * quantity)

    // Constructor
    public Sales(int mealId, String mealName, int totalQuantitySold, double totalRevenue) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    // Getters and setters
    public int getMealId() {
        return mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "mealId=" + mealId +
                ", mealName='" + mealName + '\'' +
                ", totalQuantitySold=" + totalQuantitySold +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}

