package recipes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    static long ID = 1L;
    @JsonIgnore
    long id = ID++;
    String name;
    String description;

    List<String> ingredients;
    List<String> directions;
}
