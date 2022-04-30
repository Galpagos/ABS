package pl.home.absencje;

import ProjektGlowny.commons.Components.SilentException;
import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import java.time.LocalDate;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;

public class WalidatorAbsencji {

	WalidatorAbsencji() {
	}

	private AbsencjeRepositoryDB mRepoAbs;

	WalidatorAbsencji setAbsencjeRepository(AbsencjeRepositoryDB pmRepo) {
		mRepoAbs = pmRepo;
		return this;
	}

	public String waliduj(List<AbsencjaDTO> pmListaAbsencji) {

		List<Boolean> lvLista = new ArrayList<>();
		Map<Integer, List<AbsencjaDTO>> lvZgrupowaneAbsencje = pmListaAbsencji//
				.stream()//
				.collect(Collectors.groupingBy(AbsencjaDTO::getIdPracownika));

		StringBuilder lvKomunikat = new StringBuilder();
		lvZgrupowaneAbsencje.forEach((k, v) -> {
			try {
				lvLista.add(walidujPracownika(v));
			} catch (SilentException lvE) {

				lvKomunikat.append("Dla pracownika nie można wprowadzić absencji \n"//
						+ lvE.getMessage());
			}
		});

		return lvKomunikat.toString();
	}

	private boolean walidujPracownika(List<AbsencjaDTO> pmLista) throws SilentException {
		if (pmLista.isEmpty())
			return true;

		walidujDatyAbsencji(pmLista);

		List<AbsencjaDTO> lvListaAbsencjiDB = pobierzAbsencjePracownika(pmLista);
		walidujNakladanieAbsencji(lvListaAbsencjiDB, pmLista);

		walidujLimity(pmLista, lvListaAbsencjiDB);
		return true;

	}

	private void walidujLimity(List<AbsencjaDTO> pmLista, List<AbsencjaDTO> lvListaAbsencjiDB) throws SilentException {
		List<AbsencjaDTO> lvNoweAbsencje = mergujAbsencje(lvListaAbsencjiDB, pmLista);

		Set<SLRodzajeAbsencji> lvZmienianeRodzajeAbs = pmLista//
				.stream()//
				.map(AbsencjaDTO::getRodzaj)//
				.collect(Collectors.toSet());

		Integer lvRok = pmLista.get(0).getStart().getYear();

		for (SLRodzajeAbsencji lvRodzaj : lvZmienianeRodzajeAbs)
			waliduj(lvRodzaj, lvNoweAbsencje, lvRok);
	}

	private void waliduj(SLRodzajeAbsencji pmRodzaj, List<AbsencjaDTO> pmNoweAbsencje, Integer pmRok) throws SilentException {
		if(pmRodzaj==null)
			return;
		pmRodzaj.getWalidatorFunction().apply(pmRok).walidujAndThrow(pmNoweAbsencje);
	}

	private List<AbsencjaDTO> mergujAbsencje(List<AbsencjaDTO> pmListaAbsencjiDB, List<AbsencjaDTO> pmLista) {

		List<AbsencjaDTO> lvNowaLista = new ArrayList<>();
		lvNowaLista.addAll(pmLista);

		List<Integer> lvListaId = pmLista//
				.stream()//
				.map(AbsencjaDTO::getId)//
				.collect(Collectors.toList());

		lvNowaLista.addAll(pmListaAbsencjiDB//
				.stream()//
				.filter(lvAbs -> !lvListaId.contains(Integer.valueOf(lvAbs.getId())))//
				.collect(Collectors.toList()));

		return lvNowaLista;
	}

	private void walidujNakladanieAbsencji(List<AbsencjaDTO> pmListaAbsencjiDB, List<AbsencjaDTO> pmLista) throws SilentException {
		for (AbsencjaDTO lvAbsencja : pmLista) {
			Optional<Interval> lvNakladanie = pmListaAbsencjiDB//
					.stream()//
					.filter(lvAbs -> lvAbsencja.getId() != lvAbs.getId())//
					.map(AbsencjaDTO::getOkres)//
					.filter(lvOkres -> lvOkres.overlap(lvAbsencja.getOkres()).isPresent())//
					.findAny();
			if (lvNakladanie.isPresent())
				throw new SilentException("Występuje już absencja w okresie " + lvNakladanie.get());
		}
	}

	private List<AbsencjaDTO> pobierzAbsencjePracownika(List<AbsencjaDTO> pmLista) {
		return mRepoAbs.getAbsencjePracownika(pmLista.get(0).getIdPracownika());
	}

	private void walidujDatyAbsencji(List<AbsencjaDTO> pmLista) throws SilentException {
		LocalDate lvDataOd = pmLista//
				.stream()//
				.map(lvAbsencja -> lvAbsencja.getOkres().getStart())//
				.sorted()//
				.findFirst()//
				.get();

		LocalDate lvDataDo = pmLista//
				.stream()//
				.map(lvAbsencja -> lvAbsencja.getOkres().getStart())//
				.sorted()//
				.reduce((first, second) -> second)//
				.get();

		if (lvDataDo.getYear() != lvDataOd.getYear())
			throw new SilentException("Zakres zmienianych absencji obejmuje przełom roku!\nNależy wprowadzić każdy rok osobno.");
	}
}
