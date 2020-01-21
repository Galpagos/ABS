package Frames.dbAccess.Frames.OknoPracownika;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaPracownika {

	public int getZaznaczenieTabeli();

	public AbsencjaDTO getAbsencjeZTabeli();

	public PracownikDTO getPracownika();

}
