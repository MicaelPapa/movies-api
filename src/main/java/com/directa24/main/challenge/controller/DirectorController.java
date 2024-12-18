package com.directa24.main.challenge.controller;

import com.directa24.main.challenge.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies-api")
public class DirectorController {

    private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/directors")
    @Operation(
            summary = "Get directors exceeding threshold",
            description = "Returns a JSON object with a list of directors who have directed more movies than the specified threshold. If the threshold is not provided is 0"
    )
    public ResponseEntity<Map<String, List<String>>> getDirectors(@RequestParam(defaultValue = "0")  int threshold) {
        logger.debug("Loading directors...");
        List<String> directors = directorService.getDirectors(threshold);
        Map<String, List<String>> response = new HashMap<>();
        response.put("directors", directors);
        return ResponseEntity.ok(response);
    }
}
