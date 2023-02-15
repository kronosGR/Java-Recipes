package recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.Models.Recipe;
import recipes.Repositories.UserRepository;
import recipes.Services.RecipeService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping(path = "/api/recipe")
@Validated
public class Api {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    public Map<String, Object> getRecipe(@AuthenticationPrincipal UserDetails details,
                                         @PathVariable("id") long id) {
        final String user = details.getUsername();
        final Optional<Recipe> recipeContainer = recipeService.getRecipeById(id);
        if (recipeContainer.isPresent()) {
            return recipeContainer.get().objectToReturn();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER: " + user);
        }
    }

    @PostMapping("/new")
    public Map<String, Long> postNewRecipe(@AuthenticationPrincipal UserDetails details,
                                           @RequestBody Recipe recipe) {
        final String user = details.getUsername();
        recipe.setAuthor(user);
        final long newId = recipeService.saveOrUpdate(recipe, 0, user);
        Map<String, Long> returnId = Collections.unmodifiableMap(new HashMap<>(1) {{
            put("id", newId);
        }});
        return returnId;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@AuthenticationPrincipal UserDetails details,
                                          @Valid @PathVariable long id) {
        final String user = details.getUsername();
        if (recipeService.getRecipeById(id).isPresent()) {
            if (recipeService.getRecipeById(id).get().getAuthor() == user) {
                recipeService.deleteById(id);
                return new ResponseEntity<>("USER: " + user, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("USER: " + user, HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("USER: " + user, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@AuthenticationPrincipal UserDetails details,
                                          @Valid @RequestBody Recipe recipe,
                                          @PathVariable long id) {
        final String user = details.getUsername();
        if (recipeService.getRecipeById(id).isPresent()) {
            if (recipeService.getRecipeById(id).get().getAuthor() == user) {
                recipeService.saveOrUpdate(recipe, id, user);
                return new ResponseEntity<>("USER: " + user, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("USER: " + user, HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("USER: " + user, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/")
    @ResponseBody
    public List<Object> searchRecipes(@AuthenticationPrincipal UserDetails details,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "category", required = false) String category)
            throws ConstraintViolationException {
        final String user = details.getUsername();
        Optional<String> nameCheck = Optional.ofNullable(name);
        Optional<String> categoryCheck = Optional.ofNullable(category);
        if ((nameCheck.isEmpty() && categoryCheck.isEmpty()) || (nameCheck.isPresent()) && categoryCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER: " + user);
        } else if (nameCheck.isPresent()) {
            return recipeService.getRecipeByName(name);
        } else if (categoryCheck.isPresent()) {
            return recipeService.getRecipeByCategory(category);
        } else {
            return List.of();
        }
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" " + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occurred.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
