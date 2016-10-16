package cs.mahmoud.movies.model;

import lib.morkim.mfw.domain.Entity;


public class Movie extends Entity {

	private String title;
	private int year;
	private String overview;
	private String posterUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public int getYear() {
		return year;
	}

	public String getOverview() {
		return overview;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
}
