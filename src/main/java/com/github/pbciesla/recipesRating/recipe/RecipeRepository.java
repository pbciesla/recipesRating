package com.github.pbciesla.recipesRating.recipe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    @Query("SELECT recipes FROM Recipes recipes WHERE recipes.category =: category")
    List<Recipe> findAllByCategory(@Param("category") String category);
}
