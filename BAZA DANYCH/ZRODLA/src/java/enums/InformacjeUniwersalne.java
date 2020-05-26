package enums;

import ProjektGlowny.commons.Frames.Komunikat;

public enum InformacjeUniwersalne implements Komunikat {
	POTWIERDZENIE_USUNIECIA("Czy na pewno chcesz usunąć dane? \nOperacja jest nieodwracalna", "Potwierdzenie usunięcia danych!");

	private String mKomunikat;
	private String mNazwa;

	InformacjeUniwersalne(String pmKomunikat, String pmNazwa) {
		mKomunikat = pmKomunikat;
		mNazwa = pmNazwa;
	}
	@Override
	public String getKomunikat() {
		return mKomunikat;
	}

	@Override
	public String getTytul() {
		return mNazwa;
	}

}
