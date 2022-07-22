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

@Slf4j
@Service
@AllArgsConstructor
public class MovieService {

	private MovieRepository movieRepository;
	
	private CategoryService categoryService;
	
	@Transactional
	@CacheEvict(value = "movieList", allEntries = true)
	public Movie addNewMovie(Movie movie, String categoryName) {
		log.info("Cleaning the cache");
		Category category = categoryService.getOrCreateCategory(categoryName.toLowerCase());		
		movie.setCategory(category);		
		return movieRepository.save(movie);
	}
	
	@Cacheable(value = "movieList")
	public List<Movie> listAllMovies() {
		log.info("Getting all movies from API");
		return movieRepository.findAll();
	}

	public Optional<Movie> getMovieById(Long id) {
		return movieRepository.findById(id);
	}
	
	public Optional<Movie> getMovieByImdbId(String imdbId) {
		return movieRepository.findByImdbID(imdbId);
	}
	
	public List<Movie> getMoviesByCategoryName(String category) {
		return movieRepository.findByCategoryName(category.toLowerCase());
	}
	
	@Transactional
	@CacheEvict(value = "movieList", allEntries = true)
	public void deleteMovie(Movie movie) {
		log.info("Cleaning the cache");
		movieRepository.delete(movie);
	}
	
}
