package org.example.Classes;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private static List<Meal> updatedMeals = new ArrayList<>();

    public static List<Meal> getUpdatedMeals() {
        return updatedMeals;
    }

    public static void addUpdatedMeal(Meal meal) {
        updatedMeals.add(meal);
    }

    public static void clearUpdatedMeals() {
        updatedMeals.clear();
    }

    private static List<OrderItem> orderItems = new ArrayList<>();

    public static List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public static void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public static void clearOrderItems() {
        orderItems.clear();
    }
}
