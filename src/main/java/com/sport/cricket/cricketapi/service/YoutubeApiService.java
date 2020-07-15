package com.sport.cricket.cricketapi.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YoutubeApiService {
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static final JsonFactory JSON_FACTORY = new JacksonFactory();



    public String  getYoutubeVideoList(String queryTerm) {

        try {

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("YoutubeVideoInfo")
                    .setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyAAbyvHF-VRZH0pfWOe3wZNk3dt0bGgND8")).build();

            YouTube.Search.List search = youtube.search().list("id,snippet");


            search.setQ(queryTerm);
            search.setType("video");
            search.setMaxResults(1l);

                String pageToken = null;

                if (pageToken != null) {
                    search.setPageToken(pageToken);
                }


                SearchListResponse searchResponse = search.execute();

                List<SearchResult> searchResultList = searchResponse.getItems();
                if (searchResultList != null) {
                    return  searchResultList.get(0).getId().getVideoId();
                }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }




}
