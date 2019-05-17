package Enums;

import java.awt.Color;

public enum SLRodzajeAbsencji
{
	urlop_wypoczynkowy("uW", "Urlop wypoczynkowy", new Color(255, 165, 0)), //
	urlop_w_pracy("uPR", "Urlop w pracy", new Color(254, 127, 156)), //
	L_4("L4", "L-4", new Color(9, 200, 18)), //
	opieka_na_dziecko("oDZ", "Opieka na dziecko", new Color(9, 200, 18)), //
	opieka_na_kogos("oK", "Opieka na kogo�", new Color(9, 200, 18)), //
	wypadek("WYP", "Wypadek", new Color(9, 200, 18)), //
	szpital("SZ", "Szpital", new Color(9, 200, 18)), //
	ci��a("C", "Ci��a", new Color(9, 200, 18)), //
	urlop_ojcowski("uOJ", "Urlop ojcowski", new Color(51, 51, 255)), //
	urlop_macierzy�ski("uM", "Urlop macierzy�ski", new Color(51, 51, 255)), //
	urlop_rodzicielski("uR", "Urlop rodzicielski", new Color(51, 51, 255)), //
	urlop_okoliczno�ciowy("uOK", "Urlop okoliczno�ciowy", new Color(249, 216, 85)), //
	NN("NN", "NN", new Color(255, 38, 38)), //
	NUN("NUN", "NUN", new Color(255, 38, 38)), //
	NZ("NZ", "NZ", new Color(255, 38, 38)), //
	DW("DW", "DW", new Color(255, 255, 0)), //
	NB("NB", "NB", new Color(128, 0, 128)), //
	UB("UB", "UB", new Color(255, 38, 38)); //

	private final String nazwa;
	private final Color mColor;
	private final String mSkrot;

	SLRodzajeAbsencji(String pmSkrot, String nazwa, Color pmColor)
	{
		this.nazwa = nazwa;
		mColor = pmColor;
		mSkrot = pmSkrot;

	}

	public String getNazwa()
	{
		return nazwa;
	}

	public Color getColor()
	{
		return mColor;
	}

	@Override
	public String toString()
	{
		return nazwa;
	}

	public static SLRodzajeAbsencji AbsencjaPoNazwie(String pmNazwa)
	{
		for (SLRodzajeAbsencji e : SLRodzajeAbsencji.values())
		{
			if (e.nazwa.equalsIgnoreCase(pmNazwa))
			{
				return e;
			}
		}
		return null;

	}

	public String getSkrot()
	{
		return mSkrot;
	}
}