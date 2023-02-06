package recipes.endpoints;

import org.springframework.web.bind.annotation.*;
import recipes.Models.Recipe;

@RestController
@RequestMapping(path="/api")
public class Api {

    Recipe tmpRecipe;

    @GetMapping("/recipe")
    public Recipe getRecipe(){
        return tmpRecipe;
    }

    @PostMapping(value = "/recipe", consumes = "application/json")
    public void addRecipe(@RequestBody Recipe newRecipe){
        tmpRecipe = newRecipe;
    }
}
