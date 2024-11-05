package org.example.SQLQueries;

import org.example.Meal;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ExecuteQueries {

    public static void main(String[] args) {
        Meal meal1 = null;
        try {
            meal1 = new Meal(
                    "Ginaling",
                    "Lunch",
                    "A savory dish made from ground pork sautéed with spices, onions, and vegetables, offering a comforting taste of home in every bite.",
                    "Ground pork, onions, garlic, bell peppers, soy sauce.",
                    "200 grams",
                    ImageIO.read(new File("pics/foods/ginaling.png")),
                    "Non-Vegetarian",
                    "Calories: Approximately 300 kcal, Protein: 25 g, Fat: 20 g, Carbohydrates: 5 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Larang",
                    "Lunch",
                    "A flavorful fish stew infused with spices and vegetables, offering a delightful combination of savory and tangy flavors.",
                    "Fish, tomatoes, onions, ginger, chili peppers.",
                    "250 grams",
                    ImageIO.read(new File("pics/foods/larang.png")),
                    "Non-Vegetarian",
                    "Calories: Approximately 200 kcal, Protein: 25 g, Fat: 10 g, Carbohydrates: 5 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Lemon Juice",
                    "Beverage",
                    "Refreshing and tangy, our homemade lemon juice is the perfect thirst-quencher, with just the right balance of sweetness and tartness.",
                    "Fresh lemons, sugar, water.",
                    "250 ml",
                    ImageIO.read(new File("pics/foods/lemon.png")),
                    "Vegan",
                    "Calories: Approximately 50 kcal, Protein: 0 g, Fat: 0 g, Carbohydrates: 13 g, Sugars: 10 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Pancit",
                    "Lunch",
                    "A traditional Filipino noodle dish packed with flavor, featuring stir-fried vegetables and your choice of meat or seafood, perfect for any occasion.",
                    "Egg noodles, vegetables, meat (or seafood), soy sauce.",
                    "200 grams",
                    ImageIO.read(new File("pics/foods/pancit.png")),
                    "Non-Vegetarian",
                    "Calories: Approximately 300 kcal, Protein: 15 g, Fat: 10 g, Carbohydrates: 45 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Sinigang",
                    "Dinner",
                    "A comforting sour soup made with fresh tamarind, vegetables, and your choice of meat, offering a delightful taste of Filipino cuisine.",
                    "Pork (or shrimp), tamarind, tomatoes, radish, eggplant.",
                    "300 grams",
                    ImageIO.read(new File("pics/foods/sinigang.png")),
                    "Non-Vegetarian",
                    "Calories: Approximately 250 kcal, Protein: 20 g, Fat: 10 g, Carbohydrates: 15 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Sweet and Sour Pork",
                    "Dinner",
                    "Crispy pork chunks tossed in a tangy sweet and sour sauce with colorful vegetables, creating a delightful blend of flavors.",
                    "Pork, bell peppers, pineapple, sweet and sour sauce.",
                    "250 grams",
                    ImageIO.read(new File("pics/foods/sweet and sour.png")),
                    "Non-Vegetarian",
                    "Calories: Approximately 400 kcal, Protein: 25 g, Fat: 20 g, Carbohydrates: 30 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Taho",
                    "Snack",
                    "A popular Filipino snack made with silken tofu, sweet syrup, and sago pearls, offering a delightful texture and sweetness in every spoonful.",
                    "Silken tofu, sweet syrup, sago pearls.",
                    "200 grams",
                    ImageIO.read(new File("pics/foods/taho.png")),
                    "Vegetarian",
                    "Calories: Approximately 150 kcal, Protein: 8 g, Fat: 5 g, Carbohydrates: 25 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);

        try {
            meal1 = new Meal(
                    "Tapioca Drink",
                    "Beverage",
                    "A refreshing drink made with tapioca pearls and sweetened milk, providing a delightful chewy texture and sweet flavor.",
                    "Tapioca pearls, milk, sugar.",
                    "300 ml",
                    ImageIO.read(new File("pics/foods/tapioca drink.png")),
                    "Vegetarian",
                    "Calories: Approximately 200 kcal, Protein: 5 g, Fat: 8 g, Carbohydrates: 35 g",
                    false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SQLMeal.addMeal(meal1);
    }

}
