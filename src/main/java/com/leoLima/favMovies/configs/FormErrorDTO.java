package com.leoLima.favMovies.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author leonardoljr
 * 
 * Class used to return the exception error to client
 *
 */

@AllArgsConstructor
@Getter
public class FormErrorDTO {

	private String field;
	
	private String message;
	
}
