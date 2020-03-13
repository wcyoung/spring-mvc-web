package wcyoung.spring.mvc.security;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    @SuppressWarnings("unchecked")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof String) {
            return filter((String) body);
        }

        if (body instanceof Map) {
            return filter((Map<String, Object>) body);
        }

        if (body instanceof List) {
            return filter((List<Object>) body);
        }

        return body;
    }

    private String filter(String value) {
        return value.replace("<", "&lt;").replace(">", "&gt;");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> filter(Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                map.put(key, filter((String) value));
            } else if (value instanceof Map) {
                map.put(key, filter((Map<String, Object>) value));
            } else if (value instanceof List) {
                map.put(key, filter((List<Object>) value));
            }
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    private List<Object> filter(List<Object> list) {
        for (int i = 0, length = list.size(); i < length; i++) {
            Object value = list.get(i);

            if (value instanceof String) {
                list.set(i, filter((String) value));
            } else if (value instanceof Map) {
                list.set(i, filter((Map<String, Object>) value));
            } else if (value instanceof List) {
                list.set(i, filter((List<Object>) value));
            }
        }

        return list;
    }

}
