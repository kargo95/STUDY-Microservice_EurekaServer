package io.javabrains.movieinfoservice.models;

public class Movie {
	private String movieId;
	public Movie(String movieId, String name) {
		super();
		this.movieId = movieId;
		this.name = name;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
}
