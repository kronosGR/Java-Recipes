package recipes.Models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RecipeJson {

    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotEmpty
    List<String> ingredients;

    @NotEmpty
    List<String> directions;
}
