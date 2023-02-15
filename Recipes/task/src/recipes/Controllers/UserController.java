package recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.Models.User;
import recipes.Repositories.UserRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping(value ="/api/register", consumes = APPLICATION_JSON_VALUE)
    public void register(@RequestBody User user) {
        boolean isUser = userRepository.getUserByEmail(user.getEmail()) == null;

        if (isUser && user.getEmail().trim().matches(".+@{1}.+\\..+") && user.getPassword().trim().matches(".{8,}")) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRolesAndAuthorities("USER");
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
