package com.leoLima.favMovies.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

/**
 * 
 * @author leonardoljr
 * 
 * Service layer class that handles categories between repository and controller layers
 * This class handles some business rules
 *
 */
@Service
@AllArgsConstructor
public class CategoryService {

	private CategoryRepository categoryRepository;
	
	/**
	 * This method return all categories
	 *  
	 * @return List<Category>
	 */
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	
	/**
	 * This method return a specific category by id
	 * 
	 * @param id
	 * @return Optional<Category>
	 */
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}
	
	/**
	 * This method return a specific category by name
	 * 
	 * @param name
	 * @return Optional<Category>
	 */
	public Optional<Category> getCategoryByName(String name) {
		return categoryRepository.findByName(name.toLowerCase());
	}
	
	
	/**
	 * This method return a category if exists or create a new category
	 * 
	 * @param categoryName
	 * @return Category.class
	 */
	@Transactional
	public Category getOrCreateCategory(String categoryName) {
		Optional<Category> category = categoryRepository.findByName(categoryName);
		if (category.isPresent()) {
			return category.get();
		}
		return categoryRepository.save(new Category(categoryName.toLowerCase()));
	}
	
	/**
	 * This method delete a category. The category isn't deleted if it is a foreign key in another table.
	 * 
	 * @param category
	 */
	@Transactional
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}	
	
}
