package org.example.asteroidapi.model;

public record AsteroidInfo
     (String name,
     double diameter,
     boolean isHazardous,
     String velocity,
     String distance_to_earth) {}

