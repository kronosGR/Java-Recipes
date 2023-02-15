package recipes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Models.Recipe;
import recipes.Repositories.RecipeRepository;
import recipes.Repositories.UserRepository;
import java.util.List;
import java.util.Optional;


@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

       public Optional<Recipe> getRecipeById(long id) {
        return recipeRepository.findById(id);
    }

    public Long saveOrUpdate(Recipe recipe, long id, String author) {
        if (id == 0) {
            recipe.setAuthor(author);
            Recipe newRecipe = recipeRepository.save(recipe);
            return newRecipe.getId();
        } else {
            recipe.setAuthor(author);
            recipe.setId(id);
            recipeRepository.save(recipe);
            return id;
        }
    }
    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }

    public List<Object> getRecipeByName(String name) {
        try {
            List<Recipe> list = recipeRepository.findRecipesByNameIgnoreCaseContainsOrderByDateDesc(name);
            return Recipe.listToReturn(list);
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Object> getRecipeByCategory(String category) {
        try {
            List<Recipe> list = recipeRepository.findRecipesByCategoryIgnoreCaseLikeOrderByDateDesc(category);
            return Recipe.listToReturn(list);
        } catch (Exception e) {
            return List.of();
        }
    }


}
