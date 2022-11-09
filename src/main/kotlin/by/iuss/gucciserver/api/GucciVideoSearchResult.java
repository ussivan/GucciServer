package by.iuss.gucciserver.api;

public class GucciVideoSearchResult {

    /**
     * id of video in YouTube, {@code 8P3lglutFTY} for {@code https://www.youtube.com/watch?v=8P3lglutFTY}
     */
    private final String id;

    /**
     * Video title
     */
    private final String title;

    public GucciVideoSearchResult(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
