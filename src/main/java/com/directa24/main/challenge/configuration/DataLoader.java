package com.directa24.main.challenge.configuration;

import com.directa24.main.challenge.model.Movie;
import com.directa24.main.challenge.model.MoviePageResponse;
import com.directa24.main.challenge.model.entity.DirectorEntity;
import com.directa24.main.challenge.model.entity.MovieEntity;
import com.directa24.main.challenge.repository.DirectorRepository;
import com.directa24.main.challenge.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final RestTemplate restTemplate;
    @Value("${movies.api.baseurl}")
    private String baseUrl;

    public DataLoader(MovieRepository movieRepository, DirectorRepository directorRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            logger.debug("Loading movies from external API...");
            loadMoviesFromExternalAPI();
        } catch (Exception e) {
            logger.error("Failed to load movies from external API", e);
        }
    }

    private void loadMoviesFromExternalAPI() {
        int currentPage = 1;

        MoviePageResponse firstPage = restTemplate.getForObject(baseUrl + currentPage, MoviePageResponse.class);
        if (firstPage == null) return;

        int totalPages = firstPage.getTotalPages();

        saveMovies(firstPage.getData());

        for (int page = 2; page <= totalPages; page++) {
            MoviePageResponse response = restTemplate.getForObject(baseUrl + page, MoviePageResponse.class);
            if (response != null && response.getData() != null) {
                saveMovies(response.getData());
            }
        }
    }

    private void saveMovies(List<Movie> moviesDto) {
        List<MovieEntity> movies = moviesDto.stream().map(dto -> {
            DirectorEntity directorEntity = directorRepository.findByName(dto.getDirector())
                    .orElseGet(() -> {
                        DirectorEntity newDirector = new DirectorEntity();
                        newDirector.setName(dto.getDirector());
                        return directorRepository.save(newDirector);
                    });

            MovieEntity m = new MovieEntity();
            m.setTitle(dto.getTitle());
            m.setReleaseYear(Integer.parseInt(dto.getYear()));
            m.setDirector(directorEntity);
            return m;
        }).collect(Collectors.toList());

        movieRepository.saveAll(movies);
    }
}