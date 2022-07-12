package com.leoLima.favMovies.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.model.Movie;
import com.leoLima.favMovies.repositories.MovieRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService {

	private MovieRepository movieRepository;
	
	private CategoryService categoryService;
	
	@Transactional
	public Movie addNewMovie(Movie movie, String categoryName) {
		Category category = categoryService.getOrCreateCategory(categoryName);		
		movie.setCategory(category);		
		return movieRepository.save(movie);
	}
	
	public List<Movie> listAllMovies() {
		return movieRepository.findAll();
	}

	public Optional<Movie> getMovieById(Long id) {
		return movieRepository.findById(id);
	}
	
	public Optional<Movie> getMovieByImdbId(String imdbId) {
		return movieRepository.findByImdbID(imdbId);
	}
	
	@Transactional
	public void deleteMovie(Movie movie) {
		movieRepository.delete(movie);
	}
	
}
