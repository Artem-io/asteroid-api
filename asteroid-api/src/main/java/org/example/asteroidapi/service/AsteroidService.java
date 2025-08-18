package org.example.asteroidapi.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AsteroidService
{
    @Value("${api.key}")
    private String apiKey;

    @Value("${base.url}")
    private String baseUrl;

    private final RestClient restClient;

    public AsteroidService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public void getAsteroids(String start, String end) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/feed")
                .queryParam("start_date", start)
                .queryParam("end_date", end)
                .queryParam("api_key", apiKey)
                .toUriString();

        System.out.println(restClient.get().uri(uri).retrieve().body(String.class));
    }
}
