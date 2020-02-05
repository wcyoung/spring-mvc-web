package wcyoung.spring.mvc.web.version;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import wcyoung.spring.mvc.bind.annotation.JsonRequestMapping;
import wcyoung.spring.mvc.common.base.BaseController;

@JsonRequestMapping(value = "/version")
@RestController
public class VersionController extends BaseController {

    @Resource
    private VersionService versionService;

    @GetMapping(value = "/app")
    public ResponseEntity<Map<String, Object>> getAppVersion() {
        Map<String, Object> body = new HashMap<>();

        try {
            body.put("version", versionService.getManifestVersion());
        } catch (Exception e) {
            log.error("Exception: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping(value = "/db")
    public ResponseEntity<Map<String, Object>> selectDbVersion() {
        Map<String, Object> body = new HashMap<>();

        try {
            body.put("version", versionService.selectDbVersion());
        } catch (Exception e) {
            log.error("Exception: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
