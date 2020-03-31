package Frames.dbAccess.Frames.OknoGlowne;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaGlownego {

	void odswiezKontrolki();

	int getZaznaczenieTabeli();

	PracownikDTO getPracownikZTabeli();

	void odswiezTabele();

}
