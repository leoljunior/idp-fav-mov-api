package com.leoLima.favMovies.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.leoLima.favMovies.model.Movie;

import reactor.core.publisher.Mono;

/**
 * 
 * This class is a service that do requests on external IMDB API for get a movie details
 * 
 * @author leonardoljr
 *
 */
@Service
public class ImdbApiService {
	
	@Value("${api.key}")
	private String apiKey;

	/**
	 * This method get a movie details on external api
	 * 
	 * @param id
	 * @return Movie.class
	 */
	public Movie getMovieByImdbId(String id) {
		Mono<Movie> monoMovie = WebClient.create("http://www.omdbapi.com?apikey=" + apiKey + "&i=" + id)
				.method(HttpMethod.GET)
				.retrieve()
				.bodyToMono(Movie.class);		
		monoMovie.subscribe();		
		Movie movie = monoMovie.block();		
		return movie;		
	}
	
}
