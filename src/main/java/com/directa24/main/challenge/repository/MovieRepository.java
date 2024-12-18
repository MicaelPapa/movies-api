package com.directa24.main.challenge.repository;

import com.directa24.main.challenge.model.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("SELECT m.director.name AS directorName, COUNT(m) AS movieCount " +
            "FROM MovieEntity m " +
            "GROUP BY m.director.name " +
            "HAVING COUNT(m) > :threshold " +
            "ORDER BY m.director.name ASC")
    List<Object[]> findDirectorsWithMovieCountGreaterThan(long threshold);

}
