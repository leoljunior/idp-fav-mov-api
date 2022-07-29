package com.leoLima.favMovies.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.leoLima.favMovies.dtos.MovieDTO;
import com.leoLima.favMovies.dtos.MovieInputDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class MovieControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
//	@Test
//	@Order(1)
//	void whenGetMoviesReceiveARequest_thenReturnMoviesList() {
//		webTestClient.get()
//		.uri("/movies")
//		.exchange()
//			.expectHeader().contentType(MediaType.APPLICATION_JSON)
//			.expectStatus().isOk()
//			.expectBodyList(Movie.class)
//			.hasSize(10);
//	}
	
	@Test
	@Order(2)
	void whenGetMoviesReceiveARequest_thenReturnMovieDTOList() {
		List<MovieDTO> movieDTOList = webTestClient.get()
		.uri("/movies")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(MovieDTO.class)
		.returnResult().getResponseBody();
		
		assertEquals(10, movieDTOList.size());
		assertEquals(1, movieDTOList.get(0).getId());
		assertEquals("The Dark Knight", movieDTOList.get(0).getTitle());
		assertEquals("18 Jul 2008", movieDTOList.get(0).getReleased());
		assertEquals("movie", movieDTOList.get(0).getType());
		assertEquals("action", movieDTOList.get(0).getCategory());
	}
	@Test
	@Order(3)
	void givenAValidId_whenGetMoviesReceiveARequest_thenReturnSpecificMovieDTO() {
		MovieDTO movieDTO = webTestClient.get()
				.uri("/movies/6")
				.exchange()
				.expectStatus().isOk()
				.expectBody(MovieDTO.class)
				.returnResult().getResponseBody();
				
		assertEquals(6, movieDTO.getId());
		assertEquals("tt12327578", movieDTO.getImdbID());
		assertEquals("Star Trek: Strange New Worlds", movieDTO.getTitle());
		assertEquals("TV-PG", movieDTO.getRated());
		assertEquals("05 May 2022", movieDTO.getReleased());
		assertEquals("n/a", movieDTO.getRuntime());
		assertEquals("Action, Adventure, Sci-Fi", movieDTO.getGenre());
		assertEquals("Akiva Goldsman...", movieDTO.getDirector());
		assertEquals("Akiva Goldsman, Alex Kurtzman, Jenny Lumet", movieDTO.getWriter());
		assertEquals("Melissa Navia, Anson Mount, Ethan Peck", movieDTO.getActors());
		assertEquals("A prequel to Star Trek: The Original Series, the show will follow the crew of the USS Enterprise under Captain Christopher Pike.", movieDTO.getPlot());
		assertEquals("English", movieDTO.getLanguage());
		assertEquals("United States", movieDTO.getCountry());
		assertEquals("n/a", movieDTO.getAwards());
		assertEquals("https://m.media-amazon.com/images/M/MV5BYWNlYmZkZjQtNjU5OS00YTNkLWJmOTEtYmZiMmUwZGI3NTM3XkEyXkFqcGdeQXVyMTM2NTIwMDIw._V1_SX300.jpg", movieDTO.getPoster());
		assertEquals("8.2", movieDTO.getImdbRating());
		assertEquals("17,758", movieDTO.getImdbVotes());
		assertEquals("series", movieDTO.getType());
		assertEquals("action", movieDTO.getCategory());
	}
	@Test
	@Order(4)
	void givenAInvalidId_whenGetMoviesReceiveARequest_thenIsNotReturnMovieDTO() {
				webTestClient.get()
				.uri("/movies/60")
				.exchange()
				.expectBody(String.class)
				.isEqualTo("Movie with ID: 60 not found");
	}

//	@Test
//	@Order(2)
//	void dado2() {
//		webTestClient.get()
//		.uri("/movies")
//		.exchange()
//			.expectStatus().isOk()
//			.expectBodyList(Movie.class)
//			.hasSize(10);
//	}
	
//	@Test
//	@Order(3)
//	void dadooooooo() {
//				webTestClient.get()
//		    	.uri("/movies")
//		    	.exchange()
//		    	.expectStatus().isOk()
//		    	.expectBody()
//		    	.jsonPath("$[0].id").isEqualTo("1")
//		    	.jsonPath("$[0].title").isEqualTo("The Dark Knight")
//		    	.jsonPath("$[0].released").isEqualTo("18 Jul 2008")
//		    	.jsonPath("$[0].type").isEqualTo("movie")
//		    	.jsonPath("$[0].category").isEqualTo("action")
//		    	
//				.jsonPath("$[5].id").isEqualTo("6")
//				.jsonPath("$[5].title").isEqualTo("Star Trek: Strange New Worlds")
//				.jsonPath("$[5].released").isEqualTo("05 May 2022")
//				.jsonPath("$[5].type").isEqualTo("series")
//				.jsonPath("$[5].category").isEqualTo("action");
//				
//	}

	
	@Test
	@Order(4)
	void whenGetMoviesReceiveARequestWithHorrorCategoryParam_thenReturnSpecificMovieDTOList() {
		webTestClient.get()
		.uri("/movies?category=horror")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(MovieDTO.class)
		.value(response -> {
			assertEquals(4, response.size());
			response.forEach(movie -> assertEquals("horror", movie.getCategory()));
		});
	}
	
	@Test
	@Order(5)
	void whenGetMoviesReceiveARequestWithActionCategoryParam_thenReturnSpecificMovieDTOList() {
		webTestClient.get()
		.uri("/movies?category=action")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(MovieDTO.class)
		.value(response -> {
			assertEquals(6, response.size());
			response.forEach(movie -> assertEquals("action", movie.getCategory()));
		});
	}
	
	
	
	@Test
	@Order(10)
	void givenAValidId_whenPutMoviesReceiveARequestWithCategoryParam_thenReturnMovieDTOWithUpdatedCategory() {
		Map<String, String> category = new HashMap<>();
		category.put("category", "horror");
		MovieDTO updatedMovieDTO = webTestClient.put()
		.uri("/movies/1")
		.body(BodyInserters.fromValue(category))
		.exchange()
		.expectStatus().isOk()
		.expectBody(MovieDTO.class)
		.returnResult().getResponseBody();
		
		assertEquals(1, updatedMovieDTO.getId());
		assertEquals("The Dark Knight", updatedMovieDTO.getTitle());
		assertEquals("18 Jul 2008", updatedMovieDTO.getReleased());
		assertEquals("movie", updatedMovieDTO.getType());
		assertEquals("horror", updatedMovieDTO.getCategory());
	}
	
	@Test
	@Order(11)
	void givenAValidId_whenPutMoviesReceiveARequestWithInexistentCategoryParam_thenMovieCategoryIsNotUpdated() {
		Map<String, String> category = new HashMap<>();
		category.put("category", "comedy");
				webTestClient.put()
				.uri("/movies/1")
				.body(BodyInserters.fromValue(category))
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(String.class)
				.isEqualTo("Category: comedy not found");
	}
	
	@Test
	@Order(12)
	void givenAInvalidId_whenPutMoviesReceiveARequestWithACategoryParam_thenMovieCategoryIsNotUpdated() {
		Map<String, String> category = new HashMap<>();
		category.put("category", "horror");
		
		webTestClient.put()
		.uri("/movies/100")
		.body(BodyInserters.fromValue(category))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody(String.class)
		.isEqualTo("Movie with ID: 100 not found");
	}
	
	
	@Test
	@Order(14)
	void givenAValidImdbId_whenPostMovieReceiveARequest_thenReturnANewMovieDTO() {
		MovieInputDTO movieInputDTO = new MovieInputDTO();
		movieInputDTO.setCategory("comedy");
		movieInputDTO.setImdbID("tt0898266");
		
		MovieDTO createdMovieDTO = webTestClient.post()
		.uri("/movies")
		.body(BodyInserters.fromValue(movieInputDTO))
		.exchange()
		.expectStatus().isCreated()
		.expectBody(MovieDTO.class)
		.returnResult().getResponseBody();
		
		assertEquals("tt0898266", createdMovieDTO.getImdbID());
		assertEquals("comedy", createdMovieDTO.getCategory());
		assertEquals("The Big Bang Theory", createdMovieDTO.getTitle());
		assertEquals("series", createdMovieDTO.getType());
		assertEquals("12", createdMovieDTO.getTotalSeasons());		
	}
	
	@Test
	@Order(15)
	void givenAImdbIdThatAlreadyWasCreated_whenPostMovieReceiveARequest_thenReturnConflictStatus() {
		MovieInputDTO movieInputDTO = new MovieInputDTO();
		movieInputDTO.setCategory("comedy");
		movieInputDTO.setImdbID("tt0898266");
		
				webTestClient.post()
				.uri("/movies")
				.body(BodyInserters.fromValue(movieInputDTO))
				.exchange()
				.expectStatus().isEqualTo(409)
				.expectBody(String.class)
				.isEqualTo("This movie is already in your favorites");
	}
	
	@Test
	@Order(16)
	void givenAInvalidImdbId_whenPostMovieReceiveARequest_thenIsNotReturnANewMovieDTO() {
		MovieInputDTO movieInputDTO = new MovieInputDTO();
		movieInputDTO.setCategory("comedy");
		movieInputDTO.setImdbID("0898266");
		
		webTestClient.post()
		.uri("/movies")
		.body(BodyInserters.fromValue(movieInputDTO))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody(String.class)
		.isEqualTo("Movie with IMDb ID: 0898266 not found");
	}
	
	
	
	@Test
	@Order(19)
	void givenAValidId_whenDeleteMoviesReceiveARequest_thenMovieIsNotDeleted() {
		webTestClient.delete()
		.uri("/movies/100")
		.exchange()
		.expectStatus().isNotFound()
		.expectBody(String.class)
		.isEqualTo("Movie with ID: 100 not found");
	}
	@Test
	@Order(20)
	void givenAValidId_whenDeleteMoviesReceiveARequest_thenMovieIsSucessfullyDeleted() {
		webTestClient.delete()
		.uri("/movies/10")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.isEqualTo("Movie deleted successfully");
	}

}