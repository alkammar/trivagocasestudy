package cs.mahmoud.movies.ui.movies;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

class MoviesScrollListener extends RecyclerView.OnScrollListener {

	private LinearLayoutManager layoutManager;

	private OnBottomReachedListener onBottomReachedListener;

	MoviesScrollListener(LinearLayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

		if (dy > 0) {
			int visibleItemCount = layoutManager.getChildCount();
			int totalItemCount = layoutManager.getItemCount();
			int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

			if (visibleItemCount + pastVisibleItems >= totalItemCount)
				onBottomReachedListener.onBottomReached();
		}
	}

	void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
		this.onBottomReachedListener = onBottomReachedListener;
	}

	interface OnBottomReachedListener {
		void onBottomReached();
	}
}
