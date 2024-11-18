package org.mf.csv;

import org.apache.commons.csv.*;
import org.mf.model.RecipeBuilder;
import org.mf.model.Recipe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private CSVParser csvParser;
    private List<CSVRecord> records;

    public Parser(String path) {
        try(Reader in = new FileReader(path, StandardCharsets.UTF_8)) {
            csvParser = CSVFormat.EXCEL.builder()
                    .setHeader()
                    .setDelimiter(';')
                    .build()
                    .parse(in);
            records = csvParser.getRecords();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recipe> asList(){
        List<Recipe> recipes = new ArrayList<>();
        for (CSVRecord record : records) {
            Recipe r = new RecipeBuilder()
                    .withName(record.get("name"))
                    .withDescription(record.get("description"))
                    .withInstructions(record.get("instructions"))
                    .withIngredients(record.get("ingredients").toLowerCase())
                    .withTags(record.get("tags").toLowerCase())
                    .withMinutes_prep(Integer.parseInt(record.get("preptime").replace(" min","")))
                    .withMinutes_cooking(Integer.parseInt(record.get("cookingtime").replace(" min","")))
                    .withServings(Integer.parseInt(record.get("servings")))
                    .withCategories(record.get("categories"))
                    .withCreator(record.get("creator"))
                    .withImageName(record.get("image"))
                    .build();
            recipes.add(r);
        }
        return recipes;
    }


}
