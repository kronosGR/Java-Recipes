package recipes.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Recipe does not exist")
public class RecipeNotFoundException extends RuntimeException {
}
