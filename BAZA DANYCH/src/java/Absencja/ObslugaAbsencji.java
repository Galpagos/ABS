package Absencja;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import Datownik.Interval;
import Datownik.LicznikDaty;
import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.mainframes.OknoAbsencji;
import pl.home.components.frames.parameters.OAbsencjiWejscie;

public class ObslugaAbsencji {
	private AbsencjaRepositor mRepo = new AbsencjaRepository();

	public List<AbsencjaDTO> pobierzAbsencjePracownika(int pmId) {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		Object[][] lvDane = mRepo.getAbsencjePracownika(pmId);
		for (int i = 0; i < lvDane.length; i++) {
			AbsencjaDTO lvAbs = AbsencjaDTO.builder()//
					.setId((int) lvDane[i][0])//
					.setIdPracownika((int) lvDane[i][1])//
					.setRodzaj(SLRodzajeAbsencji.getByKod((String) lvDane[i][4]))//
					.setProcent(SLEkwiwalentZaUrlop.getByKod(lvDane[i][5] == null ? null : lvDane[i][5].toString()))
					.setOkres(Interval.OkreszBazy(lvDane[i][2], lvDane[i][3]));

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
				.setRodzaj(SLRodzajeAbsencji.getByKod((String) lvDane[0][4]))//
				.setProcent(SLEkwiwalentZaUrlop.getByKod(lvDane[0][5] == null ? "0" : lvDane[0][5].toString()))
				.setOkres(Interval.OkreszBazy(lvDane[0][2], lvDane[0][3]));
	}

	int ileDniRoboczych(AbsencjaDTO pmAbs) {
		if (pmAbs.getOkres() == null)
			return 0;
		return LicznikDaty.ileDniRoboczych(pmAbs.getOkres());
	}

	int ileDniRoboczychAbsencjiPracownikaWOkresie(int pmIdPracownika, SLRodzajeAbsencji pmRodzaj, Interval pmOkres) {
		List<AbsencjaDTO> lvLista = pobierzAbsencjePracownika(pmIdPracownika).stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == pmRodzaj)//
				.collect(Collectors.toList());

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres().overlap(pmOkres).orElse(null)));

		int lvWynik = 0;
		Map<SLRodzajeAbsencji, Integer> lvMapa = zliczDniKalendarzoweWAbsencjach(lvLista);
		if (lvMapa.containsKey(pmRodzaj))
			lvWynik = lvMapa.get(pmRodzaj);
		return lvWynik;
	}

	public void modyfikujAbsencje(AbsencjaDTO pmAbs) {
		if (pmAbs != null) {
			OAbsencjiWejscie lvParams = OAbsencjiWejscie.builder().withAbsencja(pmAbs).build();
			new OknoAbsencji(lvParams);
		}
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

	int ileDniKalendarzowychAbsencjiPracownikaWOkresie(int pmIdPracownika, SLRodzajeAbsencji pmRodzaj,
			Interval pmOkres) {
		List<AbsencjaDTO> lvLista = pobierzAbsencjePracownika(pmIdPracownika).stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == pmRodzaj)//
				.collect(Collectors.toList());

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
				.collect(Collectors.groupingBy(AbsencjaDTO::getRodzaj,
						Collectors.summingInt(this::ileDniKalendarzowych)));
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
		List<AbsencjaDTO> lvLista = pobierzAbsencjePracownika(pmAbsencja.getIdPracownika());
		lvLista.add(pmAbsencja);
		lvLista = lvLista.stream()//
				.filter(lvAbs -> lvAbs.getRodzaj() == SLRodzajeAbsencji.szpital
						|| lvAbs.getRodzaj() == SLRodzajeAbsencji.L_4 || lvAbs.getRodzaj() == SLRodzajeAbsencji.ci¹¿a)//
				.sorted(Comparator.comparing(AbsencjaDTO::getStart)).collect(Collectors.toList());

		lvLista.stream().forEach(lvAbs2 -> lvAbs2.setOkres(lvAbs2.getOkres()
				.overlap(SLMiesiace.N00_ROK.getOkres(pmAbsencja.getOkres().getStart().getYear())).orElse(null)));

		lvLista.stream().forEach(lvAbs -> {
			licznik = licznik - ileDniKalendarzowych(lvAbs);
			if (licznik <= 0)
				lvDataGraniczna = lvAbs.getOkres().getEnd().plusDays(licznik);

		});
		return lvDataGraniczna;
	}
}