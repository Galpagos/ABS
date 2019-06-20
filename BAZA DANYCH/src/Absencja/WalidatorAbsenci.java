package Absencja;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.Interval;

import Datownik.JodaTime;
import Enums.Komunikat;
import Enums.SLMiesiace;
import PrzygotowanieDanych.AbsencjaDTO;

public class WalidatorAbsenci
{
	private AbsencjaRepository mRepo = new AbsencjaRepository();
	private ObslugaAbsencji mObsluga = new ObslugaAbsencji();

	public boolean czyWystepujeAbsencjaWOkresie(AbsencjaDTO pmAbsencja)
	{
		boolean lvWynik = !(mRepo.zliczAbsencjePracownikaWOkresie(pmAbsencja) == 0);
		if (!lvWynik)
			return false;

		Komunikat.Nachodz¹NaSiebieOkresy.pokaz();
		return true;
	}

	public boolean czyPrawidloweDaty(Date pmOd, Date pmDo)
	{
		boolean lvWynik = pmDo.before(pmOd);
		if (lvWynik)
		{
			Komunikat.DataPoPrzedDataPrzed.pokaz();
			return false;
		}

		Calendar lvCalOd = new GregorianCalendar();
		Calendar lvCalDo = new GregorianCalendar();
		lvCalOd.setTime(pmOd);
		lvCalDo.setTime(pmDo);

		if (lvCalOd.get(Calendar.YEAR) != lvCalDo.get(Calendar.YEAR))
		{
			Komunikat.DatayWtymSamymRoku.pokaz();
			return false;
		}
		return true;

	}

	public boolean czyPrzekraczaLimity(AbsencjaDTO pmAbsencja)
	{
		int lvWykorzystany_urlop = mObsluga.ileDniRoboczych(pmAbsencja);
		int lvLimit = 0;
		boolean lvWynik;

		switch (pmAbsencja.getRodzaj())
		{
		case urlop_wypoczynkowy:
			lvLimit = 26;
			lvWykorzystany_urlop = lvWykorzystany_urlop + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;
		case urlop_okolicznoœciowy:
			lvLimit = 2;
			lvWykorzystany_urlop = lvWykorzystany_urlop + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;
		case urlop_ojcowski:
			lvLimit = 14;
			lvWykorzystany_urlop = lvWykorzystany_urlop + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;
		case urlop_rodzicielski:
			lvLimit = 100;
			lvWykorzystany_urlop = lvWykorzystany_urlop + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					new Interval(JodaTime.okresOdDo(new GregorianCalendar(2010, 1, 1).getTime(),
							new GregorianCalendar(2100, 1, 1).getTime())));
			break;
		case urlop_macierzyñski:
			lvLimit = 160;
			lvWykorzystany_urlop = lvWykorzystany_urlop + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					new Interval(JodaTime.okresOdDo(new GregorianCalendar(2010, 1, 1).getTime(),
							new GregorianCalendar(2100, 1, 1).getTime())));
			break;
		case NZ:
			lvLimit = 4;
			lvWykorzystany_urlop = lvWykorzystany_urlop + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;

		default:
			lvLimit = 1;
			lvWykorzystany_urlop = 0;
		}
		lvWynik = (lvWykorzystany_urlop > lvLimit);

		if (lvWynik)
			Komunikat.PrzekroczoneLimity.pokaz();

		return lvWynik;

	}

}
