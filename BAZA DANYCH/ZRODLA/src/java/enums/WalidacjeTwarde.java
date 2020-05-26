package enums;

import ProjektGlowny.commons.Frames.Komunikat;

import javax.swing.JOptionPane;

public enum WalidacjeTwarde implements Komunikat {
	NachodzaNaSiebieOkresy("Występuje już absencja w tym okresie!"), //
	DataPoPrzedDataPrzed("Data końcowa musi być po dacie początkowej"), //
	DzienWolnyWWeekend("Nie można dodać dnia wolnego w weekend!"), //
	NiewlasciwyFormatDaty("Podaj datę w odpowiednim formacie!"), //
	NiewlasciwyFormatLiczby("Podaj wartosc liczbową!"), //
	DatayWtymSamymRoku("Data końcowa i początkowa powinny być w tym samym roku!");//

	private String mOpis;
	private String mNazwa;
	private int mRodzaj;

	WalidacjeTwarde(String pmOpis, String pmNazwa, int pmRodzaj) {
		this.mOpis = pmOpis;
		this.mNazwa = pmNazwa;
		this.mRodzaj = pmRodzaj;
	}

	WalidacjeTwarde(String pmOpis) {
		mOpis = pmOpis;
		mNazwa = "Błąd";
		mRodzaj = JOptionPane.ERROR_MESSAGE;
	}
	@Deprecated
	public void pokaz() {
		JOptionPane.showMessageDialog(null, mOpis, mNazwa, mRodzaj);
	}

	@Deprecated
	public static boolean PotwierdzenieOperacjiUsuniecia() {
		int reply = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć dane? \nOperacja jest nieodwracalna", "Potwierdzenie operacji",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getKomunikat() {
		return mOpis;
	}

	@Override
	public String getTytul() {
		return mNazwa;
	}
}
