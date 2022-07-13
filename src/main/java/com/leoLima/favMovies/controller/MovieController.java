package com.leoLima.favMovies.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.leoLima.favMovies.dtos.MovieDTO;
import com.leoLima.favMovies.dtos.MovieInputDTO;
import com.leoLima.favMovies.dtos.View;
import com.leoLima.favMovies.model.Movie;
import com.leoLima.favMovies.services.ImdbApiService;
import com.leoLima.favMovies.services.MovieService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;
	
	private ImdbApiService imdbApiService;
	
	private ModelMapper modelMapper;
	
	
	@PostMapping
	@JsonView(View.AllAttributes.class)
	public ResponseEntity<Object> addMovie(@RequestBody MovieInputDTO movieInputDTO) {		
		Movie movie = imdbApiService.getMovieByImdbId(movieInputDTO.getImdbID());		
		if (movie.getImdbID() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
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
	public ResponseEntity<List<MovieDTO>> listAllmovies() {		
		List<Movie> listAllMovies = movieService.listAllMovies();
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
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable Long id) {
		Optional<Movie> movieById = movieService.getMovieById(id);
		if (!movieById.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
		}
		movieService.deleteMovie(movieById.get());
		return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully");
	}
	
}
