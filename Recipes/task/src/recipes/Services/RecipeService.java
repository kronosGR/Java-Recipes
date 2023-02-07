package recipes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.Exceptions.RecipeNotFoundException;
import recipes.Models.Direction;
import recipes.Models.Ingredient;
import recipes.Models.Recipe;
import recipes.Models.RecipeJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public Long add(RecipeJson recipe){
        Recipe tmp= recipeRepository.save(convertFromJson(recipe));
        return tmp.getId();
    }

    public RecipeJson getById(Long id){
        return convertToJson(recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new));
    }

    public void deleteById(long id){
        if (!recipeRepository.existsById(id)){
            throw new RecipeNotFoundException();
        }
        recipeRepository.deleteById(id);
    }
    private Recipe convertFromJson(RecipeJson recipeJson){
        List<Ingredient> ings = new ArrayList<>();
        List<Direction> dirs = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setName(recipeJson.getName());
        recipe.setDescription(recipeJson.getDescription());

        for (int i = 0; i < recipeJson.getIngredients().size(); i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(recipeJson.getIngredients().get(i));
            ingredient.setIdx(i + 1);
            ings.add(ingredient);
        }

        for (int i = 0; i < recipeJson.getDirections().size(); i++) {
            Direction direction = new Direction();
            direction.setDirection(recipeJson.getDirections().get(i));
            direction.setIdx(i + 1);
            dirs.add(direction);
        }

        recipe.setIngredients(ings);
        recipe.setDirections(dirs);

        return recipe;
    }

    private RecipeJson convertToJson(Recipe recipe){
        RecipeJson recipeJson = new RecipeJson();
        recipeJson.setName(recipe.getName());
        recipeJson.setDescription(recipe.getDescription());

        List<String> ings = new ArrayList<>();
        recipe.getIngredients().forEach(ingredient -> {
            ings.add(ingredient.getName());
        });
        List<String> dirs = new ArrayList<>();
        recipe.getDirections().forEach(direction -> {
            dirs.add(direction.getDirection());
        });

        recipeJson.setIngredients(ings);
        recipeJson.setDirections(dirs);
        return recipeJson;
    }

}
