package com.leoLima.favMovies.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
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
	public MovieDTO addMovie(@RequestBody MovieInputDTO movieInputDTO) {
		
		Movie movie = imdbApiService.getMovieByImdbId(movieInputDTO.getImdbID());
		
		movieService.addNewMovie(movie, movieInputDTO.getCategory());
		
		MovieDTO movieDto = modelMapper.map(movie, MovieDTO.class);
		
		return movieDto;
	}
	
	@JsonView(View.Two.class)
	@GetMapping
	public List<MovieDTO> movies() {
		
		List<Movie> listAllMovies = movieService.listAllMovies();
		List<MovieDTO> collect = listAllMovies.stream()
			.map(p -> modelMapper.map(p, MovieDTO.class))
			.collect(Collectors.toList());
		return collect;
	}
	
}
