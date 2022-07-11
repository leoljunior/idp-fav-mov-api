package com.leoLima.favMovies.services;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.leoLima.favMovies.model.Movie;

import reactor.core.publisher.Mono;

@Service
public class ImdbApiService {
	
	
	final String API_KEY = "775215e7";

	public Movie getMovieByImdbId(String id) {
		Mono<Movie> monoMovie = WebClient.create("http://www.omdbapi.com?apikey=775215e7&i=" + id)
				.method(HttpMethod.GET)
				.retrieve()
				.bodyToMono(Movie.class);
		
		monoMovie.subscribe();		
		
		Movie movie = monoMovie.block();
		
		return movie;		
	}
	
}
