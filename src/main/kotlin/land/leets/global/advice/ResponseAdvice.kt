package land.leets.global.advice

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class ResponseAdvice : ResponseBodyAdvice<Any> {

    companion object {
        private const val API_DOCS_PATH = "/v3/api-docs"
        private const val IMAGE_PATH = "/images"
    }

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        req: ServerHttpRequest,
        res: ServerHttpResponse
    ): Any? {
        if (req.uri.path.contains(API_DOCS_PATH)) return body
        if (req.uri.path.contains(IMAGE_PATH)) return body

        val updatedResponse = mutableMapOf<String, Any?>()
        updatedResponse["result"] = body
        return updatedResponse
    }
}
