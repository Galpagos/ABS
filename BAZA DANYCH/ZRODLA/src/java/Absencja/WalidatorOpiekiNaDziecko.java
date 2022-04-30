package Absencja;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;

public class WalidatorOpiekiNaDziecko extends WalidatorRodzajuAbsencji {

	private static Integer LIMIT = 60;
	private WalidatorRodzajuAbsencji mWalidatorNaKogos;

	WalidatorOpiekiNaDziecko setWalidatorNaKogos(WalidatorRodzajuAbsencji pmWalidator) {
		mWalidatorNaKogos = pmWalidator;
		return this;
	}

	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return Arrays.asList(SLRodzajeAbsencji.opieka_na_dziecko, SLRodzajeAbsencji.opieka_na_kogos);
	}

	@Override
	public boolean waliduj(List<AbsencjaDTO> pmLista) {

		return super.waliduj(pmLista) && mWalidatorNaKogos.waliduj(pmLista);

	}
	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return lvId -> LIMIT;
	}

}
