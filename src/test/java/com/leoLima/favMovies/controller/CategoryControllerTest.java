package com.leoLima.favMovies.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.leoLima.favMovies.dtos.CategoryDTO;
import com.leoLima.favMovies.dtos.MovieDTO;

@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

	@Autowired
	private WebTestClient webTestClient;
		
	@Test
	@Order(2)
	void whenGetCategoriesReceiveARequest_thenReturnCategoryDTOList() {
		List<CategoryDTO> categoryDTOs = webTestClient.get()
		.uri("/categories")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(CategoryDTO.class)
		.returnResult().getResponseBody();
		
		assertEquals(3, categoryDTOs.size());
		assertEquals("horror", categoryDTOs.get(0).getName());
		assertEquals("action", categoryDTOs.get(1).getName());
		assertEquals("comedy", categoryDTOs.get(2).getName());
	}
	
	@Test
	@Order(6)
	void givenAInexistentCategory_whenDeleteCategoriesReceiveARequest_thenCategoryIsNotDeleted() {
		         webTestClient.delete()
				.uri("/categories/10")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(String.class)
				.isEqualTo("Category with ID: 10 not found");
		
	}
	
	@Test
	@Order(7)
	void givenAValidCategory_whenDeleteCategoriesReceiveARequest_thenCategoryIsDeleted() {
		webTestClient.delete()
		.uri("/categories/3")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.isEqualTo("Category deleted successfully");
		
	}
	
	@Test
	@Order(7)
	void givenACategoryWithHasARelationshipWithAnotherTable_whenDeleteCategoriesReceiveARequest_thenCategoryIsNotDeleted() {
		webTestClient.delete()
		.uri("/categories/2")
		.exchange()
		.expectStatus().isEqualTo(409)
		.expectBody(String.class)
		.isEqualTo("Could not possible delete this category, because is a FK");
		
	}
	

}
