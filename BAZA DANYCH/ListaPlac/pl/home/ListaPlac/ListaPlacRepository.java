package pl.home.ListaPlac;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public interface ListaPlacRepository {

	public List<LocalDate> getListaDniWolnychWMiesiacu(YearMonth pmMiesiac);

	public List<AbsencjaDTO> getListaAbsencjiUrlopu(YearMonth pmRokMiesiac, int pmId);
}
