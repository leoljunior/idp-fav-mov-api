package com.leoLima.favMovies.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class MovieDTO {
	
	@JsonView({View.AllAttributes.class, View.SomeAttributes.class})
	private Long id;
	
	@JsonView(View.AllAttributes.class)
	private String imdbID;
	
	@JsonView({View.AllAttributes.class, View.SomeAttributes.class})
	private String title;
	
//	@JsonView(View.AllAttributes.class)
//	private String year;	
	
	@JsonView(View.AllAttributes.class)
	private String rated;
	
	@JsonView({View.AllAttributes.class, View.SomeAttributes.class})
	private String released;
	
	@JsonView(View.AllAttributes.class)
	private String runtime;	
	
	@JsonView(View.AllAttributes.class)
	private String genre;	
	
	@JsonView(View.AllAttributes.class)
	private String director;	
	
	@JsonView(View.AllAttributes.class)
	private String writer;
	
	@JsonView(View.AllAttributes.class)
	private String actors;	
	
	@JsonView(View.AllAttributes.class)
	private String plot;
	
	@JsonView(View.AllAttributes.class)
	private String language;
	
	@JsonView(View.AllAttributes.class)
	private String country;
	
	@JsonView(View.AllAttributes.class)
	private String awards;
	
	@JsonView(View.AllAttributes.class)
	private String poster;
	
	@JsonView(View.AllAttributes.class)
	private String imdbRating;
	
	@JsonView(View.AllAttributes.class)
	private String imdbVotes;
	
	@JsonView({View.AllAttributes.class, View.SomeAttributes.class})
	private String type;
	
	@JsonView(View.AllAttributes.class)
	private String totalSeasons;
	
	@JsonView(View.AllAttributes.class)
	private String boxOffice;
	
	@JsonView({View.AllAttributes.class, View.SomeAttributes.class})
	private String category;
}
