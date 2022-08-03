package com.leoLima.favMovies.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.repositories.CategoryRepository;

class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.categoryService = new CategoryService(categoryRepository);
	}

	@Test
	void whenGetCategoriesReceiveARequest_thenReturnCategoryDTOList() {
		Category category = createACategory();
		
		when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
		
		List<Category> categoriesList = categoryService.getAllCategories();
		
		assertNotNull(categoriesList);
		assertEquals(1, categoriesList.size());
		assertEquals(1, categoriesList.get(0).getId());		
		assertEquals("comedy", categoriesList.get(0).getName());		
	}
	
	@Test
	void givenAnExistingCategory_whenGetOrCreateCategoryReceiveARequest_thenReturnThisCategory() {
		Category createACategory = createACategory();
		
		when(categoryRepository.findByName("comedy")).thenReturn(Optional.of(createACategory));
	
		Category category = categoryService.getOrCreateCategory("comedy");
		
		assertEquals(1, category.getId());
		assertEquals("comedy", category.getName());	
	}
	
	@Test
	void givenANewCategory_whenGetOrCreateCategoryReceiveARequest_thenReturnANewCategory() {
		Category createACategory = createACategory();
		createACategory.setId(null);
		
		when(categoryRepository.save(createACategory)).thenReturn(createACategory);		
		Category category = categoryService.getOrCreateCategory("comedy");
		
		assertEquals("comedy", category.getName());	
	}
	
	@Test
	void givenCategoryId_whenGetCategoryByIdReceiveARequest_thenReturnThisCategory() {
		Category createACategory = createACategory();
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(createACategory));		
		Optional<Category> category = categoryService.getCategoryById(1L);
		
		assertEquals(1, category.get().getId());	
		assertEquals("comedy", category.get().getName());	
	}
	
	@Test
	void givenCategoryName_whenGetCategoryByNameReceiveARequest_thenReturnThisCategory() {
		Category createACategory = createACategory();
		
		when(categoryRepository.findByName("comedy")).thenReturn(Optional.of(createACategory));		
		Optional<Category> category = categoryService.getCategoryByName("comedy");
		
		assertEquals(1, category.get().getId());	
		assertEquals("comedy", category.get().getName());	
	}
	
	@Test
	void givenValidCategoryId_whenDeleteCategoryReceiveARequest_thenDeleteCategory() {
		Category createACategory = createACategory();
		
		categoryService.deleteCategory(createACategory);

		Mockito.verify(categoryRepository).delete(createACategory);
	}
		
	
	
	private Category createACategory() {
		return new Category(1L, "comedy");
	}
	
}
