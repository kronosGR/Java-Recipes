package recipes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeJson {

    @NotBlank
    String name;

    @NotBlank
    String category;

    @JsonProperty
    String date;

    @NotBlank
    String description;

    @NotEmpty
    List<String> ingredients;

    @NotEmpty
    List<String> directions;



}
