package org.example.asteroidapi.service;
import org.example.asteroidapi.client.AsteroidClient;
import org.example.asteroidapi.entity.*;
import org.example.asteroidapi.model.AsteroidInfo;
import org.example.asteroidapi.response.NeoFeedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsteroidServiceTest
{
    @Mock
    private AsteroidClient apiClient;

    private AsteroidService asteroidService;

    @BeforeEach
    void setUp() {
        asteroidService = new AsteroidService(
                apiClient,
                "https://api.nasa.gov/neo/rest/v1",
                "DEMO_KEY");
    }

    @Test
    void shouldTransformAsteroid() {
        //Given
        String start = "2024-09-08";
        Asteroid asteroid = new Asteroid("Asteroid", 1.0, 3.0, "1000", "2000", false);
        NeoFeedResponse response = new NeoFeedResponse(Map.of(start, List.of(asteroid)));

        when(apiClient.fetchAsteroids(anyString())).thenReturn(response);

        //When
        Map<String, List<AsteroidInfo>> result = asteroidService.getAsteroids(start, start, false);
        AsteroidInfo asteroidInfoResult = result.get(start).getFirst();
        AsteroidInfo expected = new AsteroidInfo("Asteroid", 2.0, false, "1000", "2000");

        //Then
        assertEquals(1, result.size());
        assertEquals(expected, asteroidInfoResult);

        verify(apiClient).fetchAsteroids("https://api.nasa.gov/neo/rest/v1/feed?start_date=2024-09-08&end_date=2024-09-08&api_key=DEMO_KEY");
    }

    @Test
    void shouldTransformMultipleAsteroids() {
        //Given
        String start = "2024-09-08";
        String end = "2024-09-12";
        Asteroid asteroid1 = new Asteroid("Asteroid", 1.0, 3.0, "1000", "2000", false);
        Asteroid asteroid2 = new Asteroid("Asteroid 2", 3.0, 3.0, "800", "5000", true);
        Asteroid asteroid3 = new Asteroid("Asteroid 3", 5.0, 3.0, "35000", "69000", true);

        List<Asteroid> asteroids1 = List.of(asteroid1, asteroid3);
        List<Asteroid> asteroids2 = List.of(asteroid2);

        NeoFeedResponse response = new NeoFeedResponse(Map.of(start, asteroids1, end, asteroids2));

        when(apiClient.fetchAsteroids(anyString())).thenReturn(response);

        //When
        Map<String, List<AsteroidInfo>> result = asteroidService.getAsteroids(start, end, false);

        //Then
        assertEquals(2, result.size());

        List<AsteroidInfo> asteroidsForStart = result.get(start);
        assertEquals(2, asteroidsForStart.size());
        assertThat(asteroidsForStart).isEqualTo(List.of(
                new AsteroidInfo("Asteroid", 2.0, false, "1000", "2000"),
                new AsteroidInfo("Asteroid 3", 4.0, true, "35000", "69000")));

        List<AsteroidInfo> asteroidsForEnd = result.get(end);
        assertEquals(1, asteroidsForEnd.size());
        assertThat(asteroidsForEnd).isEqualTo(List.of(
                new AsteroidInfo("Asteroid 2", 3.0, true, "800", "5000")));

        verify(apiClient).fetchAsteroids("https://api.nasa.gov/neo/rest/v1/feed?start_date=2024-09-08&end_date=2024-09-12&api_key=DEMO_KEY");
    }

    @Test
    void shouldGetOnlyDangerousAsteroids() {
        //Given
        String start = "2024-09-08";
        String end = "2024-09-12";
        Asteroid asteroid1 = new Asteroid("Asteroid", 1.0, 3.0, "1000", "2000", true);
        Asteroid asteroid2 = new Asteroid("Asteroid 2", 3.0, 3.0, "800", "5000", false);
        Asteroid asteroid3 = new Asteroid("Asteroid 3", 5.0, 3.0, "35000", "69000", true);

        List<Asteroid> asteroids1 = List.of(asteroid1, asteroid3);
        List<Asteroid> asteroids2 = List.of(asteroid2);

        NeoFeedResponse response = new NeoFeedResponse(Map.of(start, asteroids1, end, asteroids2));

        when(apiClient.fetchAsteroids(anyString())).thenReturn(response);

        //When
        Map<String, List<AsteroidInfo>> result = asteroidService.getAsteroids(start, end, true);

        //Then
        assertEquals(1, result.size());

        List<AsteroidInfo> asteroidsForStart = result.get(start);
        assertEquals(2, asteroidsForStart.size());
        assertThat(asteroidsForStart).isEqualTo(List.of(
                new AsteroidInfo("Asteroid", 2.0, true, "1000", "2000"),
                new AsteroidInfo("Asteroid 3", 4.0, true, "35000", "69000")));

        List<AsteroidInfo> asteroidsForEnd = result.get(end);
        assertNull(asteroidsForEnd);

        verify(apiClient).fetchAsteroids("https://api.nasa.gov/neo/rest/v1/feed?start_date=2024-09-08&end_date=2024-09-12&api_key=DEMO_KEY");
    }

    @Test
    void shouldThrowWhenResponseIsNull() {
        //Given
        when(apiClient.fetchAsteroids(anyString())).thenReturn(null);

        //When & Then
        assertThrows(IllegalStateException.class, () -> asteroidService.getAsteroids("2024-09-08", "2024-09-08", false));
        verifyNoMoreInteractions(apiClient);
    }
}