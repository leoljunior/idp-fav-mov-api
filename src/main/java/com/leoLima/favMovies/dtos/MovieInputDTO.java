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
 * Class used to receive movie from client
 *
 */
@Getter @Setter
public class MovieInputDTO {

	@NotBlank
	@NotNull
	@NotEmpty
	private String imdbID;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String category;
	
}
