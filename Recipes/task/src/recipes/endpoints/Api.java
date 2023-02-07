package recipes.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.Models.Recipe;
import recipes.Models.RecipeJson;
import recipes.Services.RecipeService;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api")
@Validated
public class Api {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipe/{id}")
    public RecipeJson getRecipe(@PathVariable Long id) {
        return recipeService.getById(id);
    }

    @PostMapping(value = "/recipe/new", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRecipe(@Valid @RequestBody RecipeJson newRecipe) {
        return ResponseEntity.ok(Map.of("id", recipeService.add(newRecipe)));
    }

    @DeleteMapping("/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id) {
        recipeService.deleteById(id);
    }
}
