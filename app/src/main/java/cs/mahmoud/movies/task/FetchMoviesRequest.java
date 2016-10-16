package cs.mahmoud.movies.task;

import lib.morkim.mfw.usecase.TaskRequest;


public class FetchMoviesRequest extends TaskRequest {

	int page;
	String filter;

	private FetchMoviesRequest(RequestBuilder builder) {
		super(builder);
	}

	public static class Builder extends RequestBuilder<FetchMoviesRequest> {

		private String filter;
		private int page;

		public Builder filter(String filter) {
			this.filter = filter;

			return this;
		}

		public Builder page(int page) {
			this.page = page;

			return this;
		}

		@Override
		public FetchMoviesRequest build() {

			FetchMoviesRequest request = new FetchMoviesRequest(this);
			request.filter = filter;
			request.page = page;

			return request;
		}
	}
}
