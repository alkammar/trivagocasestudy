package cs.mahmoud.movies.services;


import cs.mahmoud.movies.app.CsmApp;
import cs.mahmoud.movies.model.CsmModel;
import cs.mahmoud.movies.task.CancelRequest;
import cs.mahmoud.movies.task.FetchMoviesRequest;
import cs.mahmoud.movies.task.FetchMoviesResult;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;

public interface Mdb {

	MorkimTask<CsmApp, CsmModel, FetchMoviesRequest, FetchMoviesResult> createMoviesFetcher(MorkimTaskListener<FetchMoviesResult> listener);
	MorkimTask<CsmApp, CsmModel, CancelRequest, ?> createCancelRequest();
}
