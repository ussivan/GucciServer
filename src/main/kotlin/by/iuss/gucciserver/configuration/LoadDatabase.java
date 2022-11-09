package by.iuss.gucciserver.configuration;

import by.iuss.gucciserver.entity.Kal;
import by.iuss.gucciserver.repository.KalRepository;
import by.iuss.gucciserver.service.YouTubeService;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    final YouTubeService youTubeService;

    public LoadDatabase(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @Bean
    CommandLineRunner initFileDownload(KalRepository repository) {
        return args -> {
            log.info("технологии заебали нахуй");
            youTubeService.searchVideos("пудж", youTubeService.auth());
        };
    }

}