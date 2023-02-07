package recipes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    long id;

    @NotBlank
    String name;
    @NotBlank
    String description;


    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Ingredient.class)
    @OrderBy("idx")
    List<Ingredient> ingredients;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Direction.class)
    @OrderBy("idx")
    List<Direction> directions;
}
