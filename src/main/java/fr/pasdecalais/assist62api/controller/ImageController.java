package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.model.Image;
import fr.pasdecalais.assist62api.repository.ImageRepository;
import fr.pasdecalais.assist62api.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final StorageService storageService;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(StorageService storageService, ImageRepository imageRepository) {
        this.storageService = storageService;
        this.imageRepository = imageRepository;
    }

    @GetMapping
    public ResponseEntity<List<String>> listUploadedFiles() {
        List<String> urls = storageService.loadAll().map(
                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/images/")
                        .path(path.getFileName().toString())
                        .toUriString())
                .toList();
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = storageService.store(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/")
                .path(filename)
                .toUriString();

        Image image = new Image();
        image.setFilename(filename);
        image.setUrl(url);
        image.setUploadedAt(LocalDateTime.now());
        imageRepository.save(image);

        return ResponseEntity.ok().body(url);
    }

    @DeleteMapping("/{filename:.+}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) {
        storageService.delete(filename);
        imageRepository.findByFilename(filename).ifPresent(imageRepository::delete);
        return ResponseEntity.noContent().build();
    }
}
