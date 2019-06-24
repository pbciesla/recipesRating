package com.github.pbciesla.recipesRating.config;

import com.github.pbciesla.recipesRating.RecipesRatingApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RecipesRatingApplication.class);
	}

}
