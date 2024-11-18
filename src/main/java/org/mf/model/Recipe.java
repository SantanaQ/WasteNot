package org.mf.model;

import java.util.List;

public class Recipe {

    private final String name;
    private final String description;
    private final String instructions;
    private final List<Ingredient> ingredients;
    private final List<String> tags;
    private final int minutes_prep;
    private final int minutes_cooking;
    private final int servings;
    private final List<String> categories;
    private final String creator;
    private final String imageName;
    private final int ingredientCount; //counts matching ingredients, when ingredient based recipe search

    public Recipe(RecipeBuilder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.instructions = builder.instructions;
        this.ingredients = builder.ingredients;
        this.tags = builder.tags;
        this.minutes_prep = builder.minutes_prep;
        this.minutes_cooking = builder.minutes_cooking;
        this.servings = builder.servings;
        this.categories = builder.categories;
        this.creator = builder.creator;
        this.imageName = builder.imageName;
        this.ingredientCount = builder.ingredientCount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getMinutes_prep() {
        return minutes_prep;
    }

    public int getMinutes_cooking() {
        return minutes_cooking;
    }

    public int getServings() {
        return servings;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getCreator() {
        return creator;
    }

    public String getImageName() {
        return imageName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe name: \t").append(name);
        sb.append("\ndescription: \t").append(description);
        sb.append("\ninstructions: \t").append(instructions);
        sb.append("\ningredients: \t");
        for (Ingredient ingredient : ingredients) {
            sb.append(ingredient.getAmount())
                    .append(" ")
                    .append(ingredient.getMeasurement())
                    .append(" ")
                    .append(ingredient.getName())
                    .append(", ");
        }
        sb.append("\ntags: \t").append(tags);
        sb.append("\nminutes_prep: \t").append(minutes_prep);
        sb.append("\nminutes_cooking: \t").append(minutes_cooking);
        sb.append("\nservings: \t").append(servings);
        sb.append("\ncategories: \t").append(categories);
        sb.append("\ncreator: \t").append(creator);
        sb.append("\nimageName: \t").append(imageName);
        return sb.toString();
    }

}
