package wcyoung.spring.mvc.bean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ResourceLoader;

public class ApplicationInitializer implements InitializingBean, DisposableBean {

    @Resource
    private ResourceLoader resourceLoader;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Properties manifestInfo;

    @Override
    public void afterPropertiesSet() throws Exception {
        loadManifestInfo();

        loggingStartup();
    }

    @Override
    public void destroy() throws Exception {
        loggingShutdown();
    }

    private boolean loadManifestInfo() {
        manifestInfo = new Properties();
        try {
            manifestInfo.load(resourceLoader.getResource("/META-INF/MANIFEST.MF").getInputStream());
        } catch (IOException e) {
            log.error("Exception : {}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    private void loggingStartup() {
        String unknown = "Unknown";
        String title = manifestInfo.getProperty("Implementation-Title", unknown);
        String vendor = manifestInfo.getProperty("Implementation-Vendor", unknown);
        String version = manifestInfo.getProperty("Implementation-Version", unknown);
        log.info("============================================================");
        log.info("[STARTUP] :: {} ({}) - (v{}) ::", title, vendor, version);
        log.info("============================================================");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println();
        System.out.print("=============================================");
        System.out.println("=============================================");
        System.out.println(String.format("[%s] - [STARTUP] :: %s (%s) - (v%s) ::",
                        now.format(formatter), title, vendor, version));
        System.out.print("=============================================");
        System.out.println("=============================================");
    }

    private void loggingShutdown() {
        String unknown = "Unknown";
        String title = manifestInfo.getProperty("Implementation-Title", unknown);
        String vendor = manifestInfo.getProperty("Implementation-Vendor", unknown);
        String version = manifestInfo.getProperty("Implementation-Version", unknown);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println();
        System.out.print("=============================================");
        System.out.println("=============================================");
        System.out.println(String.format("[%s] - [SHUTDOWN] :: %s (%s) - (v%s) ::",
                now.format(formatter), title, vendor, version));
        System.out.print("=============================================");
        System.out.println("=============================================");
    }

}
