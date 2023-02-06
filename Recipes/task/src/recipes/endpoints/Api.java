package recipes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.Models.Recipe;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
public class Api {

    Map<Long, Recipe> rcps = new HashMap<>();
    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipe( @PathVariable Long id){
        return rcps.containsKey(id) ? ResponseEntity.ok(rcps.get(id)) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/recipe/new", consumes = "application/json")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe newRecipe){
        rcps.put(newRecipe.getId(), newRecipe);
        return ResponseEntity.ok(Map.of("id", newRecipe.getId()));

    }
}
