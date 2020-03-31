package Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import java.util.List;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

public interface InterfejsPrzygotowaniaListyPracownikow {
	public void odswiezTabLewa();

	public List<PracownikDTO> getListaLewa();

	public void setListaLewa(List<PracownikDTO> pmLista);

	public List<PracownikDTO> getListaPrawa();

	public void setListaPrawa(List<PracownikDTO> pmListaPrawa);

	public List<PracownikDTO> getSelectionLewa();

	public int[] getSelectionLewaInt();
}
