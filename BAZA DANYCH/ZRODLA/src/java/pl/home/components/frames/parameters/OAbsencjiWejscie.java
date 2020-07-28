package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWejscia;

import java.util.List;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder(setterPrefix = "with")
@Getter
@NonNull
public class OAbsencjiWejscie implements ParametryWejscia {

	private final AbsencjaDTO mAbsencja;
	private final List<PracownikDTO> mListaPracownikow;

}
