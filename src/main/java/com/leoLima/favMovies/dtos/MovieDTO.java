package com.leoLima.favMovies.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class MovieDTO {
	
	@JsonView({View.One.class, View.Two.class})
	private Long id;
	
	@JsonView(View.One.class)
	private String imdbID;
	
	@JsonView({View.One.class, View.Two.class})
	private String title;
	
	@JsonView(View.One.class)
	private String year;	
	
	@JsonView(View.One.class)
	private String rated;
	
	@JsonView({View.One.class, View.Two.class})
	private String released;
	
	@JsonView(View.One.class)
	private String runtime;	
	
	@JsonView(View.One.class)
	private String genre;	
	
	@JsonView(View.One.class)
	private String director;	
	
	@JsonView(View.One.class)
	private String writer;
	
	@JsonView(View.One.class)
	private String actors;	
	
	@JsonView(View.One.class)
	private String plot;
	
	@JsonView(View.One.class)
	private String language;
	
	@JsonView(View.One.class)
	private String country;
	
	@JsonView(View.One.class)
	private String awards;
	
	@JsonView(View.One.class)
	private String poster;
	
	@JsonView(View.One.class)
	private String imdbRating;
	
	@JsonView(View.One.class)
	private String imdbVotes;
	
	@JsonView({View.One.class, View.Two.class})
	private String type;
	
	@JsonView(View.One.class)
	private String totalSeasons;
	
	@JsonView({View.One.class, View.Two.class})
	private String category;
}
