package com.github.pbciesla.recipesRating.recipe;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @GetMapping(path = "/all")
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping(path = "/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer recipeId) {
        return recipeRepository.findById(recipeId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/category/{recipeCategory}")
    public List<Recipe> getRecipeByCategory(@PathVariable String recipeCategory) {
        return recipeRepository.findAllByCategory(recipeCategory);

    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @PutMapping(path = "/{recipeId}")
    public Recipe updateRecipe(@PathVariable Integer recipeId, @Validated @RequestBody Recipe newRecipe) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipe.setName(newRecipe.getName());
            recipe.setCategory(newRecipe.getCategory());
            recipe.setPreparationTime(newRecipe.getPreparationTime());
            recipe.setDifficult(newRecipe.getDifficult());
            recipe.setRate(newRecipe.getRate());
            recipe.setNotes(newRecipe.getNotes());
            recipe.setLink(newRecipe.getLink());
            return recipeRepository.save(recipe);
        }).orElseGet(() -> {
            newRecipe.setId(recipeId);
            return recipeRepository.save(newRecipe);
        });
    }

    @DeleteMapping(path = "/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer recipeId) {
        recipeRepository.deleteById(recipeId);
        return ResponseEntity.noContent().build();
    }
}
