package Absencja;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.joda.time.Interval;

import Datownik.JodaTime;
import Enums.Komunikat;
import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import Pracownik.ObslugaPracownka;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public class WalidatorAbsenci {
	private AbsencjaRepositor mRepo = new AbsencjaRepository();
	private ObslugaAbsencji mObsluga = new ObslugaAbsencji();
	private ObslugaPracownka mObsPrac = new ObslugaPracownka();

	public boolean czyWystepujeAbsencjaWOkresie(AbsencjaDTO pmAbsencja) {
		boolean lvWynik = !(mRepo.zliczAbsencjePracownikaWOkresie(pmAbsencja) == 0);
		if (!lvWynik)
			return false;

		Komunikat.Nachodz¹NaSiebieOkresy.pokaz();
		return true;
	}

	public boolean czyPrawidloweDaty(Date pmOd, Date pmDo) {
		boolean lvWynik = pmDo.before(pmOd);
		if (lvWynik) {
			Komunikat.DataPoPrzedDataPrzed.pokaz();
			return false;
		}

		Calendar lvCalOd = new GregorianCalendar();
		Calendar lvCalDo = new GregorianCalendar();
		lvCalOd.setTime(pmOd);
		lvCalDo.setTime(pmDo);

		if (lvCalOd.get(Calendar.YEAR) != lvCalDo.get(Calendar.YEAR)) {
			Komunikat.DatayWtymSamymRoku.pokaz();
			return false;
		}
		return true;

	}

	public boolean czyPrzekraczaLimity(AbsencjaDTO pmAbsencja) {
		int lvWykorzystany_urlop = 0;
		int lvWykorzystaneDniRobocze = mObsluga.ileDniRoboczych(pmAbsencja);
		int lvWykorzystaneDniKalendarzowe = mObsluga.ileDniKalendarzowych(pmAbsencja);
		int lvLimit = 0;
		boolean lvWynik;

		switch (pmAbsencja.getRodzaj()) {
		case urlop_wypoczynkowy:

			lvLimit = mObsPrac.getUrlopNal(pmAbsencja.getIdPracownika());

			lvWykorzystany_urlop = lvWykorzystaneDniRobocze
					+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(), SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()))
					+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							SLRodzajeAbsencji.urlop_w_pracy,
							SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));

			break;
		case urlop_w_pracy:

			lvLimit = mObsPrac.getUrlopNal(pmAbsencja.getIdPracownika());

			lvWykorzystany_urlop = lvWykorzystaneDniRobocze
					+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(), SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()))
					+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							SLRodzajeAbsencji.urlop_wypoczynkowy,
							SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));

			break;

		case urlop_ojcowski:// ok
			lvLimit = 14;
			lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(),
							new Interval(JodaTime.okresOdDo(new GregorianCalendar(2010, 1, 1).getTime(),
									new GregorianCalendar(2100, 1, 1).getTime())));
			break;
		case urlop_rodzicielski:
			lvLimit = 224;
			lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(),
							new Interval(JodaTime.okresOdDo(new GregorianCalendar(2010, 1, 1).getTime(),
									new GregorianCalendar(2100, 1, 1).getTime())));
			break;
		case urlop_macierzyñski:
			lvLimit = 140;
			lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(),
							new Interval(JodaTime.okresOdDo(new GregorianCalendar(2010, 1, 1).getTime(),
									new GregorianCalendar(2100, 1, 1).getTime())));
			break;
		case NZ:// ok
			lvLimit = 4;
			lvWykorzystany_urlop = lvWykorzystaneDniRobocze + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;
		case opieka_na_dziecko:
			lvLimit = 60;
			lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(), SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()))
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							SLRodzajeAbsencji.opieka_na_kogos,
							SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;
		case opieka_na_kogos:
			lvLimit = 14;
			lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							pmAbsencja.getRodzaj(),
							SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			break;

		case ci¹¿a:
		case L_4:
		case szpital:
			lvLimit = wyznaczLimitChorobowy(pmAbsencja.getIdPracownika(), pmAbsencja.getOkres().getStart().getYear());
			lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							SLRodzajeAbsencji.ci¹¿a,
							SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()))
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							SLRodzajeAbsencji.szpital,
							SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()))
					+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
							SLRodzajeAbsencji.L_4, SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
			if (lvLimit <= lvWykorzystany_urlop) {
				JOptionPane.showMessageDialog(null,
						"Przekroczono limit " + lvLimit + " dni (Wynagrodzenie chorobowe) z dniem: "
								+ mObsluga.dzienKoncaWynagrodzeniaChorobowego(pmAbsencja, lvLimit));
				;
				lvLimit = 10000;
			}
			break;
		default:
			lvLimit = 10000000;
			lvWykorzystany_urlop = lvWykorzystaneDniRobocze + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(
					pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
					SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()));
		}
		lvWynik = (lvWykorzystany_urlop > lvLimit);

		if (lvWynik && pmAbsencja.getRodzaj() != SLRodzajeAbsencji.ci¹¿a
				&& pmAbsencja.getRodzaj() != SLRodzajeAbsencji.L_4
				&& pmAbsencja.getRodzaj() != SLRodzajeAbsencji.szpital) {
			int reply = JOptionPane.showConfirmDialog(null,
					"Przekroczono limit " + lvLimit + " dni dla absencji " + pmAbsencja.getRodzaj().getNazwa()
							+ ".\n Uwzglêdniaj¹c wprowadzon¹ absencje liczba dni nieobecnoœci wynosi "
							+ lvWykorzystany_urlop + "!\n Czy kontynuowaæ wprowadzanie? ",
					"Potwierdzenie operacji", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				return false;
			} else {
				return true;
			}
		}
		return lvWynik;
	}

	private int wyznaczLimitChorobowy(int pmIdPracownika, int pmRok) {

		Date lvDataUr = mObsPrac.getDataUrodzenia(pmIdPracownika);
		if (lvDataUr != null) {
			Calendar lvCalendarz = new GregorianCalendar();
			lvCalendarz.setTime(lvDataUr);
			if (pmRok - lvCalendarz.get(Calendar.YEAR) <= 50)
				return 33;
		}
		return 14;
	}

	public boolean czyDniL4Ciagiem(AbsencjaDTO pmAbsencja) {
		int lvDni = 33;
		List<AbsencjaDTO> lvLista = mObsluga.pobierzAbsencjePracownika(pmAbsencja.getIdPracownika());
		lvLista.add(pmAbsencja);
		List<AbsencjaDTO> lvOgraniczona = lvLista.stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == SLRodzajeAbsencji.L_4
						|| lvAbs.getRodzaj() == SLRodzajeAbsencji.szpital
						|| lvAbs.getRodzaj() == SLRodzajeAbsencji.ci¹¿a)//
				.sorted(Comparator.comparing(AbsencjaDTO::getStart))//
				.collect(Collectors.toList());

		lvOgraniczona.stream()
				.forEach(lvAbs -> lvAbs.setOkres(lvAbs.getOkres()
						.overlap(JodaTime.okresOdDo(pmAbsencja.getOkres().getStart().minusDays(lvDni).toDate(),
								pmAbsencja.getOkres().getEnd().plusDays(lvDni).toDate()))));

		lvOgraniczona.removeIf(lvAbs -> lvAbs.getOkres() == null);
		int licznik = 0;
		for (int i = lvOgraniczona.size() - 1; i >= 0; i--) {
			licznik = licznik + mObsluga.ileDniKalendarzowych(lvOgraniczona.get(i));
			if (licznik >= lvDni) {
				JOptionPane.showMessageDialog(null,
						"Dla pracownika nale¿y wykonaæ nowe badania lekarskie ze wzglêdu na d³ug¹ niedyspozycyjnoœæ");
				return true;
			}
			if (i > 0 && lvOgraniczona.get(i).getOkres().getStart().minusDays(1).minusHours(10)
					.isAfter(lvOgraniczona.get(i - 1).getOkres().getEnd()))
				licznik = 0;
		}

		return false;

	}

}
