package fiverr.controller;

import java.net.URI;
import java.security.Principal;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fiverr.entity.Image;
import fiverr.service.ImageService;

@RestController
@MultipartConfig
@RequestMapping("api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> showImage(@PathVariable Long id, Principal principal) {
        Image image = imageService.findById(id);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
            .body(new ByteArrayResource(image.getData()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("upload")
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile image, Principal principal, HttpServletRequest request) {
        Object isAvatar = request.getParameter("avatar");

        Image result = isAvatar == null ? imageService.save(principal.getName(), image) : imageService.saveAsAvatar(principal.getName(), image);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/profile/images/{id}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("{id}/as-avatar")
    public ResponseEntity setAvatar(@PathVariable Long id, Principal principal) {
        Image result = imageService.setAvatar(principal.getName(), id);

        return ResponseEntity.noContent().build();
    }
}
