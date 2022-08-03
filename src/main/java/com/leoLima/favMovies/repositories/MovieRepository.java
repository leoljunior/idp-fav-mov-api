package com.leoLima.favMovies.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoLima.favMovies.model.Movie;

/**
 * 
 * Inteface that represents the Movie repository and extends JPA Repository
 * 
 * @author leonardoljr
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Optional<Movie> findByImdbID(String imdbId);

	List<Movie> findByCategoryName(String category);

}
