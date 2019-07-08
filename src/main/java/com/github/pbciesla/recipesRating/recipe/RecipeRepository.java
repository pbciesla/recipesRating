package com.github.pbciesla.recipesRating.recipe;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    List<Recipe> findAllByCategory(String category);
}
