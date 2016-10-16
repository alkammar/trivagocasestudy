package cs.mahmoud.movies.ui.movies;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cs.mahmoud.movies.R;
import lib.morkim.mfw.ui.lists.ListAdapter;
import lib.morkim.mfw.ui.lists.ListItemHolder;


class MoviesListAdapter extends ListAdapter<MoviesPresenter, MoviesListAdapter.MovieItemHolder> {

	MoviesListAdapter(MoviesPresenter presenter) {
		super(presenter);
	}

	@Override
	protected int layoutId() {
		return R.layout.list_item_movie;
	}

	@Override
	protected MovieItemHolder holdView(View view, int i) {
		return new MovieItemHolder(view);
	}

	@Override
	public void onBindViewHolder(MovieItemHolder holder, int position) {

		holder.title.setText(presenter.getMovieTitle(position));
		holder.year.setText(presenter.getMovieYear(position));
		holder.overview.setText(presenter.getMovieOverview(position));
		String moviePosterUrl = presenter.getMoviePosterUrl(position);
		if (moviePosterUrl != null) {
			Picasso.with(holder.itemView.getContext()).load(moviePosterUrl).into(holder.poster);
			holder.poster.setVisibility(View.VISIBLE);
		} else
			holder.poster.setVisibility(View.GONE);
	}

	@Override
	public int getItemCount() {
		return presenter.getMoviesCount();
	}

	class MovieItemHolder extends ListItemHolder {

		private TextView title;
		private TextView year;
		private TextView overview;
		private ImageView poster;

		MovieItemHolder(View itemView) {
			super(itemView);

			title = (TextView) itemView.findViewById(R.id.tv_movie_title);
			year = (TextView) itemView.findViewById(R.id.tv_movie_year);
			overview = (TextView) itemView.findViewById(R.id.tv_movie_overview);
			poster = (ImageView) itemView.findViewById(R.id.iv_poster);
		}
	}
}
