package dbAccess.Frames.OknoPracownika;

import javax.swing.JLabel;

import PrzygotowanieDanych.PracownikDTO;
import dbAccess.Absencja;

public interface InterfejsOknaPracownika
{
	public PracownikDTO getPracownika();

	public int getZaznaczenieTabeli();

	public Absencja getAbsencjeZTabeli();

	public void odswiezTabele();

	public JLabel getLblGrupy();
}
