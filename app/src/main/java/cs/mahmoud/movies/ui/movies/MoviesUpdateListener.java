package cs.mahmoud.movies.ui.movies;

import lib.morkim.mfw.ui.UpdateListener;


interface MoviesUpdateListener extends UpdateListener {

	void initializeMoviesListView();

	void initializeMoviesItems();
}
