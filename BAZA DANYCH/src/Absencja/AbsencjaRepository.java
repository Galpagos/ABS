package Absencja;

import java.util.Date;

import Parsery.ParseryDB;
import PrzygotowanieDanych.AbsencjaDTO;
import dbAccess.DniWolneBean;
import dbAccess.dbAccess;

public class AbsencjaRepository
{

	public Object[][] getAbsencjePracownika(int pmId)
	{
		return dbAccess.getRecordSets(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,Rodzaj_absencji from Absencje where id_pracownika="
						+ pmId);
	}

	public Object[][] getAbsencjePoId(int pmId)
	{
		return dbAccess.getRecordSets(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,Rodzaj_absencji from Absencje where id_tabeli="
						+ pmId);
	}

	public int ileDniWolnych(Date pmDataOd, Date pmDataDo)
	{
		return dbAccess.GetCount(DniWolneBean.NazwaTabeli //
				+ " where " + DniWolneBean.kolumnaData + " BEtween " + ParseryDB.DateParserToSQL_SELECT(pmDataOd)
				+ " and " + ParseryDB.DateParserToSQL_SELECT(pmDataDo));
	}

	public void dodajAbsencje(AbsencjaDTO pmAbs)
	{
		dbAccess.Zapisz("INSERT INTO Absencje (ID_tabeli , ID_pracownika , Od_kiedy , Do_kiedy , Rodzaj_absencji ) "//
				+ " VALUES (" + pmAbs.getId() + " , " + pmAbs.getIdPracownika() + " ,"
				+ ParseryDB.DateParserToSQL_INSERT(pmAbs.getOkres().getStart().toDate()) + " , "
				+ ParseryDB.DateParserToSQL_INSERT(pmAbs.getOkres().getEnd().toDate()) + " ,\""
				+ pmAbs.getRodzaj().toString() + "\")");
	}

	public void usunAbsencje(int pmID)
	{
		dbAccess.Zapisz("Delete * from Absencje where Id_tabeli=" + pmID);
	}
}
