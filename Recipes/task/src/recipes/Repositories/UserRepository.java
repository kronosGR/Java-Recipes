package recipes.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.Models.User;

public interface UserRepository extends JpaRepository<User, String> {
    public User getUserByEmail(String email);
}
