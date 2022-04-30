package ProjektGlowny.commons.enums;

import java.time.Year;

import ProjektGlowny.commons.utils.Interval;

public enum SLMiesiace {
	N01_STYCZEN(1, "Styczeń"), //
	NO2_LUTY(2, "Luty"), //
	NO3_MARZEC(3, "Marzec"), //
	NO4_KWIECIEN(4, "Kwiecień"), //
	NO5_MAJ(5, "Maj"), //
	NO6_CZERWIEN(6, "Czewiec"), //
	NO7_LIPIEC(7, "Lipiec"), //
	NO8_SIERPIEN(8, "Sierpień"), //
	NO9_WRZESIEN(9, "Wrzesień"), //
	N10_PAZDZIERNIK(10, "Październik"), //
	N11_LISTOPAD(11, "Listopad"), //
	N12_GRUDZIEN(12, "Grudzień"), //
	N00_ROK(0, "Rok");

	private int mMiesiacInt;
	private String mNazwa;

	SLMiesiace(int pmKod, String pmNazwa) {
		mMiesiacInt = pmKod;
		mNazwa = pmNazwa;
	}

	public int getMiesiacInt() {
		return mMiesiacInt;
	}

	public String getNazwa() {
		return mNazwa;
	}

	public Interval getOkres(int pmRok) {
		if (getMiesiacInt() > 0) {

			return new Interval(getMiesiacInt(), pmRok);
		} else {
			return new Interval(Year.of(pmRok));
		}
	}
}
