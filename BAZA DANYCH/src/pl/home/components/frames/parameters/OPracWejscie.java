package pl.home.components.frames.parameters;

import Frames.dbAccess.Components.ParametryWejscia;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Builder;
import lombok.Getter;

@Builder

public class OPracWejscie implements ParametryWejscia {

	private @Getter PracownikDTO pracownik;
}
