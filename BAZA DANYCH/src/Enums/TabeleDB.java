package Enums;

public enum TabeleDB {
	AD_SYS_INFO("AD_SYS_INFO", "ID_TABELI");

	String mKod;
	String mIdPole;

	private TabeleDB(String pmNazwa, String pmIdPole) {
		mKod = pmNazwa;
		mIdPole = pmIdPole;
	}

	public String getKod() {
		return mKod;
	}

	public String getIdPole() {
		return mIdPole;
	}
}
