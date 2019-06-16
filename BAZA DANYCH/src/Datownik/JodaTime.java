package Datownik;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class JodaTime
{
	public static Interval okresMiesiac(int pmMiesiac, int pmRok)
	{
		return new Interval(
				new DateTime().withYear(pmRok).withMonthOfYear(pmMiesiac).withDayOfMonth(1).withTimeAtStartOfDay(),
				new DateTime().withYear(pmRok).withMonthOfYear(pmMiesiac).withDayOfMonth(1).withTimeAtStartOfDay()
						.plusMonths(1).minusDays(1).plusHours(2));
	}

	public static Interval okresOdDo(Date pmOd, Date pmDo)
	{
		DateTime lvOd = new DateTime(pmOd.getTime()).withTimeAtStartOfDay();
		DateTime lvDo = new DateTime(pmDo.getTime()).withTimeAtStartOfDay().plusHours(2);

		return new Interval(lvOd, lvDo);
	}

	public static DateTime dateToDateTime(Date pmData)
	{
		return DateTime.parse(pmData.toInstant().toString()).withTimeAtStartOfDay();
	}
}
