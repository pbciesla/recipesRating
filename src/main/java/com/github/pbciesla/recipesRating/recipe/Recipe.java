package com.github.pbciesla.recipesRating.recipe;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @NotNull
    @Size(min = 2, max = 25)
    String name;

    @NotNull
    @Size(min = 3, max = 25)
    String category;

    String link;
    Difficult difficult;
    PreparationTime preparationTime;

    @Min(1)
    Integer rate;

    String notes;

    public Recipe(String name, String category, String link) {
        this.name = name;
        this.category = category;
        this.link = link;
    }

    public Recipe() {
        System.out.println("asdad");
    }

}
