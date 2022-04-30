package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWejscia;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ODanePracownikaWejscie implements ParametryWejscia {
	private PracownikDTO mPracownik;

}
