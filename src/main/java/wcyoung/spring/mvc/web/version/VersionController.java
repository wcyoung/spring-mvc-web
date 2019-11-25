package wcyoung.spring.mvc.web.version;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wcyoung.spring.mvc.common.BaseController;

@RequestMapping(value = "/version")
@RestController
public class VersionController extends BaseController {

    @Resource
    private ResourceLoader resourceLoader;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVersionInfo() {
        Map<String, Object> body = new HashMap<>();

        try {
            Properties properties = new Properties();
            properties.load(resourceLoader.getResource("/META-INF/MANIFEST.MF").getInputStream());
            body.put("version", properties.getProperty("Implementation-Version"));
        } catch (Exception e) {
            log.error("Exception : {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Map<String,Object>>(body, HttpStatus.OK);
    }

}
