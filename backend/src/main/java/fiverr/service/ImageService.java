package fiverr.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fiverr.entity.Image;

public interface ImageService {

    Image save(String email, MultipartFile image);

    Image saveAsAvatar(String email, MultipartFile image);

    Image setAvatar(String email, Long id);

    List<Image> getByUser(String email);

    Image getImage(String email, Long id);

    Image findById(Long id);
}
