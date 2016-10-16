package cs.mahmoud.movies.model;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;


public class CsmModel extends Model {

	public CsmModel(MorkimApp morkimApp) {
		super(morkimApp);
	}

	@Override
	public void load() throws GatewayRetrieveException {

	}

	@Override
	public void save() throws GatewayPersistException {

	}
}
