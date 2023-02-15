package recipes.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import recipes.Models.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    void deleteById(long id);

    List<Recipe> findRecipesByCategoryIgnoreCaseLikeOrderByDateDesc(String category);

    List<Recipe> findRecipesByNameIgnoreCaseContainsOrderByDateDesc(String name);
}
