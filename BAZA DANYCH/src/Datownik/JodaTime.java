package Datownik;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class JodaTime
{
	public static Interval okresMiesiac(int pmMiesiac, int pmRok)
	{
		return new Interval(
				new DateTime().withYear(pmRok).withMonthOfYear(pmMiesiac).withDayOfMonth(1).withTimeAtStartOfDay(),
				new DateTime().withYear(pmRok).withMonthOfYear(pmMiesiac).withDayOfMonth(1).withTimeAtStartOfDay()
						.plusMonths(1).minusDays(1));
	}
}
