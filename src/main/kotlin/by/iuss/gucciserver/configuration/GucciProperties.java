package by.iuss.gucciserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GucciProperties {

    @Value("${google.api-key}")
    public String apiKey;

    @Value("${fileStorage.dir}")
    public String fileStoragePath;
}
