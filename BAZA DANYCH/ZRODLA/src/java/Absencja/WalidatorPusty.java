package Absencja;

import java.util.List;
import java.util.function.Function;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;

public class WalidatorPusty extends WalidatorRodzajuAbsencji {

	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return null;
	}

	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return null;
	}

	@Override
	public boolean waliduj(List<AbsencjaDTO> pmLista) {
		return true;
	}

}
