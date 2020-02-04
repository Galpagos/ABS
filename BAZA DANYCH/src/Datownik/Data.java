package Datownik;

import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.YearMonth;

public class Data {

	public static Date utworzDate(int pmRok, int pmMiesiac, int pmDzien) {
		return new Date(new GregorianCalendar(pmRok, pmMiesiac - 1, pmDzien).getTimeInMillis());
	}

	public static Date utworzDateNaPierwszyDzien(YearMonth pmMiesiac) {
		return utworzDate(pmMiesiac.getYear(), pmMiesiac.getMonthOfYear(), 1);
	}

	public static Date utworzDateNaOstatniDzien(YearMonth pmMiesiac) {
		return utworzDate(pmMiesiac.getYear(), pmMiesiac.getMonthOfYear(),
				pmMiesiac.toLocalDate(1).dayOfMonth().getMaximumValue());
	}
}
