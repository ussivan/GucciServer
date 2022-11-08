package by.iuss.gucciserver.configuration;

import by.iuss.gucciserver.entity.Kal;
import by.iuss.gucciserver.repository.KalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(KalRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Kal("Gucci")));
            log.info("Preloading " + repository.save(new Kal("Mane")));
        };
    }
}