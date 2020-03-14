package wcyoung.spring.mvc.security;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import wcyoung.spring.mvc.common.security.AbstractResponseXssFilterAdvice;

@ControllerAdvice
public class ResponseXssFilterAdvice extends AbstractResponseXssFilterAdvice {

    @Override
    protected boolean supportsMediaType(MediaType mediaType) {
        return true;
    }

}
