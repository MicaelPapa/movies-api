package com.directa24.main.challenge.service;

import com.directa24.main.challenge.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    private final MovieRepository movieRepository;

    public DirectorService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<String> getDirectors(int threshold) {
        var results = movieRepository.findDirectorsWithMovieCountGreaterThan(threshold);
        return results.stream()
                .map(row -> (String) row[0])
                .sorted()
                .collect(Collectors.toList());
    }
}
