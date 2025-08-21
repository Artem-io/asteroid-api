package org.example.asteroidapi.controller;
import lombok.RequiredArgsConstructor;
import org.example.asteroidapi.model.AsteroidInfo;
import org.example.asteroidapi.service.AsteroidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/asteroids")
public class AsteroidController
{
    private final AsteroidService asteroidService;

    @GetMapping("/{start}/{end}")
    public ResponseEntity<Map<String, List<AsteroidInfo>>> getAsteroids(@PathVariable String start, @PathVariable String end,
                                                                        @RequestParam(value = "dangerous", required = false) boolean dangerous) {
        return ResponseEntity.ok(asteroidService.getAsteroids(start, end, dangerous));
    }
}
