package Absencja;

import ProjektGlowny.commons.Components.SilentException;
import ProjektGlowny.commons.enums.SLMiesiace;
import ProjektGlowny.commons.utils.Interval;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;

public abstract class WalidatorRodzajuAbsencji {

	protected Interval mOkres;
	protected long mWykorzystane;
	protected long mLimit;

	abstract List<SLRodzajeAbsencji> ograniczRodzaje();
	abstract Function<Integer, Integer> getLimitPracownika();

	public boolean waliduj(List<AbsencjaDTO> pmLista) {
		mWykorzystane = getWykorzystane(pmLista);
		mLimit = getLimitPracownika().apply(pmLista.get(0).getIdPracownika());
		return mWykorzystane <= mLimit;
	}

	protected long getWykorzystane(List<AbsencjaDTO> pmLista) {
		return pmLista//
				.stream()//
				.filter(lvAbs -> ograniczRodzaje().contains(lvAbs.getRodzaj()))//
				.map(lvAbs -> mOkres != null ? mOkres.overlap(lvAbs.getOkres()) : Optional.of(lvAbs.getOkres()))//
				.filter(Optional::isPresent)//
				.map(Optional::get)//
				.mapToLong(Interval::getLiczbaDniKalendarzowych)//
				.sum();
	}

	public void walidujAndThrow(List<AbsencjaDTO> pmLista) throws SilentException {

		if (!waliduj(pmLista))
			throw new SilentException(getError());
	}

	private String getError() {
		return "Przekroczono limit " + mLimit + " dla absencji:" + listToList() + "\n\n Wykorzystano " + mWykorzystane + " dni.";
	}

	public WalidatorRodzajuAbsencji setRok(Integer pmRok) {
		mOkres = SLMiesiace.N00_ROK.getOkres(pmRok);
		return this;
	}

	private String listToList() {
		StringBuilder lvString = new StringBuilder();
		for (SLRodzajeAbsencji lvRodzaj : ograniczRodzaje())
			lvString.append("\n - " + lvRodzaj.getNazwa());
		return lvString.toString();
	}

}
