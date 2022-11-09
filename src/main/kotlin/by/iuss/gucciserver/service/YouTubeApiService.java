package by.iuss.gucciserver.service;


import by.iuss.gucciserver.api.GucciVideoSearchResult;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YouTubeApiService {

    private final YouTubeService youTubeService;

    public YouTubeApiService(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    public List<GucciVideoSearchResult> searchVideos(String query) throws IOException {
        List<SearchResult> searchResult = youTubeService.searchVideos(query, youTubeService.auth());

        return searchResult.stream().map(it ->
                new GucciVideoSearchResult(
                    it.getId().getVideoId(),
                    it.getSnippet().getTitle()
                )
        ).collect(Collectors.toList());
    }

    public File downloadVideo(String id, String outputFileName, YoutubeProgressCallback<File> callback, YouTubeService.DownloadFileFormat format) {
        return youTubeService.downloadVideo(id, outputFileName, callback, format);
    }

}
