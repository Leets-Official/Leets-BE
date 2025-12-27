package land.leets.domain.image.presentation

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/images")
class ImageController(
    @Value("\${image.path}") private val imageStoragePath: String
) {

    @GetMapping("/{imageName}")
    fun getImage(@PathVariable imageName: String): ResponseEntity<Resource> {
        val resource = ClassPathResource(imageStoragePath + imageName)
        return ResponseEntity(resource, HttpStatus.OK)
    }
}
