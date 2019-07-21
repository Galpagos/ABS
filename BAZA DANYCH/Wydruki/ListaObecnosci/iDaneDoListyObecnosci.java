package ListaObecnosci;

import java.time.LocalDate;
import java.util.List;

import PrzygotowanieDanych.PracownikDTO;

public interface iDaneDoListyObecnosci
{
	List<PracownikDTO> getListaLewa();

	List<PracownikDTO> getListaPrawa();

	LocalDate getData();

	String getNaglowek();

	String getStopka();

	int getWysokoscWiersza();

	public void odswiezKontrolki();

}
