package cs.mahmoud.movies.ui.movies;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import cs.mahmoud.movies.R;
import lib.morkim.mfw.ui.AppCompatScreen;


public class MoviesScreen
		extends AppCompatScreen<MoviesUpdateListener, MoviesController, MoviesPresenter>
		implements MoviesUpdateListener {

	private MoviesListAdapter adapter;
	private RecyclerView moviesRecyclerView;
	private LinearLayoutManager linearLayoutManager;
	private SwipeRefreshLayout refreshLayout;
	private MoviesScrollListener scrollListener;

	@Override
	protected int layoutId() {
		return R.layout.screen_movies;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setTitle(presenter.getScreenTitle());
		getSupportActionBar().setIcon(R.drawable.ic_toolbar);
	}

	@Override
	public void onBindViews() {
		super.onBindViews();

		linearLayoutManager = new LinearLayoutManager(this);
		scrollListener = new MoviesScrollListener(linearLayoutManager);
		adapter = new MoviesListAdapter(presenter);

		moviesRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_list);
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh_wrapper);
	}

	@Override
	public void onAssignListeners() {

		refreshLayout.setOnRefreshListener(controller.refreshListener);
		scrollListener.setOnBottomReachedListener(controller.bottomReachedListener);
	}

	@Override
	public void initializeMoviesListView() {

		moviesRecyclerView.setLayoutManager(linearLayoutManager);
		moviesRecyclerView.setAdapter(adapter);
		moviesRecyclerView.addOnScrollListener(scrollListener);
	}

	@Override
	public void initializeMoviesItems() {

		adapter.notifyDataSetChanged();

		refreshLayout.post(new Runnable() {
			@Override
			public void run() {
				refreshLayout.setRefreshing(false);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();

		controller.onShowingScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		searchView.setOnQueryTextListener(controller.queryListener);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public MoviesUpdateListener getUpdateListener() {
		return this;
	}
}
