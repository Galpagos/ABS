package pl.home.ListaPlac;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public interface ListaPlacRepository {

	public List<LocalDate> getListaDniWolnychWMiesiacu(YearMonth pmMiesiac);

	public List<AbsencjaDTO> getListaAbsencjiUrlopu(YearMonth pmRokMiesiac, int pmId);
}
