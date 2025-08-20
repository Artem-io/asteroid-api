package org.example.asteroidapi.client;
import lombok.RequiredArgsConstructor;
import org.example.asteroidapi.response.NeoFeedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AsteroidClient
{
    private final RestClient restClient;

    public NeoFeedResponse fetchAsteroids(String uri) {
        return restClient.get().uri(uri).retrieve().body(NeoFeedResponse.class);
    }
}
