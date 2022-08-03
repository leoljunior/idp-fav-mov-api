package com.leoLima.favMovies.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author leonardoljr
 * 
 * Class used to receive category from client
 *
 */
@Getter @Setter
public class MovieCategoryInputDTO {

	@NotBlank
	@NotNull
	@NotEmpty
	private String category;
	
}
