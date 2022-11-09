package by.iuss.gucciserver.service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class YouTubeService {

    private static final Logger log = LoggerFactory.getLogger(YouTubeService.class);

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private static final JsonFactory JSON_FACTORY = new GsonFactory();

    private static final YoutubeDownloader downloader = new YoutubeDownloader();


    public YouTube auth() {
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, request -> {
        }).setApplicationName("YoutubeVideoInfo").setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyBAtw5TNQ82XE74of8bwRckZ-vTDGbDBG8")).build();
    }

    public void searchVideos(String query, YouTube youTube) throws IOException {
        YouTube.Search.List search = youTube.search().list(Collections.singletonList("id,snippet"));


        search.setQ(query);
        search.setType(Collections.singletonList("video"));
        search.setMaxResults(10L);

        List<SearchResult> searchResultList = search.execute().getItems();
        searchResultList.forEach(it -> {
            try {
                log.info(it.toPrettyString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void downloadVideo(String id) {
        VideoInfo video = downloader.getVideoInfo(new RequestVideoInfo(id)).data();

        File outputDir = new File("./GUCCI_VIDEOS");
        Format format = video.videoFormats().get(0);

        RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                .saveTo(outputDir) // by default "videos" directory
                .renameTo("GUCCI MANE") // by default file name will be same as video title on youtube
                .overwriteIfExists(true); // if false and file with such name already exits sufix will be added video(1).mp4
        Response<File> response = downloader.downloadVideoFile(request);
        File data = response.data();
    }
}
