package land.leets.global.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final String API_DOCS_PATH = "/v3/api-docs";
    private static final String IMAGE_PATH = "/images";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest req, ServerHttpResponse res) {
        if (req.getURI().getPath().contains(API_DOCS_PATH)) return body;
        if (req.getURI().getPath().contains(IMAGE_PATH)) return body;

        Map<String, Object> updatedResponse = new HashMap<>();

        updatedResponse.put("result", body);
        return updatedResponse;
    }
}
