package Frames.dbAccess.Frames.OknoGlowne;

import ProjektGlowny.commons.Frames.InterfejsAbstractOkno;

import java.util.List;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaGlownego extends InterfejsAbstractOkno {

	void odswiezKontrolki();

	int getZaznaczenieTabeli();

	PracownikDTO getPracownikZTabeli();

	void odswiezTabele();

	List<PracownikDTO> getPracownicyZTabeli();

}
