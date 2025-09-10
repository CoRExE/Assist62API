package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.UserRequestDTO;
import fr.pasdecalais.assist62api.dto.UserResponseDTO;
import fr.pasdecalais.assist62api.mapper.UserMapper;
import fr.pasdecalais.assist62api.model.User;
import fr.pasdecalais.assist62api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des utilisateurs.
 * Fournit des méthodes pour les opérations CRUD sur les utilisateurs.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_WITH_ID = "User not found with id: ";
    private static final String USER_NOT_FOUND_WITH_NAME = "User not found with name: ";
    private static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Charge un utilisateur par son nom d'utilisateur (email).
     *
     * @param username le nom de l'utilisateur
     * @return l'utilisateur correspondant
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_NAME + username));
    }

    /**
     * Constructeur du service utilisateur.
     *
     * @param userRepository le repository utilisateur
     * @param userMapper le mapper pour convertir les entités en DTO
     * @param passwordEncoder l'encodeur pour les mots de passe
     */
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return une liste de DTO de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur
     * @return DTO de l'utilisateur
     * @throws EntityNotFoundException si l'utilisateur n'existe pas
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + id));
        return userMapper.toUserResponseDTO(user);
    }

    /**
     * Récupère un utilisateur par son email.
     *
     * @param email l'email de l'utilisateur
     * @return DTO de l'utilisateur
     * @throws EntityNotFoundException si l'utilisateur n'existe pas
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));
        return userMapper.toUserResponseDTO(user);
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userRequestDTO DTO contenant les informations de l'utilisateur à créer
     * @return DTO de l'utilisateur créé
     * @throws IllegalStateException si l'email existe déjà
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        userRepository.findByEmail(userRequestDTO.getEmail()).ifPresent(u -> {
            throw new IllegalStateException("Email already in use: " + userRequestDTO.getEmail());
        });

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(savedUser);
    }

    /**
     * Met à jour un utilisateur existant.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param userRequestDTO DTO contenant les nouvelles informations
     * @return DTO de l'utilisateur mis à jour
     * @throws EntityNotFoundException si l'utilisateur n'existe pas
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + id));

        existingUser.setName(userRequestDTO.getName());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setRole(userRequestDTO.getRole());
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponseDTO(updatedUser);
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + id);
        }
        userRepository.deleteById(id);
    }
}
