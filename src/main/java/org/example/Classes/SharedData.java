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

    public static Order order = new Order();
    public static void clearOrder() { order = new Order(); }
    
}
