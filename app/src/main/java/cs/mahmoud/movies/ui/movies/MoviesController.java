package cs.mahmoud.movies.ui.movies;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import cs.mahmoud.movies.app.CsmApp;
import cs.mahmoud.movies.model.CsmModel;
import cs.mahmoud.movies.model.Movie;
import cs.mahmoud.movies.task.CancelRequest;
import cs.mahmoud.movies.task.FetchMoviesRequest;
import cs.mahmoud.movies.task.FetchMoviesResult;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.SimpleTaskListener;


class MoviesController extends Controller<CsmApp, CsmModel, MoviesUpdateListener> {

	private List<Movie> movies;

	private String currentFilter;
	private int currentPage;
	private boolean loadingMovies;
	private boolean moreResultsAvailable;
	private MorkimTask<CsmApp, CsmModel, FetchMoviesRequest, FetchMoviesResult> currentSearchTask;

	public MoviesController(CsmApp morkimApp) {
		super(morkimApp);

		movies = new ArrayList<>();

		currentFilter = "";
		resetPaginationFlags();
	}

	@Override
	public void onInitializeViews(MoviesUpdateListener updateListener) {
		super.onInitializeViews(updateListener);

		updateListener.initializeMoviesListView();
	}

	@Override
	protected void executeInitializationTask() {
		super.executeInitializationTask();

		executeFetchMoviesTask();
	}

	SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {

			synchronized (MoviesController.this) {

				if (!loadingMovies) {
					resetPaginationFlags();
					executeFetchMoviesTask();
				}
			}
		}
	};

	SearchView.OnQueryTextListener queryListener = new SearchView.OnQueryTextListener() {
		@Override
		public boolean onQueryTextSubmit(String query) {
			return true;
		}

		@Override
		public boolean onQueryTextChange(String newText) {

			synchronized (MoviesController.this) {

				resetPaginationFlags();
				currentFilter = newText;

				executeFetchMoviesTask();
			}

			return true;
		}
	};

	private void resetPaginationFlags() {
		currentPage = 0;
		moreResultsAvailable = true;
	}

	MoviesScrollListener.OnBottomReachedListener bottomReachedListener = new MoviesScrollListener.OnBottomReachedListener() {
		@Override
		public void onBottomReached() {

			synchronized (MoviesController.this) {

				if (!loadingMovies && moreResultsAvailable) {
					loadingMovies = true;
					executeFetchMoviesTask();
				}
			}
		}
	};

	private synchronized void executeFetchMoviesTask() {

		if (currentSearchTask != null)
			getAppContext().getMdb().createCancelRequest()
					.execute(new CancelRequest.Builder()
							.task(currentSearchTask)
							.build());

		currentSearchTask = getAppContext().getMdb().createMoviesFetcher(new SimpleTaskListener<FetchMoviesResult>() {
			@Override
			public void onTaskComplete(FetchMoviesResult taskResult) {

				synchronized (MoviesController.this) {

					currentPage = taskResult.page;

					if (currentPage == 1)
						movies = taskResult.movies;
					else {
						if (taskResult.isLastPage)
							moreResultsAvailable = false;
						movies.addAll(taskResult.movies);
					}

					getUpdateListener().initializeMoviesItems();

					loadingMovies = false;
				}
			}
		});

		currentSearchTask.execute(new FetchMoviesRequest.Builder()
				.filter(currentFilter)
				.page(++currentPage)
				.build());
	}

	void onShowingScreen() {

		getUpdateListener().initializeMoviesItems();
	}

	public List<Movie> getMovies() {
		return movies;
	}
}
