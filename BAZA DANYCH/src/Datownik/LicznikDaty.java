package Datownik;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

import Absencja.AbsencjaRepository;
import Parsery.ParseryDB;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccess.DniWolneBean;
import dbAccess.dbAccess;

public class LicznikDaty {

	public static int ileDniKalendarzowych(Interval pmOkres) {
		return Math.toIntExact(ChronoUnit.DAYS.between(pmOkres.getStart(), pmOkres.getEnd())) + 1;
	}

	private static int ileDniBezWeekendowOkresie(Interval pmOkres) {
		LocalDate lvStart = pmOkres.getStart();
		int lvRobocze = 0;
		int lvPozostale = ileDniKalendarzowych(pmOkres);
		for (int i = 1; i <= lvPozostale; i++) {
			if (!DayOfWeek.SATURDAY.equals(lvStart.getDayOfWeek()) && !DayOfWeek.SUNDAY.equals(lvStart.getDayOfWeek()))
				lvRobocze++;
			lvStart = lvStart.plusDays(1);
		}
		return lvRobocze;
	}

	private static int ileDniWolnychWOkresie(Interval pmOkres) {
		return new AbsencjaRepository().ileDniWolnych(pmOkres.getStart(), pmOkres.getEnd());
	}

	public static int ileDniRoboczych(Interval pmOkres) {
		return ileDniBezWeekendowOkresie(pmOkres) - ileDniWolnychWOkresie(pmOkres);
	}

	public static int ileDniRobotnych(YearMonth pmMiesiac) {
		return ileDniRoboczych(new Interval(pmMiesiac));
	}

	public static int ileDniRobotnych(List<AbsencjaDTO> pmLista) {
		return pmLista//
				.stream()//
				.mapToInt(lvAbs -> ileDniRoboczych(lvAbs.getOkres()))//
				.sum();
	}

	static int ileDniWolnych(LocalDate pmDataOd, LocalDate pmDataDo) {
		return dbAccess.GetCount(DniWolneBean.NazwaTabeli //
				+ " where " + DniWolneBean.kolumnaData + " BEtween " + ParseryDB.DateParserToSQL_SELECT(pmDataOd)
				+ " and " + ParseryDB.DateParserToSQL_SELECT(pmDataDo));
	}

	public static void filtrujAbsencjePoOkresie(List<AbsencjaDTO> pmAbsencja, Interval pmOkres) {

		if (pmAbsencja != null && !pmAbsencja.isEmpty()) {
			for (AbsencjaDTO lvAbs : pmAbsencja) {
				if (lvAbs.getOkres() != null) {
					Interval lvNowyOkres = lvAbs.getOkres().overlap(pmOkres).orElse(null);
					lvAbs.setOkres(lvNowyOkres);
				}
			}
		}
	}

	public static LocalDate LDTparseFromObject(Object pmData) {
		Timestamp lvStart = (Timestamp) pmData;
		return lvStart.toLocalDateTime().toLocalDate();
	}

	public static int liczbaDniWAbsencjach(List<AbsencjaDTO> pmAbsencja) {
		return pmAbsencja//
				.stream()//
				.filter(lvAbs -> lvAbs.getOkres() != null)//
				.mapToInt(lvAbs -> ileDniKalendarzowych(lvAbs.getOkres()))//
				.sum();
	}

}
