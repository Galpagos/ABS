package Absencja;

import ProjektGlowny.commons.utils.Interval;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.time.LocalDate;

import Datownik.LicznikDaty;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLMiesiace;
import enums.SLRodzajeAbsencji;
import pl.home.absencje.AbsencjeRepositoryDB;

@Deprecated
public class ObslugaAbsencjiDeprecated {
	private AbsencjeRepositoryDB mRepoAbs = new AbsencjeRepositoryDB();
	int ileDniRoboczych(AbsencjaDTO pmAbs) {
		if (pmAbs.getOkres() == null)
			return 0;
		return LicznikDaty.ileDniRoboczych(pmAbs.getOkres());
	}

	int ileDniRoboczychAbsencjiPracownikaWOkresie(int pmIdPracownika, SLRodzajeAbsencji pmRodzaj, Interval pmOkres) {
		List<AbsencjaDTO> lvLista = mRepoAbs.getAbsencjePracownika(pmIdPracownika);

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres().overlap(pmOkres).orElse(null)));

		int lvWynik = 0;
		Map<SLRodzajeAbsencji, Integer> lvMapa = zliczDniKalendarzoweWAbsencjach(lvLista);
		if (lvMapa.containsKey(pmRodzaj))
			lvWynik = lvMapa.get(pmRodzaj);
		return lvWynik;
	}

	int ileDniKalendarzowychAbsencjiPracownikaWOkresie(int pmIdPracownika, SLRodzajeAbsencji pmRodzaj, Interval pmOkres) {
		List<AbsencjaDTO> lvLista = mRepoAbs.getAbsencjePracownika(pmIdPracownika);

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres().overlap(pmOkres).orElse(null)));
		int lvWynik = 0;
		Map<SLRodzajeAbsencji, Integer> lvMapa = zliczDniKalendarzoweWAbsencjach(lvLista);
		if (lvMapa.containsKey(pmRodzaj))
			lvWynik = lvMapa.get(pmRodzaj);
		return lvWynik;
	}

	private Map<SLRodzajeAbsencji, Integer> zliczDniKalendarzoweWAbsencjach(List<AbsencjaDTO> pmLista) {
		return pmLista //
				.stream() //
				.collect(Collectors.groupingBy(AbsencjaDTO::getRodzaj, Collectors.summingInt(this::ileDniKalendarzowych)));
	}

	int ileDniKalendarzowych(AbsencjaDTO pmAbsencja) {
		if (pmAbsencja.getOkres() == null)
			return 0;
		return LicznikDaty.ileDniKalendarzowych(pmAbsencja.getOkres());
	}

	private LocalDate lvDataGraniczna = null;
	private int licznik = 33;

	LocalDate dzienKoncaWynagrodzeniaChorobowego(AbsencjaDTO pmAbsencja, int pmLimit) {
		licznik = pmLimit;
		List<AbsencjaDTO> lvLista = mRepoAbs.getAbsencjePracownika(pmAbsencja.getIdPracownika());
		lvLista.add(pmAbsencja);
		lvLista = lvLista.stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == SLRodzajeAbsencji.szpital || lvAbs.getRodzaj() == SLRodzajeAbsencji.L_4
						|| lvAbs.getRodzaj() == SLRodzajeAbsencji.ciaza)//
				.sorted(Comparator.comparing(AbsencjaDTO::getStart)).collect(Collectors.toList());

		lvLista.stream().forEach(
				lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres().overlap(SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear())).orElse(null)));

		lvLista.stream().forEach(lvAbs -> {
			licznik = licznik - ileDniKalendarzowych(lvAbs);
			if (licznik <= 0)
				lvDataGraniczna = lvAbs.getOkres().getEnd().plusDays(licznik);

		});
		return lvDataGraniczna;
	}
}