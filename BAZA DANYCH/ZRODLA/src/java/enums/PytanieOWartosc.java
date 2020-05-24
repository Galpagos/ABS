package enums;

import ProjektGlowny.commons.Frames.Komunikat;

public enum PytanieOWartosc implements Komunikat {

	POWOD_DNIA_WOLNEGO("Podaj powód dnia wolnego:", "Powód dnia wolnego");

	private String mPytanie;
	private String mTytul;

	PytanieOWartosc(String pmPytanie, String pmTytul) {
		mPytanie = pmPytanie;
		mTytul = pmTytul;
	}
	@Override
	public String getKomunikat() {
		return mPytanie;
	}

	@Override
	public String getTytul() {
		return mTytul;
	}

}
