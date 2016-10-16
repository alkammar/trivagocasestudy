package cs.mahmoud.movies.task;

import cs.mahmoud.movies.app.CsmApp;
import cs.mahmoud.movies.model.CsmModel;
import lib.morkim.mfw.usecase.MorkimAsyncTask;
import lib.morkim.mfw.usecase.TaskResult;


public class CancelRequestTask extends MorkimAsyncTask<CsmApp, CsmModel, CancelRequest, TaskResult> {

	public CancelRequestTask(CsmApp appContext) {
		super(appContext);
	}

	@Override
	protected TaskResult onExecute(CancelRequest cancelRequest) {

		cancelRequest.task.cancel();

		return null;
	}
}
