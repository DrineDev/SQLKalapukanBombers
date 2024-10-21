package org.example;

public class Meal implements Cloneable {
    private String name;
    private String type;
    private String description;
    private String[] ingredients;
    private String servingSize;
    private int numberOfIngredients;
    private static int instanceNumber = 0;
    private int idNumber;

    public Meal() {
        name = "";
        type = "";
        description = "";
        numberOfIngredients = 0;
        ingredients = new String[numberOfIngredients];
        servingSize = "";
        idNumber = instanceNumber;
        instanceNumber++;
    }

    public Meal(String name, String type, String description, String[] ingredients,
                String servingSize, int numberOfIngredients) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.ingredients = ingredients;
        this.servingSize = servingSize;
        this.numberOfIngredients = numberOfIngredients;
        idNumber = instanceNumber;
        instanceNumber++;
    }


    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setIngredients(int i, String ingredients) { this.ingredients[i] = ingredients; }
    public void setNumberOfIngredients(int numberOfIngredients) {
        this.ingredients = new String[numberOfIngredients];
        this.numberOfIngredients = numberOfIngredients;
    }
    public void setServingSize(String servingSize) { this.servingSize = servingSize; }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public int getNumberOfIngredients() { return numberOfIngredients; }
    public String[] getIngredients() { return ingredients; }
    public String getServingSize() { return servingSize; }
    public int getInstanceNumber() { return instanceNumber; }

    @Override public int hashCode() { return idNumber; }

    @Override public boolean equals(Object obj) {
        if (!(obj instanceof Meal))
            return false;

        Meal meal2 = (Meal)obj;

        if (name != meal2.getName())
            return false;
        if (type != meal2.getType())
            return false;
        if (description != meal2.getDescription())
            return false;
        if (numberOfIngredients != meal2.getNumberOfIngredients())
            return false;
        if (servingSize != meal2.getServingSize())
            return false;

        String[] tempIngredients = meal2.getIngredients();
        for(int i = 0; i < numberOfIngredients; i++) {
            if (ingredients[i] != tempIngredients[i])
                return false;
        }

        return true;
    }

    @Override public String toString() {
        String result;
        result =   "Name: " + name + "\n"+
                   "Type: " + type + "\n" +
                   "Description: " + description + "\n";
        for(int i = 0; i < numberOfIngredients; i++)
           result += "Ingredients #" + (i + 1) + ": " + ingredients[i] + "\n";
        result +=  "Serving size: " + servingSize;

        return result;
   }

    @Override public Meal clone()
    {
        try {
            return (Meal)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
