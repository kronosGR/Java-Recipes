package recipes.Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Data
@Entity(name="User")
@Table(name = "USERS")
@SecondaryTable(name = "AUTHORITIES", pkJoinColumns = @PrimaryKeyJoinColumn(name = "username"))
@NoArgsConstructor
public class User {
    @Id
    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    @Column(name = "username")
    String email;

    @NotBlank
    @Size(min = 8, max = 255)
    String password;

    @NotNull
    @Column(name = "enabled")
    private boolean isEnabled;

    @NotEmpty
    @Column(name = "authority", table = "AUTHORITIES")
    private String rolesAndAuthorities;

    public User(String email, String password, boolean isEnabled, String authority) {
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.rolesAndAuthorities = authority;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.isEnabled = true;
        this.rolesAndAuthorities = "USER";
    }
}
