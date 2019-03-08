package fiverr.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fiverr.entity.Image;
import fiverr.entity.User;
import fiverr.exception.ClientException;
import fiverr.exception.ResourceNotFoundException;
import fiverr.exception.ServiceException;
import fiverr.repository.ImageRepository;
import fiverr.service.ImageService;
import fiverr.service.UserService;
import fiverr.util.Translator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserService     userService;
    @Value("${app.images.max:10}")
    private       Integer         maxImages;

    public ImageServiceImpl(ImageRepository imageRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    @Override
    public Image save(String email, MultipartFile image) {
        log.debug("save() :: email = {}, image = {}", email, image.getName());
        // Normalize file name
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        User user = userService.findByEmail(email);

        try {
            if (imageRepository.countAllByUser(user) >= maxImages) {
                log.warn("save() :: user '{}' reached maximum allowed images count, max = {}", email, maxImages);
                throw new ClientException(Translator.translate("exception.images.maxAllowed"));
            }

            if (!image.getContentType().equals("image/jpeg")) {
                log.warn("save() :: image content type not allowed, type = {}", image.getContentType());
                throw new ClientException(Translator.translate("exception.images.formatNotAllowed"));
            }
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                log.warn("save() :: image name contain invalid characters, name {}", fileName);
                throw new ClientException("");
            }

            Image result = new Image();
            result.setName(fileName);
            result.setData(image.getBytes());
            result.setUser(user);

            return imageRepository.save(result);
        } catch (IOException ex) {
            log.error("save() :: could not store file, error = {}", ex.getMessage());
            throw new ServiceException("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public Image saveAsAvatar(String email, MultipartFile image) {
        log.debug("saveAsAvatar() :: email = {}, image = {}", email, image.getName());
        Image result = save(email, image);
        userService.setAvatar(email, result);
        return result;
    }

    @Override
    public List<Image> getByUser(String email) {
        log.debug("getByUser() :: email = {}", email);
        User user = userService.findByEmail(email);

        return imageRepository.findAllByUser(user);
    }

    @Override
    public Image setAvatar(String email, Long id) {
        log.debug("saveAsAvatar() :: email = {}, id = {}", email, id);

        User user = userService.findByEmail(email);

        Image result = imageRepository.findByUserAndId(user, id).orElseThrow(() -> {
            log.warn("saveAsAvatar() :: image not found for user, email = {}, id = {}", email, id);
            return new ResourceNotFoundException("");
        });

        userService.setAvatar(email, result);

        return result;
    }

    @Override
    public Image getImage(String email, Long id) {
        log.debug("getImage() :: email = {}, id = {}", email, id);

        User user = userService.findByEmail(email);

        Image result = imageRepository.findByUserAndId(user, id).orElseThrow(() -> {
            log.warn("getImage() :: image not found for user, email = {}, id = {}", email, id);
            return new ResourceNotFoundException("");
        });

        return result;
    }

    @Override
    public Image findById(Long id) {
        log.debug("findById() :: id = {}", id);

        return imageRepository.findById(id).orElseThrow(() -> {
            log.warn("getImage() :: image not found, id = {}", id);
            return new ResourceNotFoundException("");
        });
    }
}
