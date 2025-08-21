package org.example.asteroidapi.service;
import org.example.asteroidapi.client.AsteroidClient;
import org.example.asteroidapi.entity.Asteroid;
import org.example.asteroidapi.model.AsteroidInfo;
import org.example.asteroidapi.response.NeoFeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsteroidService
{
    private final String apiKey;
    private final String baseUrl;
    private final AsteroidClient apiClient;

    @Autowired
    public AsteroidService(AsteroidClient apiClient, @Value("${base.url}") String baseUrl, @Value("${api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.apiClient = apiClient;
    }

    public Map<String, List<AsteroidInfo>> getAsteroids(String start, String end, boolean dangerous) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/feed")
                .queryParam("start_date", start)
                .queryParam("end_date", end)
                .queryParam("api_key", apiKey)
                .toUriString();

        NeoFeedResponse response = apiClient.fetchAsteroids(uri);
        if (response == null) throw new IllegalStateException("Response is null");
        Map<String, List<AsteroidInfo>> result = new HashMap<>();

        for (Map.Entry<String, List<Asteroid>> entry : response.getAsteroids().entrySet())
        {
            String date = entry.getKey();

            List<AsteroidInfo> transformedAsteroids = dangerous?
                    entry.getValue().stream().filter(Asteroid::isHazardous).map(AsteroidInfo::map).toList() :
                    entry.getValue().stream().map(AsteroidInfo::map).toList();

            if(!transformedAsteroids.isEmpty()) result.put(date, transformedAsteroids);
        }
        return result;
    }
}