package Enums;

import javax.swing.JOptionPane;

public enum Komunikat
{
	oknoPracownikaBrakZaznaczeniaWTabeli("Wybierz Absencje!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE), //
	oknoGlowneBrakZaznaczeniaWTabeli("Wybierz pracownika!", "Ostrze¿enie", JOptionPane.WARNING_MESSAGE)//
	;

	private String mOpis;
	private String mNazwa;
	private int mRodzaj;

	Komunikat(String pmOpis, String pmNazwa, int pmRodzaj)
	{
		this.mOpis = pmOpis;
		this.mNazwa = pmNazwa;
		this.mRodzaj = pmRodzaj;
	}

	public void pokaz()
	{
		JOptionPane.showMessageDialog(null, mOpis, mNazwa, mRodzaj);
	}
}
