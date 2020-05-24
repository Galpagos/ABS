package enums;

import ProjektGlowny.commons.Frames.Komunikat;

public enum Informacje implements Komunikat {
	BRAK_NIEOBECNOSCI_DNIA("Brak nieobecności na dzień " + ARG, "Nieobecni dnia: " + ARG), //
	DODANO_DZIEN_WOLNY("Dnia: " + ARG + " z powodu " + ARG, "Dodano dzień wolny"), //
	NIEOBECNI_DNIA("Nieobecności na dzień " + ARG + ":\n" + ARG, "Nieobecni dnia: " + ARG), //
	DNI_WOLNE("Ostatnie wprowadzone 20 dni wolnych: \n" + ARG, "Dni Wolne");
	private String mKomunikat;
	private String mTytul;

	Informacje(String pmKomunikat, String pmTytul) {
		mTytul = pmTytul;
		mKomunikat = pmKomunikat;
	}

	@Override
	public String getKomunikat() {
		return mKomunikat;
	}

	@Override
	public String getTytul() {
		return mTytul;
	}

}
