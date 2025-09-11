package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.NewsRequestDTO;
import fr.pasdecalais.assist62api.dto.NewsResponseDTO;
import fr.pasdecalais.assist62api.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des actualités.
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Récupère toutes les actualités.
     * @return liste de toutes les actualités
     */
    @GetMapping("/all")
    public ResponseEntity<List<NewsResponseDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    /**
     * Récupère une actualité par son identifiant.
     * @param id identifiant de l'actualité
     * @return l'actualité correspondante
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    /**
     * Crée une nouvelle actualité.
     * @param requestDTO données de l'actualité à créer
     * @return l'actualité créée
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<NewsResponseDTO> createNews(@Valid @RequestBody NewsRequestDTO requestDTO) {
        NewsResponseDTO createdNews = newsService.createNews(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdNews.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdNews);
    }

    /**
     * Met à jour une actualité existante.
     * @param id identifiant de l'actualité à mettre à jour
     * @param requestDTO nouvelles données de l'actualité
     * @return l'actualité mise à jour
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<NewsResponseDTO> updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequestDTO requestDTO) {
        NewsResponseDTO updatedNews = newsService.updateNews(id, requestDTO);
        return ResponseEntity.ok(updatedNews);
    }

    /**
     * Supprime une actualité.
     * @param id identifiant de l'actualité à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
