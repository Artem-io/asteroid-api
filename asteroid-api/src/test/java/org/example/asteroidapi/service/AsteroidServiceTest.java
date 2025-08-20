package org.example.asteroidapi.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.asteroidapi.entity.*;
import org.example.asteroidapi.model.AsteroidInfo;
import org.example.asteroidapi.response.NeoFeedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsteroidServiceTest {
    
    @Mock
    private RestClient.Builder restClientBuilder;
    
    @Mock
    private RestClient restClient;
    
    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    
    @Mock
    private RestClient.ResponseSpec responseSpec;
    
    private AsteroidService asteroidService;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        
        // Mock the RestClient.Builder chain
        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
        
        // Mock the RestClient call chain
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        
        asteroidService = new AsteroidService(restClientBuilder, "https://api.nasa.gov/neo/rest/v1", "DEMO_KEY");
    }
    
    @Test
    void shouldGetAsteroids() {
        //Given
        String start = "2024-09-08";
        
        DiameterRange diameterRange = new DiameterRange(1.0, 2.0);
        Diameter diameter = new Diameter(diameterRange);
        Velocity velocity = new Velocity("1000");
        Distance distance = new Distance("10000");
        CloseApproachData closeApproachData = new CloseApproachData(velocity, distance);
        Asteroid asteroid = new Asteroid("Asteroid", diameter, List.of(closeApproachData), false);
        
        NeoFeedResponse response = new NeoFeedResponse(Map.of(start, List.of(asteroid)));
        
        // Mock the response
        when(responseSpec.body(NeoFeedResponse.class)).thenReturn(response);
        
        //When
        Map<String, List<AsteroidInfo>> result = asteroidService.getAsteroids(start, start);
        
        //Then
    }
}