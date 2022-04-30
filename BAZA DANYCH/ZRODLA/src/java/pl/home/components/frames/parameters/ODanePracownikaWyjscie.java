package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWyjscia;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
@Accessors(fluent = true)
@Getter
@Setter
public class ODanePracownikaWyjscie extends ParametryWyjscia {
	private PracownikDTO mPracownik;
}
