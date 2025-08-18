package org.example.asteroidapi.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloseApproachData
{
    @JsonProperty("relative_velocity")
    private Velocity velocity;

    @JsonProperty("miss_distance")
    private Distance distance;
}
