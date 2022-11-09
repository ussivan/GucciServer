package by.iuss.gucciserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:keys.properties")
public class GucciProperties {

    @Value("${google.api-key}")
    public String apiKey;

    @Value("${fileStorage.dir}")
    public String fileStoragePath;
}
