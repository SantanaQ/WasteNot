package org.mf.database;


import org.mf.model.Ingredient;
import org.mf.model.Recipe;

import java.sql.*;
import java.util.List;


/**
 * Exclusively used when new recipe is to be inserted into DB
 */
public class InsertRecipe {

    public void addRecipe(Recipe r) {

        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO Recipe VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
            ResultSet recipeRs = null;
            int recipeKey = -1;
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, r.getName());
                ps.setString(2, r.getDescription());
                ps.setString(3, r.getInstructions());
                ps.setInt(4, r.getMinutes_prep());
                ps.setInt(5, r.getMinutes_cooking());
                ps.setInt(6, r.getServings());
                ps.setString(7, r.getCreator());
                ps.setString(8, r.getImageName());
                ps.executeUpdate();

                // save key of recipe
                recipeRs = ps.getGeneratedKeys();
                if (recipeRs.next()) {
                    recipeKey = recipeRs.getInt(1);
                }
            }

            addTags(r.getTags(), recipeKey);
            addIngredients(r.getIngredients(), recipeKey);
            addCategories(r.getCategories(), recipeKey);

            recipeRs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void addTags(List<String> tags, int recipeKey) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO tag (t_name) VALUES (?) ON CONFLICT (t_name) DO NOTHING";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (String tag : tags) {
                    ps.setString(1, tag);
                    ps.executeUpdate();
                    if(ps.getGeneratedKeys().next()){
                        addRecipeTag(ps.getGeneratedKeys().getInt(1), recipeKey);
                    } else {
                        sql = "SELECT * FROM tag WHERE t_name = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, tag);
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                addRecipeTag(rs.getInt(1), recipeKey);
                            }
                            rs.close();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addRecipeTag(int tagKey, int recipeKey) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO recipetag VALUES(?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, tagKey);
                ps.setInt(2, recipeKey);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addIngredients(List<Ingredient> ingredients, int recipeKey) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO ingredient (i_name) VALUES (?) ON CONFLICT (i_name) DO NOTHING";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (Ingredient ingredient : ingredients) {
                    ps.setString(1, ingredient.getName());
                    ps.executeUpdate();
                    if(ps.getGeneratedKeys().next()){
                        addMeasurement(ingredient.getMeasurement(), ingredient.getAmount(), recipeKey, ps.getGeneratedKeys().getInt(1));
                    } else {
                        sql = "SELECT * FROM ingredient WHERE i_name = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, ingredient.getName());
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                addMeasurement(ingredient.getMeasurement(), ingredient.getAmount(), recipeKey, rs.getInt(1));
                            }
                            rs.close();
                        }
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMeasurement(String measurement, double amount,  int recipeKey, int ingredientKey) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO measurement (abbreviation) VALUES (?) ON CONFLICT (abbreviation) DO NOTHING";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, measurement);
                ps.executeUpdate();
                if(ps.getGeneratedKeys().next()){
                    addRecipeIngredient(ps.getGeneratedKeys().getInt(1), recipeKey, ingredientKey, amount);
                } else {
                    sql = "SELECT * FROM measurement WHERE abbreviation = ?";
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setString(1, measurement);
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            addRecipeIngredient(rs.getInt(1), recipeKey, ingredientKey, amount);
                        }
                        rs.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addRecipeIngredient(int measurementKey, int recipeKey, int ingredientKey ,double amount) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO recipeingredient VALUES(?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
               ps.setInt(1, recipeKey);
               ps.setInt(2, ingredientKey);
               ps.setDouble(3, measurementKey);
               ps.setDouble(4, amount);
               ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCategories(List<String> categories, int recipeKey) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO category (c_name) VALUES (?) ON CONFLICT (c_name) DO NOTHING";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (String category : categories) {
                    ps.setString(1, category);
                    ps.executeUpdate();
                    if(ps.getGeneratedKeys().next()){
                        addRecipeCategory(ps.getGeneratedKeys().getInt(1), recipeKey);
                    } else {
                        sql = "SELECT * FROM category WHERE c_name = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, category);
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                addRecipeCategory(rs.getInt(1), recipeKey);
                            }
                            rs.close();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addRecipeCategory(int categoryKey, int recipeKey) {
        try(Connection con = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "INSERT INTO recipecategory VALUES(?, ?)";
            try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, categoryKey);
                ps.setInt(2, recipeKey);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
