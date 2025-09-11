package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFilename(String filename);
}
