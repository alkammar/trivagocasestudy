package cs.mahmoud.movies.task;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs.mahmoud.movies.app.CsmApp;
import cs.mahmoud.movies.model.CsmModel;
import cs.mahmoud.movies.model.Movie;
import cs.mahmoud.movies.retrofit.TraktImages;
import cs.mahmoud.movies.retrofit.TraktPopularMoviesApi;
import cs.mahmoud.movies.retrofit.TraktResponse;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import okio.Buffer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class FetchMoviesTask extends MorkimTask<CsmApp, CsmModel, FetchMoviesRequest, FetchMoviesResult> {

	private Call<List<TraktResponse>> call;

	public FetchMoviesTask(CsmApp morkimApp, MorkimTaskListener<FetchMoviesResult> listener) {
		super(morkimApp, listener);
	}

	@Override
	protected FetchMoviesResult onExecute(FetchMoviesRequest request) {

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.trakt.tv")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		TraktPopularMoviesApi api = retrofit.create(TraktPopularMoviesApi.class);

		synchronized (this) {
			if (request.filter.isEmpty())
				call = api.loadMovies(request.page);
			else
				call = api.loadMovies(request.filter, request.page);
		}

		retrofit.client().interceptors().add(new LoggingInterceptor());

		call.enqueue(new Callback<List<TraktResponse>>() {
			@Override
			public void onResponse(Response<List<TraktResponse>> response, Retrofit retrofit) {

				FetchMoviesResult result = new FetchMoviesResult();

				String pageCountHeader = response.headers().get("x-pagination-page-count");
				Integer pageCount = pageCountHeader != null ? Integer.valueOf(pageCountHeader) : 1;
				result.page = Integer.valueOf(response.headers().get("x-pagination-page"));
				result.isLastPage = pageCount == result.page;

				result.movies = new ArrayList<>();

				List<TraktResponse> tmList = response.body();

				if (tmList != null)
					for (TraktResponse tr : tmList) {

						Movie movie = new Movie();

						movie.setTitle(tr.movie.title);
						movie.setYear(tr.movie.year);
						movie.setOverview(tr.movie.overview);
						TraktImages images = tr.movie.images;
						if (images != null)
							movie.setPosterUrl(images.poster.thumb);

						result.movies.add(movie);
					}

				getListener().onTaskComplete(result);
			}

			@Override
			public void onFailure(Throwable t) {

				FetchMoviesResult result = new FetchMoviesResult();

				// TODO we should set some error in the result)
			}
		});

		return null;
	}

	@Override
	public void cancel() {
		super.cancel();

		synchronized (this) {
			if (call != null)
				call.cancel();
		}
	}

	public static class LoggingInterceptor implements Interceptor {
		@Override
		public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
			Log.i("LoggingInterceptor", "inside intercept callback");
			Request request = chain.request();
			long t1 = System.nanoTime();
			String requestLog = String.format("Sending request %s on %s%n%s",
					request.url(), chain.connection(), request.headers());
			if (request.method().compareToIgnoreCase("post") == 0) {
				requestLog = "\n" + requestLog + "\n" + bodyToString(request);
			}
			Log.d("TAG", "request" + "\n" + requestLog);
			com.squareup.okhttp.Response response = chain.proceed(request);
			long t2 = System.nanoTime();

			String responseLog = String.format("Received response for %s in %.1fms%n%s",
					response.request().url(), (t2 - t1) / 1e6d, response.headers());

			String bodyString = response.body().string();

			Log.d("TAG", "response only" + "\n" + bodyString);

			Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);

			return response.newBuilder()
					.body(ResponseBody.create(response.body().contentType(), bodyString))
					.build();

		}


		public static String bodyToString(final Request request) {
			try {
				final Request copy = request.newBuilder().build();
				final Buffer buffer = new Buffer();
				copy.body().writeTo(buffer);
				return buffer.readUtf8();
			} catch (final IOException e) {
				return "did not work";
			}
		}
	}
}
