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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/recipe")
@Validated
public class Api {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/{id}")
    public RecipeJson getRecipe(@PathVariable Long id) {
        return recipeService.getById(id);
    }

    @PostMapping(value = "/new", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRecipe(@Valid @RequestBody RecipeJson newRecipe) {
        newRecipe.setDate(LocalDateTime.now().toString());
        return ResponseEntity.ok(Map.of("id", recipeService.add(newRecipe)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id) {
        recipeService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeJson newRecipe) {
        recipeService.update(id, newRecipe);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeJson>> searchRecipe(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String category) {
        if (name == null && category == null) return ResponseEntity.badRequest().build();

       return ResponseEntity.ok(recipeService.search(name ,category));


    }
}
