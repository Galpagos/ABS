package Absencja;

import ProjektGlowny.commons.enums.SLMiesiace;
import ProjektGlowny.commons.utils.Interval;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import Pracownik.ObslugaPracownka;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.absencje.AbsencjeRepositoryDB;

@Deprecated
public class WalidatorAbsenci {
	private AbsencjaRepositor mRepo = new AbsencjaRepository();
	private AbsencjeRepositoryDB mRepoAbs = new AbsencjeRepositoryDB();
	private ObslugaAbsencjiDeprecated mObsluga = new ObslugaAbsencjiDeprecated();
	private ObslugaPracownka mObsPrac = new ObslugaPracownka();
	private PracownikRepository mRepoPracownika = new PracownikRepository();

	public Optional<WalidacjeTwarde> czyWystepujeAbsencjaWOkresie(AbsencjaDTO pmAbsencja) {
		boolean lvWynik = !(mRepo.zliczAbsencjePracownikaWOkresie(pmAbsencja) == 0);
		if (!lvWynik)
			return Optional.empty();

		return Optional.of(WalidacjeTwarde.NachodzaNaSiebieOkresy);
	}

	public boolean czyPrawidloweDaty(LocalDate pmOd, LocalDate pmDo) {
		boolean lvWynik = pmDo.isBefore(pmOd);
		if (lvWynik) {
			WalidacjeTwarde.DataPoPrzedDataPrzed.pokaz();
			return false;
		}

		if (pmOd.getYear() != pmDo.getYear()) {
			WalidacjeTwarde.DatayWtymSamymRoku.pokaz();
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
			case urlop_wypoczynkowy :

				lvLimit = mObsPrac.getUrlopNal(pmAbsencja.getIdPracownika());

				lvWykorzystany_urlop = lvWykorzystaneDniRobocze
						+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()))
						+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), SLRodzajeAbsencji.urlop_w_pracy,
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));

				break;
			case urlop_w_pracy :

				lvLimit = mObsPrac.getUrlopNal(pmAbsencja.getIdPracownika());

				lvWykorzystany_urlop = lvWykorzystaneDniRobocze
						+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()))
						+ mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), SLRodzajeAbsencji.urlop_wypoczynkowy,
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));

				break;

			case urlop_ojcowski :
				lvLimit = 14;
				lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(), new Interval(
								new Date(new GregorianCalendar(2010, 1, 1).getTimeInMillis()), new Date(new GregorianCalendar(2100, 1, 1).getTimeInMillis())));
				break;
			case urlop_rodzicielski :
				lvLimit = 224;
				lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(), new Interval(
								new Date(new GregorianCalendar(2010, 1, 1).getTimeInMillis()), new Date(new GregorianCalendar(2100, 1, 1).getTimeInMillis())));
				break;
			case urlop_macierzynski :
				lvLimit = 140;
				lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(), new Interval(
								new Date(new GregorianCalendar(2010, 1, 1).getTimeInMillis()), new Date(new GregorianCalendar(2100, 1, 1).getTimeInMillis())));
				break;
			case NZ :
				lvLimit = 4;
				lvWykorzystany_urlop = lvWykorzystaneDniRobocze + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
						pmAbsencja.getRodzaj(), SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));
				break;
			case opieka_na_dziecko :
				lvLimit = 60;
				lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), pmAbsencja.getRodzaj(),
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()))
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), SLRodzajeAbsencji.opieka_na_kogos,
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));
				break;
			case opieka_na_kogos :
				lvLimit = 14;
				lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe + mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
						pmAbsencja.getRodzaj(), SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));
				break;

			case ciaza :
			case L_4 :
			case szpital :
				lvLimit = wyznaczLimitChorobowy(pmAbsencja.getIdPracownika(), pmAbsencja.getOkres().getStart().getYear());
				lvWykorzystany_urlop = lvWykorzystaneDniKalendarzowe
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), SLRodzajeAbsencji.ciaza,
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()))
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), SLRodzajeAbsencji.szpital,
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()))
						+ mObsluga.ileDniKalendarzowychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(), SLRodzajeAbsencji.L_4,
								SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));
				if (lvLimit <= lvWykorzystany_urlop) {
					JOptionPane.showMessageDialog(null, "Przekroczono limit " + lvLimit + " dni (Wynagrodzenie chorobowe) z dniem: "
							+ mObsluga.dzienKoncaWynagrodzeniaChorobowego(pmAbsencja, lvLimit));;
					lvLimit = 10000;
				}
				break;
			default :
				lvLimit = 10000000;
				lvWykorzystany_urlop = lvWykorzystaneDniRobocze + mObsluga.ileDniRoboczychAbsencjiPracownikaWOkresie(pmAbsencja.getIdPracownika(),
						pmAbsencja.getRodzaj(), SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear()));
		}
		lvWynik = (lvWykorzystany_urlop > lvLimit);

		if (lvWynik && pmAbsencja.getRodzaj() != SLRodzajeAbsencji.ciaza && pmAbsencja.getRodzaj() != SLRodzajeAbsencji.L_4
				&& pmAbsencja.getRodzaj() != SLRodzajeAbsencji.szpital) {
			int reply = JOptionPane.showConfirmDialog(null, "Przekroczono limit " + lvLimit + " dni dla absencji " + pmAbsencja.getRodzaj().getNazwa()
					+ ".\n Uwzględniając wprowadzoną absencje liczba dni nieobecności wynosi " + lvWykorzystany_urlop + "!\n Czy kontynuować wprowadzanie? ",
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

		Date lvDataUr = mRepoPracownika.getDataUrodzenia(pmIdPracownika);
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
		List<AbsencjaDTO> lvLista = mRepoAbs.getAbsencjePracownika(pmAbsencja.getIdPracownika());
		lvLista.add(pmAbsencja);
		List<AbsencjaDTO> lvOgraniczona = lvLista.stream()//
				.filter(lvAbs -> Arrays.asList(SLRodzajeAbsencji.L_4, SLRodzajeAbsencji.szpital, SLRodzajeAbsencji.ciaza).contains(lvAbs.getRodzaj()))//
				.collect(Collectors.toList());

		lvOgraniczona.stream().forEach(lvAbs -> lvAbs.setOkres(//
				lvAbs.getOkres().overlap(new Interval(//
						pmAbsencja.getOkres().getStart().minusDays(lvDni), pmAbsencja.getOkres().getEnd().plusDays(lvDni))).orElse(null)));

		lvOgraniczona.removeIf(lvAbs -> lvAbs.getOkres() == null);
		int licznik = 0;
		for (int i = lvOgraniczona.size() - 1; i >= 0; i--) {
			licznik = licznik + mObsluga.ileDniKalendarzowych(lvOgraniczona.get(i));
			if (licznik >= lvDni) {
				JOptionPane.showMessageDialog(null, "Dla pracownika należy wykonać nowe badania lekarskie ze względu na długą niedyspozycyjność");
				return true;
			}
			if (i > 0 && lvOgraniczona.get(i).getOkres().getStart().minusDays(1).isAfter(lvOgraniczona.get(i - 1).getOkres().getEnd()))
				licznik = 0;
		}

		return false;

	}

}
