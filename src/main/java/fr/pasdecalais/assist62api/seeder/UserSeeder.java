package fr.pasdecalais.assist62api.seeder;

import fr.pasdecalais.assist62api.factory.UserFactory;
import fr.pasdecalais.assist62api.model.Role;
import fr.pasdecalais.assist62api.model.User;
import fr.pasdecalais.assist62api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSeeder {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    /**
     * Constructeur injectant les dépendances nécessaires à la génération et la persistance des utilisateurs.
     *
     * @param userFactory Fabrique pour créer des instances de User
     * @param userRepository Repository pour persister les utilisateurs
     */
    @Autowired
    public UserSeeder(UserFactory userFactory, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        User john = new User();
        john.setName("John Doe");
        john.setEmail("john.doe@gmal.aie");
        john.setPassword(passwordEncoder.encode("azerty"));
        john.setRole(Role.ADMIN);
        this.userRepository.save(john);
    }

    /**
     * Génère et persiste une liste d'utilisateurs.
     *
     * @param numUsers Nombre d'utilisateurs à générer
     */
    public void seedUsers(int numUsers) {
        List<User> users = userFactory.createUsers(numUsers);
        userRepository.saveAll(users);
    }
}
