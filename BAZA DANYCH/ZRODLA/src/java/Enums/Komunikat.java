package Enums;

import javax.swing.JOptionPane;

public enum Komunikat {
	oknoPracownikaBrakZaznaczeniaWTabeli("Wybierz Absencje!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE), //
	oknoGlowneBrakZaznaczeniaWTabeli("Wybierz pracownika!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE), //
	NachodzaNaSiebieOkresy("Występuje już absencja w tym okresie!", "Błąd", JOptionPane.ERROR_MESSAGE), //
	DataPoPrzedDataPrzed("Data końcowa musi być po dacie początkowej", "Błąd", JOptionPane.ERROR_MESSAGE), //
	DatayWtymSamymRoku("Data ko�cowa i początkowa powinny być w tym samym roku!", "Błąd", JOptionPane.ERROR_MESSAGE);//

	private String mOpis;
	private String mNazwa;
	private int mRodzaj;

	Komunikat(String pmOpis, String pmNazwa, int pmRodzaj) {
		this.mOpis = pmOpis;
		this.mNazwa = pmNazwa;
		this.mRodzaj = pmRodzaj;
	}

	public void pokaz() {
		JOptionPane.showMessageDialog(null, mOpis, mNazwa, mRodzaj);
	}

	public static boolean PotwierdzenieOperacjiUsuniecia() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Czy na pewno chcesz usunąć dane? \nOperacja jest nieodwracalna", "Potwierdzenie operacji",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
}
