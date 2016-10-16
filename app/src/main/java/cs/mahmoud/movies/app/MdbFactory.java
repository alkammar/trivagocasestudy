package cs.mahmoud.movies.app;

import cs.mahmoud.movies.services.Mdb;
import cs.mahmoud.movies.task.CancelRequestTask;
import cs.mahmoud.movies.task.FetchMoviesResult;
import cs.mahmoud.movies.task.FetchMoviesTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;


class MdbFactory implements Mdb {

	private CsmApp app;

	MdbFactory(CsmApp app) {
		this.app = app;
	}

	@Override
	public FetchMoviesTask createMoviesFetcher(MorkimTaskListener<FetchMoviesResult> listener) {
		return new FetchMoviesTask(app, listener);
	}

	@Override
	public CancelRequestTask createCancelRequest() {
		return new CancelRequestTask(app);
	}
}
