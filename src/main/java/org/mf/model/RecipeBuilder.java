package org.mf.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeBuilder {

    String name;
    String description;
    String instructions;
    List<Ingredient> ingredients;
    List<String> tags;
    int minutes_prep;
    int minutes_cooking;
    int servings;
    List<String> categories;
    String creator;
    String imageName;
    int ingredientCount;

    public RecipeBuilder withName(String name){
        this.name = name;
        return this;
    }

    public RecipeBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public RecipeBuilder withInstructions(String instructions){
        this.instructions = instructions;
        return this;
    }

    public RecipeBuilder withIngredients(String ingredients){
        List<Ingredient> ingr = new ArrayList<>();
        String[] ingredientsArray = ingredients.split(", ");
        for(String ingredient : ingredientsArray){
            Ingredient ing = buildIngredient(ingredient);
            ingr.add(ing);
        }
        this.ingredients = ingr;
        return this;
    }

    public RecipeBuilder withIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        return this;
    }

    private Ingredient buildIngredient(String ingredient){
        String[] ingParts = ingredient.split(" ");
        Ingredient ing = new Ingredient();
        StringBuilder ingName = new StringBuilder();
        for(int i = 2; i < ingParts.length; i++){
            ingName.append(ingParts[i]);
        }
        ing.setAmount(Double.parseDouble(ingParts[0]));
        ing.setMeasurement(ingParts[1]);
        ing.setName(ingName.toString());
        return ing;
    }

    public RecipeBuilder withTags(String tags){
        this.tags = Arrays.asList(tags.split(", "));
        return this;
    }

    public RecipeBuilder withTags(List<String> tags){
        this.tags = tags;
        return this;
    }

    public RecipeBuilder withMinutes_prep(int minutes_prep){
        this.minutes_prep = minutes_prep;
        return this;
    }

    public RecipeBuilder withMinutes_cooking(int minutes_cooking){
        this.minutes_cooking = minutes_cooking;
        return this;
    }

    public RecipeBuilder withServings(int servings){
        this.servings = servings;
        return this;
    }

    public RecipeBuilder withCategories(String categories){
        this.categories = Arrays.asList(categories.split(", "));
        return this;
    }

    public RecipeBuilder withCategories(List<String> categories){
        this.categories = categories;
        return this;
    }

    public RecipeBuilder withCreator(String creator){
        this.creator = creator;
        return this;
    }

    public RecipeBuilder withImageName(String imageName){
        this.imageName = imageName;
        return this;
    }

    public RecipeBuilder withIngredientCount(int ingredientCount){
        this.ingredientCount = ingredientCount;
        return this;
    }

    public Recipe build(){
        return new Recipe(this);
    }

}
