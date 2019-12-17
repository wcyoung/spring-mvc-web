package wcyoung.spring.mvc.web.version;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import wcyoung.spring.mvc.common.base.BaseService;
import wcyoung.spring.mvc.mapper.springmvc.SpringMvcMapper;

@Service
public class VersionService extends BaseService {

    @Resource
    private ResourceLoader resourceLoader;

    @Resource
    private SpringMvcMapper springMvcMapper;

    public String getManifestVersion() throws IOException {
        Properties properties = new Properties();
        properties.load(resourceLoader.getResource("/META-INF/MANIFEST.MF").getInputStream());
        return properties.getProperty("Implementation-Version");
    }

    public String selectDbVersion() {
        return springMvcMapper.selectDbVersion();
    }

}
