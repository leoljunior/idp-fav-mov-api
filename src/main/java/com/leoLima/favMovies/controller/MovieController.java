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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

/**
 * 
 * @author leonardoljr
 * 
 * Controller layer class that handles movies
 *
 */

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;
	
	private ImdbApiService imdbApiService;
	
	private CategoryService categoryService;
	
	private ModelMapper modelMapper;
	
	/**
	 * 
	 * This method is used to added a new movie on a movie list by category
	 * 
	 * @param movieInputDTO
	 * @return MovieDTO.class
	 */	
	@ApiOperation(value = "Add a new movie on favorite list by category")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Movie created.", response = MovieDTO.class),
			@ApiResponse(code = 404, message = "Movie not found on IMDb API"),
			@ApiResponse(code = 409, message = "Movie is already on your favorites"),
	})
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
	
	/**
	 * 
	 * This method return all movies or all movies by category if a category parameter is inserted 
	 * 
	 * @param category
	 * @return List<MovieDTO>
	 */
	@ApiOperation(value = "List all movies or list all movies by category param")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Movie list found successfully", response = MovieDTO.class),
	})
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
	
	/**
	 * 
	 * This method return a specific movies by id with all its attributes
	 * 
	 * @param id
	 * @return MovieDTO.class
	 */
	@ApiOperation(value = "List a movie by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Movie found successfully", response = MovieDTO.class),
			@ApiResponse(code = 404, message = "Movie not found on your favorites"),
	})
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
	
	/**
	 * 
	 * This method is used to delete a movie from list
	 * 
	 * @param id
	 * @return HttpStatus.OK
	 */
	@ApiOperation(value = "Delete a movie by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Movie deleted successfully"),
			@ApiResponse(code = 404, message = "Movie not found"),
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable Long id) {
		Optional<Movie> movieById = movieService.getMovieById(id);
		if (!movieById.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID: "+ id +" not found");
		}
		movieService.deleteMovie(movieById.get());
		return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully");
	}	
	
	/**
	 * 
	 * This method is used to update a movie category
	 * 
	 * @param id
	 * @param categoryInputDTO
	 * @return MovieDTO.class
	 */
	@ApiOperation(value = "Update a movie by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Movie updated successfully"),
			@ApiResponse(code = 404, message = "Movie or category not found", response = Void.class),
	})
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
