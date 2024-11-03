package org.example;

public class Meal implements Cloneable {
  private String name;
  private String type;
  private String description;
  private String ingredients;
  private String servingSize;
  private String imagePath;
  private String category;
  private static int instanceNumber = 0;
  private int idNumber;

  public Meal() {
    name = "";
    type = "";
    description = "";
    ingredients = "";
    servingSize = "";
    idNumber = instanceNumber;
    instanceNumber++;
    imagePath = "";
  }

  public Meal(String name, String type, String description, String ingredients,
      String servingSize, String imagePath, String category) {
    this.name = name;
    this.type = type;
    this.description = description;
    this.ingredients = ingredients;
    this.servingSize = servingSize;
    this.imagePath = imagePath;
    this.category = category;
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

  public String getImagePath() {
    return imagePath;
  }

  public String getCategory() {
    return category;
  }

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
