package com.leoLima.favMovies.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.leoLima.favMovies.dtos.MovieDTO;
import com.leoLima.favMovies.model.Movie;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class MovieControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	@Order(1)
	void test() {
		webTestClient.get()
		.uri("/movies")
		.exchange()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectStatus().isOk();
	}

	@Test
	@Order(2)
	void dado2() {
		webTestClient.get()
		.uri("/movies")
		.exchange()
			.expectStatus().isOk()
			.expectBodyList(Movie.class)
			.hasSize(10);
	}
	
	@Test
	@Order(3)
	void dadooooooo() {
				webTestClient.get()
		    	.uri("/movies")
		    	.exchange()
		    	.expectStatus().isOk()
		    	.expectBody()
		    	.jsonPath("$[0].id").isEqualTo("1")
		    	.jsonPath("$[0].title").isEqualTo("The Dark Knight")
		    	.jsonPath("$[0].released").isEqualTo("18 Jul 2008")
		    	.jsonPath("$[0].type").isEqualTo("movie")
		    	.jsonPath("$[0].category").isEqualTo("action")
		    	
				.jsonPath("$[5].id").isEqualTo("6")
				.jsonPath("$[5].title").isEqualTo("Star Trek: Strange New Worlds")
				.jsonPath("$[5].released").isEqualTo("05 May 2022")
				.jsonPath("$[5].type").isEqualTo("series")
				.jsonPath("$[5].category").isEqualTo("action");
				
	}

	
	@Test
	@Order(4)
	void dadooooyrtyrtooooouytiyu() {
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
	void dadooooyrtyrtopyoiuoooouytiyu() {
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
	@Order(20)
	void xxxx() {
		webTestClient.delete()
		.uri("/movies/10")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.isEqualTo("Movie deleted successfully");
	}

}
