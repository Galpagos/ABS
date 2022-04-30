package pl.home.absencje;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;

public class WalidatorAbsencjiTest {

	WalidatorAbsencji mWalidator;
	@Mock
	AbsencjeRepositoryDB mRepo;
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mWalidator = new WalidatorAbsencji().setAbsencjeRepository(mRepo);
	}

	@Test
	public void pustaListaOk() {
		String lvWynik = mWalidator.waliduj(Collections.emptyList());
		assertTrue("".equals(lvWynik));
	}

	@Test
	public void walidacjaNaPrzelomRoku() {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		lvLista.add(AbsencjaDTO.builder().setIdPracownika(1).setOkres(new Interval(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 2))));
		lvLista.add(AbsencjaDTO.builder().setIdPracownika(1).setOkres(new Interval(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 2, 2))));

		String lvWynik = mWalidator.waliduj(lvLista);
		assertFalse("".equals(lvWynik));
	}

	@Test
	@Disabled
	public void walidacjaNaNakladanieOkresow() {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		lvLista.add(AbsencjaDTO.builder().setId(1).setRodzaj(SLRodzajeAbsencji.urlop_wychowawczy).setIdPracownika(1)
				.setOkres(new Interval(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 2))));

		List<AbsencjaDTO> lvLista2 = new ArrayList<>();
		lvLista2.add(AbsencjaDTO.builder().setId(2).setRodzaj(SLRodzajeAbsencji.urlop_wychowawczy).setIdPracownika(1)
				.setOkres(new Interval(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 2))));

		Mockito.doReturn(lvLista2).when(mRepo).getWorkerAbsenceInTerms(Mockito.anyInt(), Mockito.any());
		String lvWynik = mWalidator.waliduj(lvLista);
		assertFalse("".equals(lvWynik));
	}

	@Test
	public void brakWalidacjiNaZmianeOkresow() {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		lvLista.add(AbsencjaDTO.builder().setId(1).setIdPracownika(1).setOkres(new Interval(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 2))));

		Mockito.doReturn(lvLista).when(mRepo).getWorkerAbsenceInTerms(Mockito.anyInt(), Mockito.any());
		String lvWynik = mWalidator.waliduj(lvLista);
		assertTrue("".equals(lvWynik));
	}
}
