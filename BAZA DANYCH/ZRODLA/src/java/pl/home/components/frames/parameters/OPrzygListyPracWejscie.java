package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWejscia;

import java.util.List;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class OPrzygListyPracWejscie implements ParametryWejscia {

	private List<PracownikDTO> mLista;
	private String mNazwa = "Wybierz pracowników";
	private boolean mCzyTrybSzybki;
}
