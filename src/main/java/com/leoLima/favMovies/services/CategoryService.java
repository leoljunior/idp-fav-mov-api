package com.leoLima.favMovies.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

	private CategoryRepository categoryRepository;
	
	public List<Category> listaAllCategories() {
		return categoryRepository.findAll();
	}
	
	public Category getOrCreateCategory(String categoryName) {
		Optional<Category> category = categoryRepository.findByName(categoryName);
		if (category.isPresent()) {
			return category.get();
		}
		return categoryRepository.save(new Category(categoryName));
	}
	
}
