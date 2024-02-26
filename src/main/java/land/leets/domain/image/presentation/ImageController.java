package land.leets.domain.image.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    @Value("${image.path}")
    private String imageStoragePath;

    @GetMapping("/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        Resource resource = new ClassPathResource(imageStoragePath + imageName);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
