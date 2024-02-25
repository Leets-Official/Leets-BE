package land.leets.domain.image.usecase;


import land.leets.domain.image.exception.ImageSaveFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class SaveImageImpl implements SaveImage{

    @Value("${image.path}")
    private String imageStoragePath;

    @Override
    public String save(MultipartFile pic){
        if (pic == null){
            return "no_image";
        }
        try {
            UUID uuid = UUID.randomUUID();
            String imageFileName = uuid + "_" + pic.getOriginalFilename();
            Path imagePath = Paths.get(imageStoragePath + imageFileName);
            Files.write(imagePath, pic.getBytes());
            return imageFileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageSaveFailException();
        }
    }
}
