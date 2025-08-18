package org.example.asteroidapi.model;
import org.example.asteroidapi.entity.Asteroid;
import org.example.asteroidapi.entity.DiameterRange;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AsteroidInfoMapper implements Function<Asteroid, AsteroidInfo>
{
    @Override
    public AsteroidInfo apply(Asteroid asteroid) {
        return new AsteroidInfo(
                asteroid.getName(),
                getAvgDiameterKm(asteroid.getDiameter().getKilometers()),
                asteroid.isHazardous(),
                asteroid.getApproachData().getFirst().getVelocity().getKilometersPerSecond(),
                asteroid.getApproachData().getFirst().getDistance().getKilometers()
                );
    }

    private double getAvgDiameterKm(@NonNull DiameterRange range) {
        return (range.getMin() + range.getMax()) / 2;
    }
}
