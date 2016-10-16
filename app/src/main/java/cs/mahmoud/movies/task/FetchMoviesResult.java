package cs.mahmoud.movies.task;

import java.util.List;

import cs.mahmoud.movies.model.Movie;
import lib.morkim.mfw.usecase.TaskResult;


public class FetchMoviesResult extends TaskResult {

	public List<Movie> movies;
	public int page;
	public boolean isLastPage;
}
