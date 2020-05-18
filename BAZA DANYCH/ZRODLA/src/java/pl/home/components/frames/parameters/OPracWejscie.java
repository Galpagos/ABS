package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWejscia;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Builder;
import lombok.Getter;

@Builder

public class OPracWejscie implements ParametryWejscia {

	private @Getter PracownikDTO mPracownik;
}
