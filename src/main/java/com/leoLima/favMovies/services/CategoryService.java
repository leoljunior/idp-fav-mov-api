package com.leoLima.favMovies.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}
	
	public Optional<Category> getCategoryByName(String name) {
		return categoryRepository.findByName(name.toLowerCase());
	}
	
	@Transactional
	public Category getOrCreateCategory(String categoryName) {
		Optional<Category> category = categoryRepository.findByName(categoryName);
		if (category.isPresent()) {
			return category.get();
		}
		return categoryRepository.save(new Category(categoryName.toLowerCase()));
	}
	
	@Transactional
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}	
	
}
