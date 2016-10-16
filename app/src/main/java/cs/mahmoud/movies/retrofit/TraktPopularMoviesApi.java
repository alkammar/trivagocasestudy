package cs.mahmoud.movies.retrofit;


import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

public interface TraktPopularMoviesApi {

	@Headers({
			"Content-type: application/json",
			"trakt-api-key: ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086",
			"trakt-api-version: 2"
	})
	@GET("/search?type=movie")
	Call<List<TraktResponse>> loadMovies(@Query("query") String filter, @Query("page") int page);

	@Headers({
			"Content-type: application/json",
			"trakt-api-key: ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086",
			"trakt-api-version: 2"
	})
	@GET("/movies/trending?extended=full")
	Call<List<TraktResponse>> loadMovies(@Query("page") int page);
}
