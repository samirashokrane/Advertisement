package ir.webmetric.advertisement.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * @author Samira Shokrane
 * shokrane.samira@gmail.com
 */
@Log4j2
@Service
public class VersionConfig {

    @Value("${app.version}")
    private String appVersion;

    public String getVersion() {
        return appVersion;
    }
}