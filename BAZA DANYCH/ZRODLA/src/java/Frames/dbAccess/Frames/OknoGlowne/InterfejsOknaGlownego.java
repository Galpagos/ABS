package Frames.dbAccess.Frames.OknoGlowne;

import java.util.List;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaGlownego {

	void odswiezKontrolki();

	int getZaznaczenieTabeli();

	PracownikDTO getPracownikZTabeli();

	void odswiezTabele();

	List<PracownikDTO> getPracownicyZTabeli();

}
