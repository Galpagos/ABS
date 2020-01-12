package Absencja;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Frames.Absencja.OknoAbsencji;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public class ObslugaAbsencji {
	AbsencjaRepositor mRepo = new AbsencjaRepository();

	public List<AbsencjaDTO> pobierzAbsencjePracownika(int pmId) {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		Object[][] lvDane = mRepo.getAbsencjePracownika(pmId);
		for (int i = 0; i < lvDane.length; i++) {
			AbsencjaDTO lvAbs = AbsencjaDTO.builder()//
					.setId((int) lvDane[i][0])//
					.setIdPracownika((int) lvDane[i][1])//
					.setRodzaj(SLRodzajeAbsencji.AbsencjaPoNazwie((String) lvDane[i][4]))//
					.setOkres(Datownik.LicznikDaty.OkreszBazy(lvDane[i][2], lvDane[i][3]));
			lvLista.add(lvAbs);
		}
		return lvLista;
	}

	public AbsencjaDTO pobierzAbsencjePoId(int pmId) {
		Object[][] lvDane = mRepo.getAbsencjePoId(pmId);
		if (lvDane.length < 1)
			return null;
		return AbsencjaDTO.builder()//
				.setId((int) lvDane[0][0])//
				.setIdPracownika((int) lvDane[0][1])//
				.setRodzaj(SLRodzajeAbsencji.AbsencjaPoNazwie((String) lvDane[0][4]))//
				.setOkres(Datownik.LicznikDaty.OkreszBazy(lvDane[0][2], lvDane[0][3]));
	}

	private int ileDniBezWeekendowOkresie(Interval pmOkres) {
		DateTime lvStart = pmOkres.getStart();
		long lvRobocze = 0;
		lvRobocze = (pmOkres.toDuration().getStandardDays() / 7l) * 5l;
		long lvPozostale = pmOkres.toDuration().getStandardDays() % 7l;
		for (int i = 0; i <= lvPozostale; i++) {
			if (lvStart.getDayOfWeek() != 6 && lvStart.getDayOfWeek() != 7)
				lvRobocze++;
			lvStart = lvStart.plusDays(1);
		}
		return (int) lvRobocze;
	}

	private int ileDniWolnychWOkresie(Interval pmOkres) {
		return mRepo.ileDniWolnych(pmOkres.getStart().toDate(), pmOkres.getEnd().toDate());
	}

	public int ileDniRoboczych(Interval pmOkres) {
		return ileDniBezWeekendowOkresie(pmOkres) - ileDniWolnychWOkresie(pmOkres);
	}

	public int ileDniRoboczych(AbsencjaDTO pmAbs) {
		if (pmAbs.getOkres() == null)
			return 0;
		return ileDniRoboczych(pmAbs.getOkres());
	}

	public Map<SLRodzajeAbsencji, Integer> zliczDniRoboczeWAbsencjach(List<AbsencjaDTO> pmLista) {
		return pmLista //
				.stream() //
				.collect(Collectors.groupingBy(AbsencjaDTO::getRodzaj, Collectors.summingInt(this::ileDniRoboczych)));
	}

	public int ileDniRoboczychAbsencjiPracownikaWOkresie(int pmIdPracownika, SLRodzajeAbsencji pmRodzaj,
			Interval pmOkres) {
		List<AbsencjaDTO> lvLista = pobierzAbsencjePracownika(pmIdPracownika).stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == pmRodzaj)//
				.collect(Collectors.toList());

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres().overlap(pmOkres)));

		int lvWynik = 0;
		Map<SLRodzajeAbsencji, Integer> lvMapa = zliczDniKalendarzoweWAbsencjach(lvLista);
		if (lvMapa.containsKey(pmRodzaj))
			lvWynik = lvMapa.get(pmRodzaj);
		return lvWynik;
	}

	public void modyfikujAbsencje(AbsencjaDTO pmAbs) {
		if (pmAbs != null)
			new OknoAbsencji(pmAbs);
	}

	public void dodajAbsencje(AbsencjaDTO pmAbs) {
		if (pmAbs != null) {

			mRepo.dodajAbsencje(pmAbs);
		}
	}

	public void usunAbsencje(int pmID, boolean pmPotwierdzone) {
		if (pmPotwierdzone || JOptionPane.showConfirmDialog(null, "Czy na pewno usun¹æ?") == JOptionPane.YES_OPTION)
			mRepo.usunAbsencje(pmID);
	}

	public int ileDniKalendarzowychAbsencjiPracownikaWOkresie(int pmIdPracownika, SLRodzajeAbsencji pmRodzaj,
			Interval pmOkres) {
		List<AbsencjaDTO> lvLista = pobierzAbsencjePracownika(pmIdPracownika).stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == pmRodzaj)//
				.collect(Collectors.toList());

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres().overlap(pmOkres)));
		int lvWynik = 0;
		Map<SLRodzajeAbsencji, Integer> lvMapa = zliczDniKalendarzoweWAbsencjach(lvLista);
		if (lvMapa.containsKey(pmRodzaj))
			lvWynik = lvMapa.get(pmRodzaj);
		return lvWynik;
	}

	public Map<SLRodzajeAbsencji, Integer> zliczDniKalendarzoweWAbsencjach(List<AbsencjaDTO> pmLista) {
		return pmLista //
				.stream() //
				.collect(Collectors.groupingBy(AbsencjaDTO::getRodzaj,
						Collectors.summingInt(this::ileDniKalendarzowych)));
	}

	public int ileDniKalendarzowych(AbsencjaDTO pmAbsencja) {
		if (pmAbsencja.getOkres() == null)
			return 0;
		return Math.toIntExact(pmAbsencja.getOkres().toDuration().getStandardDays()) + 1;
	}

	private DateTime lvDataGraniczna = null;
	private int licznik = 33;

	public LocalDate dzienKoncaWynagrodzeniaChorobowego(AbsencjaDTO pmAbsencja, int pmLimit) {
		licznik = pmLimit;
		List<AbsencjaDTO> lvLista = pobierzAbsencjePracownika(pmAbsencja.getIdPracownika());
		lvLista.add(pmAbsencja);
		lvLista = lvLista.stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == SLRodzajeAbsencji.szpital
						|| lvAbs.getRodzaj() == SLRodzajeAbsencji.L_4 || lvAbs.getRodzaj() == SLRodzajeAbsencji.ci¹¿a)//
				.sorted(Comparator.comparing(AbsencjaDTO::getStart)).collect(Collectors.toList());

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(
				lvAbs2.getOkres().overlap(SLMiesiace.Rok.getOkres(pmAbsencja.getOkres().getStart().getYear()))));

		lvLista.stream().forEach(lvAbs -> {
			licznik = licznik - ileDniKalendarzowych(lvAbs);
			if (licznik <= 0)
				lvDataGraniczna = lvAbs.getOkres().getEnd().plusDays(licznik);

		});
		return lvDataGraniczna.toLocalDate();
	}
}