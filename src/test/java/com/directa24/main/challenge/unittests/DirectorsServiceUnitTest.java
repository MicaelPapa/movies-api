package com.directa24.main.challenge.unittests;

import com.directa24.main.challenge.repository.MovieRepository;
import com.directa24.main.challenge.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorsServiceUnitTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    DirectorService directorService;

    @Test
    void testGetDirectors() {
        Object[] row1 = new Object[]{"Woody Allen", 5L};
        Object[] row2 = new Object[]{"Martin Scorsese", 6L};
        List<Object[]> mockResult = Arrays.asList(row1, row2);

        when(movieRepository.findDirectorsWithMovieCountGreaterThan(4)).thenReturn(mockResult);

        List<String> directors = directorService.getDirectors(4);
        assertEquals(2, directors.size());
        assertEquals("Martin Scorsese", directors.get(0));
        assertEquals("Woody Allen", directors.get(1));
    }
}