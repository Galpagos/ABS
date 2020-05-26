package Frames.dbAccess.Frames.OknoPracownika;

import ProjektGlowny.commons.Frames.InterfejsAbstractOkno;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaPracownika extends InterfejsAbstractOkno {

	public int getZaznaczenieTabeli();

	public AbsencjaDTO getAbsencjeZTabeli();

	public PracownikDTO getPracownika();

}
