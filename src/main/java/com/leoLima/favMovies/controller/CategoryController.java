package com.leoLima.favMovies.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leoLima.favMovies.dtos.CategoryDTO;
import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.services.CategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

	private CategoryService categoryService;
	
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<Category> listAllCategories = categoryService.listaAllCategories();
		List<CategoryDTO> categoriesList = listAllCategories.stream()
				.map(p -> modelMapper.map(p, CategoryDTO.class))
				.collect(Collectors.toList());		
		return ResponseEntity.status(HttpStatus.OK).body(categoriesList);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCategory(@PathVariable(required = true) Long id) {
		
		try {
			Optional<Category> optCategory = categoryService.getCategoryById(id);
			if (!optCategory.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID: " + id + " not found");			
			}
			categoryService.deleteCategory(optCategory.get());
			return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully");
			
		} catch (DataIntegrityViolationException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Could not possible delete this category, because is a FK");
		}
	}
	
}
