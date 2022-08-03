package com.leoLima.favMovies.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("imdbID")
	private String imdbID;
	
	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("Rated")
	private String rated;
	
	@JsonProperty("Released")
	private String released;
	
	@JsonProperty("Runtime")
	private String runtime;
	
	@JsonProperty("Genre")
	private String genre;
	
	@JsonProperty("Director")
	private String director;
	
	@JsonProperty("Writer")
	private String writer;
	
	@JsonProperty("Actors")
	private String actors;	
	
	@JsonProperty("Plot")
	private String plot;	
	
	@JsonProperty("Language")
	private String language;
	
	@JsonProperty("Country")
	private String country;
	
	@JsonProperty("Awards")
	private String awards;
	
	@JsonProperty("Poster")
	private String poster;
	
	@JsonProperty("imdbRating")
	private String imdbRating;
	
	@JsonProperty("Metascore")
	private String metascore;
	
	@JsonProperty("imdbVotes")
	private String imdbVotes;
	
	@JsonProperty("Type")
	private String type;
	
	@JsonProperty("totalSeasons")
	private String totalSeasons;
	
	@JsonProperty("BoxOffice")
	private String boxOffice;
	
	@ManyToOne
	private Category category;
	
}
