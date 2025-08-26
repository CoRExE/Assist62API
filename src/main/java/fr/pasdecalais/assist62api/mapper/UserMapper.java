package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.UserResponseDTO;
import fr.pasdecalais.assist62api.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir les entités {@link User} en objets {@link UserResponseDTO}.
 */
@Component
public class UserMapper {

    /**
     * Convertit une entité {@link User} en {@link UserResponseDTO}.
     *
     * @param user l'entité User à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }
}
