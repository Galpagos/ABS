package Absencja;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.AliasDB;
import ProjektGlowny.commons.parsers.ParseryDB;

import java.time.LocalDate;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
@Deprecated
public class AbsencjaRepository extends AccessDB implements AbsencjaRepositor {

	private static int GetCount(String pmTabela) {

		String lvZapytanie = "SELECT Count(1) as TOTAL FROM " + pmTabela;
		return executeQuery(lvZapytanie).getAsInteger(new AliasDB("TOTAL", Integer.class));
	}

	@Override
	public int ileDniWolnych(LocalDate pmDataOd, LocalDate pmDataDo) {
		return GetCount("DniWolne"//
				+ " where DATA BEtween " + ParseryDB.DateParserToSQL_SELECT(pmDataOd) + " and " + ParseryDB.DateParserToSQL_SELECT(pmDataDo));
	}

	@Override
	public int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja) {
		return GetCount("Absencje where " + AbsencjeColumns.ID_tabeli + " != " + pmAbsencja.getId() + " and id_pracownika=" + pmAbsencja.getIdPracownika()
				+ " and Od_kiedy <= " + ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getEnd()) //
				+ " and Do_Kiedy>=" + ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getOkres().getStart()));
	}
}
