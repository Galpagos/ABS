package Enums;

import javax.swing.JOptionPane;

public enum Komunikat {
	oknoPracownikaBrakZaznaczeniaWTabeli("Wybierz Absencje!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE), //
	oknoGlowneBrakZaznaczeniaWTabeli("Wybierz pracownika!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE), //
	Nachodz¹NaSiebieOkresy("Wystêpuje ju¿ absencja w tym okresie!", "B³¹d", JOptionPane.ERROR_MESSAGE), //
	DataPoPrzedDataPrzed("Data koñcowa musi byæ po dacie pocz¹tkowej", "B³¹d", JOptionPane.ERROR_MESSAGE), //
	DatayWtymSamymRoku("Data koñcowa i pocz¹tkowa powinny byæ w tym samym roku!", "B³¹d", JOptionPane.ERROR_MESSAGE);//

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
				"Czy na pewno chcesz usun¹æ dane? \nOperacja jest nieodwracalna", "Potwierdzenie operacji",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
}
