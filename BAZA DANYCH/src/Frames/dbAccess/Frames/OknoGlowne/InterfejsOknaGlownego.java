package Frames.dbAccess.Frames.OknoGlowne;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaGlownego {

	void odswiezTabele();

	int getZaznaczenieTabeli();

	PracownikDTO getPracownikZTabeli();

}
