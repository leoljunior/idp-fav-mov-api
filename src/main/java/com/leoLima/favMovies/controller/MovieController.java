package com.leoLima.favMovies.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.leoLima.favMovies.dtos.MovieCategoryInputDTO;
import com.leoLima.favMovies.dtos.MovieDTO;
import com.leoLima.favMovies.dtos.MovieInputDTO;
import com.leoLima.favMovies.dtos.View;
import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.model.Movie;
import com.leoLima.favMovies.services.CategoryService;
import com.leoLima.favMovies.services.ImdbApiService;
import com.leoLima.favMovies.services.MovieService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;
	
	private ImdbApiService imdbApiService;
	
	private CategoryService categoryService;
	
	private ModelMapper modelMapper;
	
	
	@PostMapping
	@JsonView(View.AllAttributes.class)
	public ResponseEntity<Object> addMovieOnFavorites(@RequestBody @Valid MovieInputDTO movieInputDTO) {		
		Movie movie = imdbApiService.getMovieByImdbId(movieInputDTO.getImdbID());		
		if (movie.getImdbID() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with IMDb ID: " + movieInputDTO.getImdbID() +" not found");
		}
		
		Optional<Movie> movieByImdbId = movieService.getMovieByImdbId(movie.getImdbID());
		if (movieByImdbId.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("This movie is already in your favorites");			
		}
		
		movieService.addNewMovie(movie, movieInputDTO.getCategory());		
		MovieDTO movieDto = modelMapper.map(movie, MovieDTO.class);		
		return ResponseEntity.status(HttpStatus.CREATED).body(movieDto);
	}
	
	@GetMapping
	@JsonView(View.SomeAttributes.class)
	public ResponseEntity<List<MovieDTO>> listAllMovies(@RequestParam(required = false) Optional<String> category) {
		if (!category.isPresent()) {
			List<Movie> listAllMovies = movieService.listAllMovies();
			List<MovieDTO> movieList = listAllMovies.stream()
				.map(p -> modelMapper.map(p, MovieDTO.class))
				.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(movieList);
		}
		List<Movie> listAllMovies = movieService.getMoviesByCategoryName(category.get());
		List<MovieDTO> movieList = listAllMovies.stream()
				.map(p -> modelMapper.map(p, MovieDTO.class))
				.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(movieList);		
	}
	
	@GetMapping("/{id}")
	@JsonView(View.AllAttributes.class)
	public ResponseEntity<Object> getMovieById(@PathVariable Long id) {
		Optional<Movie> movieById = movieService.getMovieById(id);
		if (movieById.isPresent()) {
			MovieDTO movieDto = modelMapper.map(movieById, MovieDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(movieDto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID: "+ id +" not found");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable Long id) {
		Optional<Movie> movieById = movieService.getMovieById(id);
		if (!movieById.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID: "+ id +" not found");
		}
		movieService.deleteMovie(movieById.get());
		return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully");
	}
	
	@PutMapping("/{id}")
	@JsonView(View.SomeAttributes.class)
	public ResponseEntity<Object> updateMovieCategory(@PathVariable Long id, @RequestBody MovieCategoryInputDTO categoryInputDTO) {
		Optional<Movie> optMovie = movieService.getMovieById(id);
		Optional<Category> optCategory = categoryService.getCategoryByName(categoryInputDTO.getCategory());
		if (!optMovie.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID: "+ id +" not found");
		}
		if (!optCategory.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category: "+ categoryInputDTO.getCategory() +" not found");
		}
		movieService.addNewMovie(optMovie.get(), categoryInputDTO.getCategory());
		MovieDTO movieDto = modelMapper.map(optMovie.get(), MovieDTO.class);		
		return ResponseEntity.status(HttpStatus.OK).body(movieDto);
	}
}
