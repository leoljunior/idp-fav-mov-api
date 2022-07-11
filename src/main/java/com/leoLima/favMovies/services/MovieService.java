package com.leoLima.favMovies.services;

import java.util.List;

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
	
	public Movie addNewMovie(Movie movie, String categoryName) {
		Category category = categoryService.getOrCreateCategory(categoryName);		
		movie.setCategory(category);		
		return movieRepository.save(movie);
	}
	
	public List<Movie> listAllMovies() {
		return movieRepository.findAll();
	}
	
}
