package Enums;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public enum SLMiesiace
{
	Stycze�(1), //
	Luty(2), //
	Marzec(3), //
	Kwiecie�(4), //
	Maj(5), //
	Czerwiec(6), //
	Lipiec(7), //
	Sierpie�(8), //
	Wrzesie�(9), //
	Pa�dziernik(10), //
	Listopad(11), //
	Grudzie�(12), //
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

	public static SLMiesiace getByValue(int i)
	{
		for (SLMiesiace e : SLMiesiace.values())
		{
			if (i == e.getMiesiacInt())
				return e;
		}
		return null;
	}

}