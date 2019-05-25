package dbAccess.Frames.OknoGlowne;

import PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaGlownego
{

	void odswiezTabele();

	int getZaznaczenieTabeli();

	PracownikDTO getPracownikZTabeli();

}
