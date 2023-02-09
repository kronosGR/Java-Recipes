package recipes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.Exceptions.NoContentException;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Models.Recipe;
import recipes.Models.RecipeJson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public Long add(RecipeJson recipe) {
        Recipe tmp = recipeRepository.save(convertFromJson(recipe));
        return tmp.getId();
    }

    public RecipeJson getById(Long id) {
        return convertToJson(recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new));
    }

    public void deleteById(long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException();
        }
        recipeRepository.deleteById(id);
    }



    public void update(long id, RecipeJson newRecipe) {

          Recipe original= convertFromJson(getById(id));
            original.setId(id);
            original.setName(newRecipe.getName());
            original.setDescription(newRecipe.getDescription());
            original.setCategory(newRecipe.getCategory());
            original.setIngredients(newRecipe.getIngredients());
            original.setDirections(newRecipe.getDirections());
            recipeRepository.saveAndFlush(original);
    }

    public List<RecipeJson> search(String n, String c) {
        List<Recipe> tmp;
        if (n == null) {
            tmp = recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(c);
            return tmp.stream().map(item -> convertToJson(item)).toList();
        } else if (c == null) {
            tmp = recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(n);
            return tmp.stream().map(item -> convertToJson(item)).toList();
        } else {
            tmp = recipeRepository.findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(n, c);
            return tmp.stream().map(item -> convertToJson(item)).toList();
        }
    }


    private Recipe convertFromJson(RecipeJson recipeJson) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeJson.getName());
        recipe.setDescription(recipeJson.getDescription());
        recipe.setCategory(recipeJson.getCategory());
        recipe.setDate(LocalDateTime.parse(recipeJson.getDate().toString()));
        recipe.setIngredients(recipeJson.getIngredients());
        recipe.setDirections(recipeJson.getDirections());

        return recipe;
    }

    private RecipeJson convertToJson(Recipe recipe) {
        RecipeJson recipeJson = new RecipeJson();
        recipeJson.setName(recipe.getName());
        recipeJson.setDescription(recipe.getDescription());
        recipeJson.setCategory(recipe.getCategory());
        recipeJson.setDate(recipe.getDate().toString());

        recipeJson.setIngredients(recipe.getIngredients());
        recipeJson.setDirections(recipe.getDirections());
        return recipeJson;
    }
}
