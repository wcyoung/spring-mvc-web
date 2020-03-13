package wcyoung.spring.mvc.security;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import wcyoung.spring.mvc.security.annotation.ApplyXssFilter;
import wcyoung.spring.mvc.security.annotation.IgnoreXssFilter;

@ControllerAdvice
public class ResponseXssFilterAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public
    boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.hasMethodAnnotation(IgnoreXssFilter.class)) {
            return false;
        }

        if (returnType.getContainingClass().isAnnotationPresent(IgnoreXssFilter.class)
                && !returnType.hasMethodAnnotation(ApplyXssFilter.class)) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        // TODO value filter

        return body;
    }

}
