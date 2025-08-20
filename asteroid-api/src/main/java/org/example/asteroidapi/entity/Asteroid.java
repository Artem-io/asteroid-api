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

    public Asteroid(String name, double min, double max, String velocity, String distance, boolean isHazardous) {
        this.name = name;
        this.diameter = new Diameter(new DiameterRange(min, max));
        this.approachData = List.of(new CloseApproachData(new Velocity(velocity), new Distance(distance)));
        this.isHazardous = isHazardous;
    }
}
