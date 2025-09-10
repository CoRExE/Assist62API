package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.Role;
import fr.pasdecalais.assist62api.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFactory {

    /** Générateur de données fictives. */
    private final Faker faker = new Faker();

    /** Encodeur de mots de passe utilisant BCrypt. */
     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Crée un utilisateur fictif.
     *
     * @return un utilisateur
     */
    public User createUser() {
        User user = new User();
        user.setName(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(passwordEncoder.encode(faker.internet().password()));
        user.setRole(Role.USER);
        return user;
    }

    /**
     * Crée plusieurs utilisateurs fictifs.
     *
     * @param numberOfUsers le nombre d'utilisateurs à créer
     * @return une liste d'utilisateurs
     */
    public List<User> createUsers(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            users.add(createUser());
        }
        return users;
    }
}
