package org.example.asteroidapi.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asteroid
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("estimated_diameter")
    private Diameter diameter;

    @JsonProperty("close_approach_data")
    private List<CloseApproachData> approachData;

    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean isHazardous;
}
