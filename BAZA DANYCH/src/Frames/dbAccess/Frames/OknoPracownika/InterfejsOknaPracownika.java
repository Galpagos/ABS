package Frames.dbAccess.Frames.OknoPracownika;

import javax.swing.JLabel;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsOknaPracownika {
	public PracownikDTO getPracownika();

	public int getZaznaczenieTabeli();

	public AbsencjaDTO getAbsencjeZTabeli();

	public void odswiezTabele();

	public JLabel getLblGrupy();
}
