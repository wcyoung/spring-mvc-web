package wcyoung.spring.mvc.web.version;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/version")
@RestController
public class VersionController {

    @Resource
    private ResourceLoader resourceLoader;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVersionInfo() {
        Map<String, Object> body = new HashMap<>();
        String version = "";

        try {
            Properties properties = new Properties();
            properties.load(resourceLoader.getResource("/META-INF/MANIFEST.MF").getInputStream());
            version = properties.getProperty("Implementation-Version");
        } catch (Exception e) {
            e.printStackTrace();
        }

        body.put("version", version);

        return new ResponseEntity<Map<String,Object>>(body, HttpStatus.OK);
    }

}
