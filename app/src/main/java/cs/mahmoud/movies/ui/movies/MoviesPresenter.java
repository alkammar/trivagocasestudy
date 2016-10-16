package cs.mahmoud.movies.ui.movies;

import lib.morkim.mfw.ui.Presenter;


class MoviesPresenter extends Presenter<MoviesController> {

	String getScreenTitle() {
		return "Top Movies";
	}

	int getMoviesCount() {
		return controller.getMovies().size();
	}

	String getMovieTitle(int position) {
		return controller.getMovies().get(position).getTitle();
	}

	String getMovieYear(int position) {
		return "" + controller.getMovies().get(position).getYear();
	}

	String getMovieOverview(int position) {
		return controller.getMovies().get(position).getOverview();
	}

	String getMoviePosterUrl(int position) {
		return controller.getMovies().get(position).getPosterUrl();
	}
}
