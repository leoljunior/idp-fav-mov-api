package com.leoLima.favMovies.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoLima.favMovies.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Optional<Movie> findByImdbID(String imdbId);

}
