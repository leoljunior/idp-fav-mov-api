package com.leoLima.favMovies.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.leoLima.favMovies.model.Category;
import com.leoLima.favMovies.model.Movie;
import com.leoLima.favMovies.repositories.MovieRepository;

class MovieServiceTest {

	@Autowired
	private MovieService movieService;
	
	@Mock
	private CategoryService mockCategoryService;
	
	@Mock
	private MovieRepository mockMovieRepository;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.movieService = new MovieService(mockMovieRepository, mockCategoryService);
	}
	
	
	@Test
	void givenAMovieAndCategory_whenAddNewMovieReceiveARequest_thenReturnANewMovie() {
		Movie movie = createAMovie();
		Category createACategory = createACategory();
		
		when(mockMovieRepository.save(movie)).thenReturn(movie);
		when(mockCategoryService.getOrCreateCategory("war")).thenReturn(createACategory);
	
		Movie createdMovie = movieService.addNewMovie(movie, "war");
		
		assertEquals(movie, createdMovie);
		assertEquals(1, createdMovie.getId());		
		assertEquals("tt0120815", createdMovie.getImdbID());	
		assertEquals("Saving Private Ryan", createdMovie.getTitle());	
		assertEquals("movie", createdMovie.getType());	
		assertEquals("Drama, War", createdMovie.getGenre());	
	}

	
	@Test
	void whenGetAllMoviesReceiveARequest_thenReturnAMovieList() {
		Movie movie = createAMovie();
		when(mockMovieRepository.findAll()).thenReturn(Arrays.asList(movie));
		
		List<Movie> allMoviesList = movieService.listAllMovies();
		
		assertNotNull(allMoviesList);
		assertEquals(1, allMoviesList.size());
		assertEquals("tt0120815", allMoviesList.get(0).getImdbID());		
		assertEquals("Saving Private Ryan", allMoviesList.get(0).getTitle());		
		assertEquals("movie", allMoviesList.get(0).getType());		
		assertEquals("Drama, War", allMoviesList.get(0).getGenre());		
	}
	
	@Test
	void givenACategory_whenGetAllMoviesReceiveARequest_thenReturnAMovieListByCategory() {
		Movie movie = createAMovie();
		movie.setCategory(new Category("war"));
		when(mockMovieRepository.findByCategoryName("war")).thenReturn(Arrays.asList(movie));
		
		List<Movie> allMoviesList = movieService.getMoviesByCategoryName("war");
		
		assertNotNull(allMoviesList);
		assertEquals("war", allMoviesList.get(0).getCategory().getName());
		assertEquals(1, allMoviesList.size());
		assertEquals("tt0120815", allMoviesList.get(0).getImdbID());		
		assertEquals("Saving Private Ryan", allMoviesList.get(0).getTitle());		
		assertEquals("movie", allMoviesList.get(0).getType());		
		assertEquals("Drama, War", allMoviesList.get(0).getGenre());		
	}
	
	@Test
	void givenAValidId_whenGetMovieByIdReceiveARequest_thenReturnASpecificMovie() {
		Movie movie = createAMovie();
		when(mockMovieRepository.findById(1L)).thenReturn(Optional.of(movie));
		
		Optional<Movie> movieById = movieService.getMovieById(1L);
		
		assertEquals(1, movieById.get().getId());
		assertEquals("tt0120815", movieById.get().getImdbID());		
		assertEquals("Saving Private Ryan", movieById.get().getTitle());		
		assertEquals("movie", movieById.get().getType());		
		assertEquals("Drama, War", movieById.get().getGenre());		
	}
	
	@Test
	void givenAValidImdbId_whenGetMovieByIdReceiveARequest_thenReturnASpecificMovie() {
		Movie movie = createAMovie();
		when(mockMovieRepository.findByImdbID("tt0120815")).thenReturn(Optional.of(movie));
		
		Optional<Movie> movieById = movieService.getMovieByImdbId("tt0120815");
		
		assertEquals(1, movieById.get().getId());
		assertEquals("tt0120815", movieById.get().getImdbID());		
		assertEquals("Saving Private Ryan", movieById.get().getTitle());		
		assertEquals("movie", movieById.get().getType());		
		assertEquals("Drama, War", movieById.get().getGenre());		
	}
	
	@Test
	void givenAValidMovie_whenDelteMovieReceiveARequest_thenMovieIsDeleted() {
		Movie movie = createAMovie();
	
		movieService.deleteMovie(movie);
		
		Mockito.verify(mockMovieRepository).delete(movie);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Movie createAMovie() {
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setImdbID("tt0120815");
		movie.setTitle("Saving Private Ryan");
		movie.setType("movie");
		movie.setGenre("Drama, War");
		return movie;
	}
	private Category createACategory() {
		return new Category("war");
	}
	
}
