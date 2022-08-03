package com.leoLima.favMovies.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoLima.favMovies.model.Category;

/**
 * 
 * Inteface that represents the Category repository and extends JPA Repository
 * 
 * @author leonardoljr
 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Optional<Category> findByName(String categoryName);
	
}
