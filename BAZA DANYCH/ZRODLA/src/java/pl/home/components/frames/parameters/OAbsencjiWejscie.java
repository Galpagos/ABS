package pl.home.components.frames.parameters;

import java.util.List;

import ProjektGlowny.commons.Frames.ParametryWejscia;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class OAbsencjiWejscie implements ParametryWejscia {
	private final AbsencjaDTO mAbsencja;
	private final List<PracownikDTO> mListaPracownikow;

}
