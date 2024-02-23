package land.leets.domain.image.usecase;


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
            // 파일 저장 중 예외 발생 시 처리할 내용
            e.printStackTrace(); // 예외 메시지를 출력하거나 로깅하는 등의 작업을 수행합니다.
            return "error_occurred"; // 오류 발생 시 반환할 값을 설정합니다.
        }
    }
}
