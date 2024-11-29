package org.mf.database;

import org.mf.model.Ingredient;
import org.mf.model.Recipe;
import org.mf.model.RecipeBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: InsertRecipe Queries vereinfachen durch joins
 */

public class FilterQueries {

    public List<Recipe> getByIngredients(String[] ingredients, String[] categories) {
        List<Recipe> foundRecipes = new ArrayList<>();
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql;
            if (categories == null || categories.length == 0) {
                sql = "SELECT r.*, COUNT(*) as matching_ingredients\n" +
                        "FROM recipe as r\n" +
                        "JOIN recipeingredient AS ri ON r.recipeid = ri.recipeid\n" +
                        "JOIN ingredient AS i ON ri.ingredientid = i.ingredientid\n" +
                        "WHERE i.i_name = ANY(?)\n" +
                        "GROUP BY r.recipeid\n" +
                        "ORDER BY matching_ingredients DESC";
            }else
                sql = "SELECT r.*, COUNT(*) as matching_ingredients\n" +
                        "FROM recipe as r\n" +
                        "JOIN recipeingredient AS ri ON r.recipeid = ri.recipeid\n" +
                        "JOIN ingredient AS i ON ri.ingredientid = i.ingredientid\n" +
                        "JOIN recipecategory AS rc ON r.recipeid = rc.recipeid\n" +
                        "JOIN category as c ON c.categoryid = rc.categoryid\n" +
                        "WHERE i.i_name = ANY(?)\n" +
                        "AND c.c_name = ANY(?)\n" +
                        "GROUP BY r.recipeid\n" +
                        "ORDER BY matching_ingredients DESC";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                Array ingredientsArray = con.createArrayOf("VARCHAR", ingredients);
                ps.setArray(1,ingredientsArray);
                Array categoriesArray = con.createArrayOf("VARCHAR", categories);
                ps.setArray(2,categoriesArray);
                ResultSet rs = ps.executeQuery();
                foundRecipes = getFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundRecipes;
    }

    public List<Recipe> getByTag(String tag) {
        List<Recipe> foundRecipes = new ArrayList<>();
        try (Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT r.*\n" +
                    "FROM recipe AS r\n" +
                    "JOIN recipetag AS rt ON r.recipeid = rt.recipeid\n" +
                    "JOIN tag as t ON rt.tagid = t.tagid\n" +
                    "WHERE t.t_name = ?";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,tag);
                ResultSet rs = ps.executeQuery();
                foundRecipes = getFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundRecipes;
    }

    public List<Recipe> getByCategory(String category) {
        List<Recipe> foundRecipes = new ArrayList<>();
        try (Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT r.*\n" +
                    "FROM recipe AS r\n" +
                    "JOIN recipecategory AS rc ON r.recipeid = rc.recipeid\n" +
                    "JOIN category as c ON rc.categoryid = c.categoryid\n" +
                    "WHERE c.c_name = ?";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,category);
                ResultSet rs = ps.executeQuery();
                foundRecipes = getFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundRecipes;
    }

    private List<Ingredient> getRecipeIngredients(int recipeId) {
        List<Ingredient> recipeIngredients = new ArrayList<>();
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT ri.amount, m.abbreviation, i.i_name\n" +
                    "FROM ingredient as i\n" +
                    "JOIN recipeingredient AS ri ON ri.ingredientid = i.ingredientid\n" +
                    "JOIN measurement AS m ON m.measurementid = ri.measurementid\n" +
                    "JOIN recipe AS r ON ri.recipeid = r.recipeid\n" +
                    "WHERE r.recipeid = ?";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1,recipeId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setAmount(rs.getDouble(1));
                    ingredient.setMeasurement(rs.getString(2));
                    ingredient.setName(rs.getString(3));
                    recipeIngredients.add(ingredient);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeIngredients;
    }

    private List<String> getRecipeCategories(int recipeId) {
        List<String> recipeCategories = new ArrayList<>();
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT c.c_name\n" +
                    "FROM category AS c\n" +
                    "JOIN recipecategory AS rc ON rc.categoryid = c.categoryid\n" +
                    "JOIN recipe AS r ON r.recipeid = rc.recipeid\n" +
                    "WHERE r.recipeid = ?";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1,recipeId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    recipeCategories.add(rs.getString(1));
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeCategories;
    }

    private List<String> getRecipeTags(int recipeId) {
        List<String> recipeTags = new ArrayList<>();
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT t.t_name\n" +
                    "FROM tag AS t\n" +
                    "JOIN recipetag AS rt ON rt.tagid = t.tagid\n" +
                    "JOIN recipe AS r ON r.recipeid = rt.recipeid\n" +
                    "WHERE r.recipeid = ?";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1,recipeId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    recipeTags.add(rs.getString(1));
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeTags;
    }

    private List<Recipe> getFromRS(ResultSet rs) throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        while (rs.next()) {
            Recipe r = new RecipeBuilder()
                    .withName(rs.getString("r_name"))
                    .withDescription(rs.getString("description"))
                    .withInstructions(rs.getString("instructions"))
                    .withIngredients(getRecipeIngredients(rs.getInt("recipeid")))
                    .withCategories(getRecipeCategories(rs.getInt("recipeid")))
                    .withTags(getRecipeTags(rs.getInt("recipeid")))
                    .withMinutes_prep(rs.getInt("preptime"))
                    .withMinutes_cooking(rs.getInt("cookingtime"))
                    .withServings(rs.getInt("servings"))
                    .withCreator(rs.getString("creator"))
                    .withImageName(rs.getString("image"))
                    .withIngredientCount(rs.getInt("matching_ingredients"))
                    .build();
            recipes.add(r);
        }
        rs.close();
        return recipes;
    }


}
