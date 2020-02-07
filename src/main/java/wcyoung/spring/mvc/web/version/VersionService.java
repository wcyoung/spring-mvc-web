package wcyoung.spring.mvc.web.version;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import wcyoung.spring.mvc.bean.ApplicationInitializer;
import wcyoung.spring.mvc.common.base.BaseService;
import wcyoung.spring.mvc.mapper.springmvc.SpringMvcMapper;

@Service
public class VersionService extends BaseService {

    @Resource
    private ApplicationInitializer applicationInitializer;

    @Resource
    private SpringMvcMapper springMvcMapper;

    public String getManifestVersion() throws IOException {
        return applicationInitializer.getManifestInfo("Implementation-Version");
    }

    public String selectDbVersion() {
        return springMvcMapper.selectDbVersion();
    }

}
