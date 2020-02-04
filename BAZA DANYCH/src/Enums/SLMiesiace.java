package Enums;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public enum SLMiesiace {
	N01_STYCZEN(1, "Stycze�"), //
	NO2_LUTY(2, "Luty"), //
	NO3_MARZEC(3, "Marzec"), //
	NO4_KWIECIEN(4, "Kwiecie�"), //
	NO5_MAJ(5, "Maj"), //
	NO6_CZERWIEN(6, "Czewiec"), //
	NO7_LIPIEC(7, "Lipiec"), //
	NO8_SIERPIEN(8, "Sierpie�"), //
	NO9_WRZESIEN(9, "Wrzesie�"), //
	N10_PAZDZIERNIK(10, "Pa�dziernik"), //
	N11_LISTOPAD(11, "Listopad"), //
	N12_GRUDZIEN(12, "Grudzie�"), //
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
			DateTime lvStart = new DateTime().withYear(pmRok).withMonthOfYear(getMiesiacInt()).withDayOfMonth(1)
					.withTimeAtStartOfDay();
			DateTime lvEnd = lvStart.plusMonths(1).plusHours(10).minusDays(1);
			return new Interval(lvStart, lvEnd);
		} else {
			DateTime lvStart = new DateTime().withYear(pmRok).withMonthOfYear(1).withDayOfMonth(1)
					.withTimeAtStartOfDay();
			DateTime lvEnd = lvStart.plusMonths(12).plusHours(10).minusDays(1);
			return new Interval(lvStart, lvEnd);
		}
	}

	public static SLMiesiace getByValue(int i) {
		for (SLMiesiace e : SLMiesiace.values()) {
			if (i == e.getMiesiacInt())
				return e;
		}
		return null;
	}

}
