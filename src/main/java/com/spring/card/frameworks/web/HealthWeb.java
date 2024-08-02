package com.spring.card.frameworks.web;

import com.spring.card.util.health.CustomHealthIndicator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/order/health")
@Tag(name = "Saúde")
public class HealthWeb {
    @Autowired
    private CustomHealthIndicator healthIndicator;

    @GetMapping
    public ResponseEntity<Health> health() {
        return ResponseEntity.ok(healthIndicator.health());
    }
}
