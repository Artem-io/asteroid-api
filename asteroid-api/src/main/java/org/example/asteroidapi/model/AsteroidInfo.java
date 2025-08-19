package org.example.asteroidapi.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.asteroidapi.entity.Asteroid;
import org.example.asteroidapi.entity.DiameterRange;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsteroidInfo
{
    private String name;
    private double diameter;
    private boolean isHazardous;
    private String velocity;
    private String distance_to_earth;

    public static AsteroidInfo map(Asteroid asteroid) {
        return new AsteroidInfo(
                asteroid.getName(),
                getAvgDiameterKm(asteroid.getDiameter().getKilometers()),
                asteroid.isHazardous(),
                asteroid.getApproachData().getFirst().getVelocity().getKilometersPerSecond(),
                asteroid.getApproachData().getFirst().getDistance().getKilometers()
        );
    }

    private static double getAvgDiameterKm(@NonNull DiameterRange range) {
        return (range.getMin() + range.getMax()) / 2;
    }
}

