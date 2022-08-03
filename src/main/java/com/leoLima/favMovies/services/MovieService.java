package com.leoLima.favMovies.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.model.Movie;
import com.leoLima.favMovies.repositories.MovieRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author leonardoljr
 * 
 * Service layer class that handles movies between repository and controller layers
 * This class handles some business rules
 *
 */
@Slf4j
@Service
@AllArgsConstructor
public class MovieService {

	private MovieRepository movieRepository;
	
	private CategoryService categoryService;
	
	/**
	 * This method add a new movie on a movie list
	 * 
	 * @param movie
	 * @param categoryName
	 * @return Movie.class
	 */
	@Transactional
	@CacheEvict(value = "movieList", allEntries = true)
	public Movie addNewMovie(Movie movie, String categoryName) {
		log.info("Cleaning the cache");
		Category category = categoryService.getOrCreateCategory(categoryName.toLowerCase());		
		movie.setCategory(category);		
		return movieRepository.save(movie);
	}
	
	/**
	 * This method return a movie list and saves to cache
	 * 	
	 * @return List<Movie>
	 */
	@Cacheable(value = "movieList")
	public List<Movie> listAllMovies() {
		log.info("Getting all movies from API");
		return movieRepository.findAll();
	}

	/**
	 * This method return a specific movie by id
	 * 
	 * @param id
	 * @return Optional<Movie>
	 */
	public Optional<Movie> getMovieById(Long id) {
		return movieRepository.findById(id);
	}
	
	/**
	 * This method return a specific movie by IMDbId
	 * 
	 * @param imdbId
	 * @return Optional<Movie>
	 */
	public Optional<Movie> getMovieByImdbId(String imdbId) {
		return movieRepository.findByImdbID(imdbId);
	}
	
	/**
	 * This method return a movie list by category name
	 * 
	 * @param category
	 * @return List<Movie>
	 */
	public List<Movie> getMoviesByCategoryName(String category) {
		return movieRepository.findByCategoryName(category.toLowerCase());
	}
	
	/**
	 * This method deletes a movie and when this method is called it clears the cache
	 * 
	 * @param movie
	 */
	@Transactional
	@CacheEvict(value = "movieList", allEntries = true)
	public void deleteMovie(Movie movie) {
		log.info("Cleaning the cache");
		movieRepository.delete(movie);
	}
	
}
