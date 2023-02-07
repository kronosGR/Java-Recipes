package recipes.Services;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.Models.Recipe;

public interface RecipeRepository  extends JpaRepository<Recipe, Long> {
}
