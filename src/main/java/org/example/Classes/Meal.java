package org.example.Classes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Meal implements Cloneable {
  private String name;
  private String type;
  private String description;
  private String ingredients;
  private String servingSize;
  private String nutritionFact;
  private BufferedImage image;
  private String category;
  private boolean isSpicy;
  private static int instanceNumber = 0;
  private int idNumber;

  public Meal() throws IOException {
    name = "";
    type = "";
    description = "";
    ingredients = "";
    servingSize = "";
    image = ImageIO.read(new File("none.png"));
    nutritionFact = "";
    isSpicy = false;
    idNumber = instanceNumber;
    instanceNumber++;
  }

  public Meal(String name, String type, String description, String ingredients,
              String servingSize, BufferedImage image, String category, String nutritionFact, boolean isSpicy) {
    this.name = name;
    this.type = type;
    this.description = description;
    this.ingredients = ingredients;
    this.servingSize = servingSize;
    this.image = image;
    this.category = category;
    this.nutritionFact = nutritionFact;
    this.isSpicy = isSpicy;
    idNumber = instanceNumber;
    instanceNumber++;
  }

  public void setName(String name) {
    this.name = name;
  }
  public void setType(String type) {
    this.type = type;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }
  public void setServingSize(String servingSize) {
    this.servingSize = servingSize;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public void setNutritionFact(String nutritionFact) { this.nutritionFact = nutritionFact; }
  public void setImage(BufferedImage image) { this.image = image; }
  public void setIsSpicy(boolean isSpicy) { this.isSpicy = isSpicy; }

  public String getName() {
    return name;
  }
  public String getType() {
    return type;
  }
  public String getDescription() {
    return description;
  }
  public String getIngredients() {
    return ingredients;
  }
  public String getServingSize() {
    return servingSize;
  }
  public int getInstanceNumber() {
    return instanceNumber;
  }
  public String getCategory() {
    return category;
  }
  public String getNutritionFact() { return nutritionFact; }
  public BufferedImage getImage() { return image; }
  public boolean getIsSpicy() { return isSpicy; }


  @Override
  public int hashCode() {
    return idNumber;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Meal))
      return false;

    Meal meal2 = (Meal) obj;

    if (name != meal2.getName())
      return false;
    if (type != meal2.getType())
      return false;
    if (description != meal2.getDescription())
      return false;
    if (servingSize != meal2.getServingSize())
      return false;
    if (ingredients != meal2.getIngredients())
      return false;
    if (category != meal2.getCategory())
      return false;

    return true;
  }

  @Override
  public String toString() {
    String result;
    result = "Name: " + name + "\n" +
        "Type: " + type + "\n" +
        "Description: " + description + "\n" +
        "Ingredients: " + ingredients + "\n" +
        "Category: " + category + "\n" +
        "Type: " + type + "\n" +
        "Serving size: " + servingSize + "\n";

    return result;
  }

  @Override
  public Meal clone() {
    try {
      return (Meal) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
