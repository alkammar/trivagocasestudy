package cs.mahmoud.movies.app;

import cs.mahmoud.movies.model.CsmModel;
import cs.mahmoud.movies.services.Mdb;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.task.TaskFactory;


public class CsmApp extends MorkimApp<CsmModel, MorkimRepository> {

	@Override
	protected void createFactories() {

	}

	@Override
	protected MorkimRepository createRepo() {
		return new MorkimRepository(this) {
			@Override
			protected int version() {
				return 0;
			}

			@Override
			protected void onUpgrade(int i) {

			}
		};
	}

	@Override
	protected CsmModel createModel() {
		return new CsmModel(this);
	}

	@Override
	protected TaskFactory createScheduledTaskFactory() {
		return null;
	}

	public Mdb getMdb() {
		return new MdbFactory(this);
	}
}
