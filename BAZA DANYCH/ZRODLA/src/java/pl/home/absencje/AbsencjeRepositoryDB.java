package pl.home.absencje;

import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.Do_kiedy;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.EKWIWALENT;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.ID_pracownika;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.Od_kiedy;
import static dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns.RODZAJ;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.utils.Interval;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;

public class AbsencjeRepositoryDB extends AccessDB {

	public void saveAbsence(AbsencjaDTO pmAbsencja) {
		int lvId = pmAbsencja.getId() == 0 ? QueryBuilder.getNextID(AbsencjeColumns.ID_tabeli) : pmAbsencja.getId();
		QueryBuilder.INSERT()//
				.set(ID_tabeli, lvId)//
				.set(AbsencjeColumns.ID_pracownika, pmAbsencja.getIdPracownika())//
				.set(AbsencjeColumns.Od_kiedy, pmAbsencja.getOkres().getStart())//
				.set(AbsencjeColumns.Do_kiedy, pmAbsencja.getOkres().getEnd())//
				.set(AbsencjeColumns.EKWIWALENT, pmAbsencja.getProcent().getKod())//
				.set(AbsencjeColumns.RODZAJ, pmAbsencja.getRodzaj().getKod())//
				.execute();
	}

	public List<AbsencjaDTO> getAbsencjePracownika(Integer pmPracownikId) {

		LRecordSet lvDane = QueryBuilder.SELECT()//
				.select(ID_tabeli, ZestawienieColumns.Pracownik, ID_pracownika, Od_kiedy, Do_kiedy, RODZAJ, EKWIWALENT)//
				.joinOn(ZestawienieColumns.ID_tabeli, ID_pracownika)//
				.andWarunek(ID_pracownika, pmPracownikId)//
				.execute();

		return lvDane.stream().map(AbsencjaDTO::parsuj).collect(Collectors.toList());
	}

	public List<AbsencjaDTO> getWorkerAbsenceInTerms(int pmIdPracownika, Interval pmOkres) {
		LRecordSet lvDane = QueryBuilder.SELECT()//
				.select(ID_tabeli, ZestawienieColumns.Pracownik, ID_pracownika, Od_kiedy, Do_kiedy, RODZAJ, EKWIWALENT)//
				.joinOn(ZestawienieColumns.ID_tabeli, ID_pracownika)//
				.andWarunek(ID_pracownika, pmIdPracownika)//
				.andAfterOrEqual(Do_kiedy, pmOkres.getStart())//
				.andBeforeOrEqual(Od_kiedy, pmOkres.getEnd())//
				.execute();

		return lvDane.stream().map(AbsencjaDTO::parsuj).collect(Collectors.toList());

	}

	public void deleteAbsence(Integer pmAbsenceId) {
		QueryBuilder.DELETE()//
				.delete(SystemTablesNames.ABSENCJE)//
				.andWarunek(AbsencjeColumns.ID_tabeli, pmAbsenceId)//
				.execute();
	}

	public Optional<AbsencjaDTO> getAbsenceById(Integer pmIdAbs) {
		LRecordSet lvDane = QueryBuilder.SELECT()//
				.select(ID_tabeli, ZestawienieColumns.Pracownik, ID_pracownika, Od_kiedy, Do_kiedy, RODZAJ, EKWIWALENT)//
				.joinOn(ZestawienieColumns.ID_tabeli, ID_pracownika)//
				.andWarunek(ID_tabeli, pmIdAbs)//
				.execute();

		return lvDane.stream().map(AbsencjaDTO::parsuj).findAny();
	}

}
