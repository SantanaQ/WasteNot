package org.mf.csv;

import org.mf.database.InsertRecipe;
import org.mf.model.Recipe;

import java.util.List;

public class InsertIntoDB {

    public static void main(String[] args) {

        Parser parser = new Parser("assets/recipes.csv");
        List<Recipe> recipes = parser.asList();
        InsertRecipe dbStatements = new InsertRecipe();
        for (Recipe recipe : recipes) {
            dbStatements.addRecipe(recipe);
            System.out.println(recipe);
        }

    }

}
