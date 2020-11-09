package pl.home.components.frames.src;

import ProjektGlowny.commons.Frames.AbstractOkno;

import pl.home.components.frames.parameters.RaportsConfigurationIn;
import pl.home.components.frames.parameters.RaportsConfigurationOut;

public abstract class SrcRaportsConfiguration extends AbstractOkno<RaportsConfigurationIn, RaportsConfigurationOut> {

	public SrcRaportsConfiguration(RaportsConfigurationIn pmParams) {
		super(pmParams);

	}

	@Override
	protected void onOpen() {
	}

	@Override
	protected void przypiszMetody() {
	}

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void budujOkno() {
	}

	@Override
	protected RaportsConfigurationOut budujWyjscie() {
		return null;
	}

}
