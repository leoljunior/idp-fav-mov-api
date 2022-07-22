package com.leoLima.favMovies.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FormErrorDTO {

	private String field;
	private String message;
	
}
