package Datownik;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.YearMonth;

import Parsery.ParseryDB;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccess.DniWolneBean;
import dbAccess.dbAccess;

public class LicznikDaty {
	static int ileDniPomiedzy(Date pmDataOd, Date pmDataDo) {
		long diffInMillies = Math.abs(pmDataDo.getTime() - pmDataOd.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return (int) diff + 1;
	}

	public static int ileDniRobotnych(YearMonth pmMiesiac) {
		Date lvDataOd = Data.utworzDateNaPierwszyDzien(pmMiesiac);
		Date lvDataDo = Data.utworzDateNaOstatniDzien(pmMiesiac);
		return ileDniRobotnych(lvDataOd, lvDataDo);
	}

	public static int ileDniRobotnych(Interval pmOkres) {
		Date lvDataOd = pmOkres.getStart().toDate();
		Date lvDataDo = pmOkres.getEnd().toDate();
		return ileDniRobotnych(lvDataOd, lvDataDo);
	}

	public static int ileDniWolnych(Interval pmOkres) {
		Date lvDataOd = pmOkres.getStart().toDate();
		Date lvDataDo = pmOkres.getEnd().toDate();
		return ileDniWolnych(lvDataOd, lvDataDo);
	}

	public static int ileDniRobotnych(List<AbsencjaDTO> pmLista) {
		return pmLista//
				.stream()//
				.mapToInt(lvAbs -> ileDniRobotnych(lvAbs.getOkres()) - ileDniWolnych(lvAbs.getOkres()))//
				.sum();
	}

	public static int ileDniRobotnych(Date pmDataOd, Date pmDataDo) {
		Calendar lvStartCal = Calendar.getInstance();
		lvStartCal.setTime(pmDataOd);

		Calendar lvEndCal = Calendar.getInstance();
		lvEndCal.setTime(pmDataDo);
		int lvWorkDays = 0;

		// Return 0 if start and end are the same
		if (lvStartCal.getTimeInMillis() == lvEndCal.getTimeInMillis()) {
			return 1;
		}
		if (lvStartCal.getTimeInMillis() > lvEndCal.getTimeInMillis()) {
			lvStartCal.setTime(pmDataDo);
			lvEndCal.setTime(pmDataOd);
		}
		do {
			if (lvStartCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& lvStartCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++lvWorkDays;
			}
			lvStartCal.add(Calendar.DAY_OF_MONTH, 1);
		} while (lvStartCal.getTimeInMillis() <= lvEndCal.getTimeInMillis()); // excluding end date
		return lvWorkDays;
	}

	static int ileDniWolnych(Date pmDataOd, Date pmDataDo) {
		return dbAccess.GetCount(DniWolneBean.NazwaTabeli //
				+ " where " + DniWolneBean.kolumnaData + " BEtween " + ParseryDB.DateParserToSQL_SELECT(pmDataOd)
				+ " and " + ParseryDB.DateParserToSQL_SELECT(pmDataDo));
	}

	public static Interval OkreszBazy(Object pmDataOd, Object pmDataDo) {
		Timestamp lvStart = (Timestamp) pmDataOd;
		Timestamp lvEnd = (Timestamp) pmDataDo;
		DateTime lvStartt = DateTime.parse(lvStart.toLocalDateTime().toString());
		DateTime lvEndd = DateTime.parse(lvEnd.toLocalDateTime().plusMinutes(10).toString());
		if (lvStartt.isBefore(lvEndd))
			return new Interval(lvStartt, lvEndd);
		return new Interval(lvEndd, lvStartt);
	}

	public static LocalDateTime LDTparseFromObject(Object pmData) {
		Timestamp lvStart = (Timestamp) pmData;
		return new LocalDateTime(lvStart.toLocalDateTime().toString());
	}

	public static void filtrujAbsencjePoOkresie(List<AbsencjaDTO> pmAbsencja, Interval pmOkres) {

		if (pmAbsencja != null && !pmAbsencja.isEmpty()) {
			for (AbsencjaDTO lvAbs : pmAbsencja) {
				if (lvAbs.getOkres() != null) {
					Interval lvNowyOkres = lvAbs.getOkres().overlap(pmOkres);
					lvAbs.setOkres(lvNowyOkres);
				}
			}
		}
	}

	public static int liczbaDniWAbsencjach(List<AbsencjaDTO> pmAbsencja) {
		return pmAbsencja//
				.stream()//
				.filter(lvAbs -> lvAbs.getOkres() != null)//
				.mapToInt(
						lvAbs -> Days.daysBetween(lvAbs.getOkres().getStart(), lvAbs.getOkres().getEnd()).getDays() + 1)//
				.sum();
	}

}
