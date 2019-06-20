package Enums;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public enum SLMiesiace
{
	Styczeñ(1), //
	Luty(2), //
	Marzec(3), //
	Kwiecieñ(4), //
	Maj(5), //
	Czerwiec(6), //
	Lipiec(7), //
	Sierpieñ(8), //
	Wrzesieñ(9), //
	PaŸdziernik(10), //
	Listopad(11), //
	Grudzieñ(12), //
	Rok(0);

	private int mMiesiacInt;

	SLMiesiace(int k)
	{
		this.mMiesiacInt = k;
	}

	public int getMiesiacInt()
	{
		return mMiesiacInt;
	}

	public Interval getOkres(int pmRok)
	{
		if (getMiesiacInt() > 0)
		{
			DateTime lvStart = new DateTime().withYear(pmRok).withMonthOfYear(getMiesiacInt()).withDayOfMonth(1)
					.withTimeAtStartOfDay();
			DateTime lvEnd = lvStart.plusMonths(1).plusHours(10).minusDays(1);
			return new Interval(lvStart, lvEnd);
		} else
		{
			DateTime lvStart = new DateTime().withYear(pmRok).withMonthOfYear(1).withDayOfMonth(1)
					.withTimeAtStartOfDay();
			DateTime lvEnd = lvStart.plusMonths(12).plusHours(10).minusDays(1);
			return new Interval(lvStart, lvEnd);
		}
	}

}
