package by.iuss.gucciserver.controller;

import by.iuss.gucciserver.api.GucciVideoSearchResult;
import by.iuss.gucciserver.aspect.Profile;
import by.iuss.gucciserver.configuration.GucciProperties;
import by.iuss.gucciserver.service.FileStorageService;
import by.iuss.gucciserver.service.YouTubeApiService;
import by.iuss.gucciserver.service.YouTubeService;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class GucciApiController {

    private static final Logger log = LoggerFactory.getLogger(YouTubeService.class);
    private final GucciProperties properties;
    private final YouTubeApiService youTubeApiService;

    private final FileStorageService fileStorageService;

    public GucciApiController(YouTubeApiService youTubeApiService, FileStorageService fileStorageService, GucciProperties properties) {
        this.youTubeApiService = youTubeApiService;
        this.fileStorageService = fileStorageService;
        this.properties = properties;
    }

    @Profile
    @GetMapping("/gucci/search")
    public List<GucciVideoSearchResult> search(@RequestParam String query) throws IOException {
        return youTubeApiService.searchVideos(query);
    }

    @Profile
    @GetMapping("/gucci/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value = "id") String videoId, @RequestParam(value = "t", required = false) String type, HttpServletRequest request) {
        String downloadedFileName = videoId + System.currentTimeMillis();
        YoutubeProgressCallback<File> callback = new YoutubeProgressCallback<>() {
            @Override
            public void onDownloading(int progress) {
                if (progress % 10 == 0 || progress == 1) {
                    log.info("Downloading file {}, progress: {}%", downloadedFileName, progress);
                }
            }

            @Override
            public void onFinished(File data) {
                log.info("Finished downloading file {}, success", downloadedFileName);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Something went wrong while downloading file {}, error: {}", downloadedFileName, throwable);
            }
        };
        YouTubeService.DownloadFileFormat format = YouTubeService.DownloadFileFormat.BEST_AUDIO;
        if (type == null || type.equals("a")) {
            format = YouTubeService.DownloadFileFormat.BEST_AUDIO;
        } else if (type.equals("av")) {
            format = YouTubeService.DownloadFileFormat.BEST_AUDIO_AND_VIDEO;
        } else if (type.equals("v")) {
            format = YouTubeService.DownloadFileFormat.BEST_VIDEO;
        }
        File downloadedFile = youTubeApiService.downloadVideo(videoId, downloadedFileName, callback, format);
        return sendFile(downloadedFile, request);
    }

    private ResponseEntity<Resource> sendFile(File downloadedFile, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(downloadedFile.getAbsolutePath());
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
