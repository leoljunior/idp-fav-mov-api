package com.leoLima.favMovies.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leoLima.favMovies.dtos.MovieDTO;
import com.leoLima.favMovies.model.Movie;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		
		modelMapper.createTypeMap(Movie.class, MovieDTO.class)
			.<String>addMapping(src -> src.getCategory().getName(),
					(dest, value) -> dest.setCategory(value));
		
		
		
		return modelMapper;
	}
	
}
