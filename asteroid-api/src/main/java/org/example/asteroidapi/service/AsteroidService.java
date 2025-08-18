package org.example.asteroidapi.service;
import lombok.RequiredArgsConstructor;
import org.example.asteroidapi.entity.Asteroid;
import org.example.asteroidapi.model.AsteroidInfo;
import org.example.asteroidapi.model.AsteroidInfoMapper;
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


    private final AsteroidInfoMapper asteroidInfoMapper;

    @Autowired
    public AsteroidService(RestClient.Builder builder, AsteroidInfoMapper asteroidInfoMapper) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
        this.asteroidInfoMapper = asteroidInfoMapper;
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
        return response.getAsteroids().entrySet().stream()
                .collect(HashMap::new, (m, e) ->
                        m.put(e.getKey(), e.getValue().stream().map(asteroidInfoMapper).toList()), HashMap::putAll);

//        Map<String, List<AsteroidInfo>> result = new HashMap<>();
//        for (Map.Entry<String, List<Asteroid>> entry : response.getAsteroids().entrySet()) {
//            String date = entry.getKey();
//            List<AsteroidInfo> transformedAsteroids = new ArrayList<>();
//
//            for (Asteroid asteroid : entry.getValue()) {
//                transformedAsteroids.add(asteroidInfoMapper.apply(asteroid));
//            }
//
//            result.put(date, transformedAsteroids);
//        }

    }
}
