package org.example.asteroidapi.service;
import org.example.asteroidapi.entity.Asteroid;
import org.example.asteroidapi.model.AsteroidInfo;
import org.example.asteroidapi.response.NeoFeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsteroidService
{
    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    private final RestClient restClient;


    @Autowired
    public AsteroidService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public Map<String, List<AsteroidInfo>> getAsteroids(String start, String end) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/feed")
                .queryParam("start_date", start)
                .queryParam("end_date", end)
                .queryParam("api_key", apiKey)
                .toUriString();

        NeoFeedResponse response = restClient.get().uri(uri).retrieve().body(NeoFeedResponse.class);

        assert response != null;

        Map<String, List<AsteroidInfo>> result = new HashMap<>();

        for (Map.Entry<String, List<Asteroid>> entry : response.getAsteroids().entrySet()) {
            String date = entry.getKey();

            List<AsteroidInfo> transformedAsteroids = entry.getValue().stream().map(AsteroidInfo::map).toList();

            result.put(date, transformedAsteroids);
        }
        return result;
    }
}
