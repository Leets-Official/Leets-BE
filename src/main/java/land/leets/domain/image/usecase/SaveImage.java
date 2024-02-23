package land.leets.domain.image.usecase;

import org.springframework.web.multipart.MultipartFile;

public interface SaveImage {
    String save(MultipartFile pic) ;
}
