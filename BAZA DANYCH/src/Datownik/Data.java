package Datownik;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

public class Data {

	public static Date utworzDate(int pmRok, int pmMiesiac, int pmDzien) {
		return new Date(new GregorianCalendar(pmRok, pmMiesiac - 1, pmDzien).getTimeInMillis());
	}

	public static Date utworzDateNaPierwszyDzien(YearMonth pmMiesiac) {
		return DateFromLocalDate(pmMiesiac.atDay(1));
	}

	public static Date utworzDateNaOstatniDzien(YearMonth pmMiesiac) {
		return DateFromLocalDate(pmMiesiac.atEndOfMonth());
	}

	public static LocalDate LocalDateFromDate(Date pmData) {
		if (pmData == null)
			return null;
		return pmData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date DateFromLocalDate(LocalDate pmData) {
		return Date.from(pmData.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate ex(int pmRok, int pmMiesiac, int pmDzien) {
		return LocalDate.of(pmRok, pmMiesiac, pmDzien);
	}
}
