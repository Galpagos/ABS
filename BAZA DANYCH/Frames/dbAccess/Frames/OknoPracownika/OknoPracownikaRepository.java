package dbAccess.Frames.OknoPracownika;

import java.util.Date;

import Parsery.ParseryDB;
import dbAccess.AbsencjaBean;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;

public class OknoPracownikaRepository
{
	public void usunAbsencje(int pmIdAbsencji)
	{
		dbAccess.Zapisz("Delete * from " + AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaID + " = "
				+ pmIdAbsencji);
	}

	public void ustawDateUrodzenia(ZestawienieBean pmPracownika, Date pmDataUrodzenia)
	{
		dbAccess.Zapisz("Update Zestawienie set Data_Urodzenia=" + ParseryDB.DateParserToSQL_INSERT(pmDataUrodzenia)
				+ " where id_tabeli =" + pmPracownika.getLvID());

	}

	public Object[][] getDataUrodzenia(ZestawienieBean pmPracownik)
	{
		return dbAccess
				.getRecordSets("Select Data_Urodzenia from Zestawienie where ID_tabeli=" + pmPracownik.getLvID());
	}

	public Object[][] getUrlopNalezny(ZestawienieBean pmPracownik)
	{
		return dbAccess.getRecordSets("Select Urlop_Nalezny from Zestawienie where ID_tabeli=" + pmPracownik.getLvID());
	}

	public void ustawUrlopNalezny(ZestawienieBean pmPracownika, int pmUrlop)
	{
		dbAccess.Zapisz(
				"Update Zestawienie set Urlop_Nalezny=" + pmUrlop + " where id_tabeli =" + pmPracownika.getLvID());

	}
}
