package Enums;

import javax.swing.JOptionPane;

public enum Komunikat
{
	oknoPracownikaBrakZaznaczeniaWTabeli("Wybierz Absencje!", "Ostrze�enie", JOptionPane.WARNING_MESSAGE), //
	oknoGlowneBrakZaznaczeniaWTabeli("Wybierz pracownika!", "Ostrze�enie", JOptionPane.WARNING_MESSAGE), //
	Nachodz�NaSiebieOkresy("Wyst�puje ju� absencja w tym okresie!", "B��d", JOptionPane.ERROR_MESSAGE), //
	DataPoPrzedDataPrzed("Data ko�cowa musi by� po dacie pocz�tkowej", "B��d", JOptionPane.ERROR_MESSAGE), //
	PrzekroczoneLimity("Przekroczono limit dla wybranej Absencji", "Ostrze�enie", JOptionPane.WARNING_MESSAGE), //
	DatayWtymSamymRoku("Data ko�cowa i pocz�tkowa powinny by� w tym samym roku!", "B��d", JOptionPane.ERROR_MESSAGE);//
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

	public static boolean PotwierdzenieOperacjiUsuniecia()
	{
		int reply = JOptionPane.showConfirmDialog(null,
				"Czy na pewno chcesz usun�� dane? \nOperacja jest nieodwracalna", "Potwierdzenie operacji",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			return true;
		} else
		{
			return false;
		}
	}
}