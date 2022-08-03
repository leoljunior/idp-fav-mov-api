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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author leonardoljr
 * 
 * Controller layer class that handles categories
 *
 */

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

	private CategoryService categoryService;
	
	private ModelMapper modelMapper;
	
	/**
	 *
	 * This method returns a list with all categories
	 * 
	 * @return CategoryDTO.class
	 * 
	 */	
	@ApiOperation(value = "List all categories")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Category list found successfully", response = CategoryDTO.class),
	})	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<Category> listAllCategories = categoryService.getAllCategories();
		List<CategoryDTO> categoriesList = listAllCategories.stream()
				.map(p -> modelMapper.map(p, CategoryDTO.class))
				.collect(Collectors.toList());		
		return ResponseEntity.status(HttpStatus.OK).body(categoriesList);
	}
	
	/**
	 * This method is used to delete a category by id. The category isn't deleted if it is a foreign key in another table
	 * 
	 * @param id
	 * 
	 * @return HTTP.Status.ok
	 *	  	 
	 */	
	@ApiOperation(value = "Delete a category by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Category deleted successfully"),
			@ApiResponse(code = 404, message = "Category not found"),
			@ApiResponse(code = 409, message = "Category can be not deleted, because is a FK"),
	})
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
