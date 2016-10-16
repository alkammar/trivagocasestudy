package cs.mahmoud.movies.task;

import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.TaskRequest;

public class CancelRequest extends TaskRequest {

	MorkimTask task;

	private CancelRequest(RequestBuilder builder) {
		super(builder);
	}

	public static class Builder extends RequestBuilder<CancelRequest> {

		private MorkimTask task;

		public CancelRequest.Builder task(MorkimTask task) {
			this.task = task;

			return this;
		}

		@Override
		public CancelRequest build() {

			CancelRequest request = new CancelRequest(this);
			request.task = task;

			return request;
		}
	}
}
