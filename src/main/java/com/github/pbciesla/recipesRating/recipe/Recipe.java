package com.github.pbciesla.recipesRating.recipe;

import lombok.Data;

@Data
public class Recipe {
    String name;
    String category;
    String link;
    Difficult difficult;
    PreparationTime preparationTime;
    int rate;
    String notes;

    Recipe(String name, String category, String link) {
        this.name = name;
        this.category = category;
        this.link = link;
    }

}
