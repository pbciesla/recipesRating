package com.github.pbciesla.recipesRating.recipe;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String name;
    String category;
    String link;
    Difficult difficult;
    PreparationTime preparationTime;
    int rate;
    String notes;

    public Recipe(String name, String category, String link) {
        this.name = name;
        this.category = category;
        this.link = link;
    }

    public Recipe() {}

}
