package com.sampleyoutubevideo.app;



//import com.google.api.services.samples.youtube.cmdline.Auth;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
//import com.google.api.services.samples.youtube.cmdline.Auth;


import org.apache.http.auth.AUTH;


public class Main extends YouTubeBaseActivity implements
        CustomYouTubePlayer.OnInitializedListener {



    static TextView text;


    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    private static YouTube youtube;



    static private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";
    static private final String VIDEO = "HfeIJGuShxs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        //YouTubePlayerView youTubeView = (YouTubePlayerView)
                //findViewById(R.id.youtube_view);
        //youTubeView.initialize(DEVELOPER_KEY, this);

        /*Properties properties = new Properties();
        try {
            InputStream in = YouTube.Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            //System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    //+ " : " + e.getMessage());
            System.exit(1);
        }
*/
        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.


            youtube = new YouTube.Builder(null, null, new HttpRequestInitializer() {
                public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {
                }
            })
                    .setApplicationName("youtube-cmdline-search-sample")
                    .build();

            // Prompt the user to enter a query term.
            String queryTerm = getInputQuery();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the Google Developers Console for
            // non-authenticated requests. See:
            // https://cloud.google.com/console
            //String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(DEVELOPER_KEY);
            search.setQ(queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), queryTerm);
            }
        } catch (GoogleJsonResponseException e) {
            //System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
             //       + e.getDetails().getMessage());
        } catch (IOException e) {
            //System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }




    @Override
    public void onInitializationFailure(CustomYouTubePlayer.Provider provider,
                                        YouTubeInitializationResult error) {
        Toast.makeText(this, "Oh no! " + error.toString(),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onInitializationSuccess(CustomYouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        player.loadVideo(VIDEO);
    }


    private static String getInputQuery() throws IOException {

        String inputQuery = "dogs";

        //System.out.print("Please enter a search term: ");
        //BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        //inputQuery = bReader.readLine();

        if (inputQuery.length() < 1) {
            // Use the string "YouTube Developers Live" as a default.
            inputQuery = "YouTube Developers Live";
        }
        return "dogs";
    }

    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        //System.out.println("\n=============================================================");
        text.setText(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        //System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            text.setText(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                text.setText(text.getText() + " Video Id" + rId.getVideoId());
                text.setText(text.getText() + " Title: " + singleVideo.getSnippet().getTitle());
                text.setText(text.getText() + " Thumbnail: " + thumbnail.getUrl());
                text.setText(text.getText() + "\n-------------------------------------------------------------\n");
            }
        }
    }


}


