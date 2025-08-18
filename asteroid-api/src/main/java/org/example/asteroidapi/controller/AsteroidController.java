package org.example.asteroidapi.controller;
import lombok.RequiredArgsConstructor;
import org.example.asteroidapi.service.AsteroidService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/asteroids")
public class AsteroidController
{
    private final AsteroidService asteroidService;

    @GetMapping("/{start}/{end}")
    public void getAsteroids(@PathVariable String start, @PathVariable String end) {
        asteroidService.getAsteroids(start, end);
    }
}
