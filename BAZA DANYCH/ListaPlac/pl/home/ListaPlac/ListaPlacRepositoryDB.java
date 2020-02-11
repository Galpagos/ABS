package pl.home.ListaPlac;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Datownik.Data;
import Parsery.ParseryDB;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import pl.home.Database.components.AccessDB;
import pl.home.Database.components.LRecordSet;

public class ListaPlacRepositoryDB extends AccessDB implements ListaPlacRepository {

	@Override
	public List<LocalDate> getListaDniWolnychWMiesiacu(YearMonth pmMiesiac) {
		List<LocalDate> lvLista = new ArrayList<>();

		LRecordSet lvDniWolne = executeQuery(""//
				+ " SELECT "//
				+ "   Data "//
				+ " FROM "//
				+ "   DniWolne "//
				+ " WHERE "//
				+ " Data BEtween " + ParseryDB.DateParserToSQL_SELECT(Data.utworzDateNaPierwszyDzien(pmMiesiac))
				+ " and " + ParseryDB.DateParserToSQL_SELECT(Data.utworzDateNaOstatniDzien(pmMiesiac)));

		if (lvDniWolne != null && !lvDniWolne.isEmpty())
			lvLista = lvDniWolne//
					.stream()//
					.map(lvRecord -> lvDniWolne.getAsTimestamp("Data").toLocalDateTime().toLocalDate())//
					.collect(Collectors.toList());

		return lvLista;

	}

	@Override
	public List<AbsencjaDTO> getListaAbsencjiUrlopu(YearMonth pmRokMiesiac, int pmId) {

		return null;
	}
}
