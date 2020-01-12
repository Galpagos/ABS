package Frames.dbAccess.Frames.OknoPracownika;

import java.util.Date;

import Parsery.ParseryDB;
import dbAccess.AbsencjaBean;
import dbAccess.dbAccess;

public class OknoPracownikaRepository {
	public void usunAbsencje(int pmIdAbsencji) {
		dbAccess.Zapisz("Delete * from " + AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaID + " = "
				+ pmIdAbsencji);
	}

	public void ustawDateUrodzenia(int pmId, Date pmDataUrodzenia) {
		dbAccess.Zapisz("Update Zestawienie set Data_Urodzenia=" + ParseryDB.DateParserToSQL_INSERT(pmDataUrodzenia)
				+ " where id_tabeli =" + pmId);

	}

	public Object[][] getDataUrodzenia(int pmId) {
		return dbAccess.getRecordSets("Select Data_Urodzenia from Zestawienie where ID_tabeli=" + pmId);
	}

	public Object[][] getUrlopNalezny(int pmId) {
		return dbAccess.getRecordSets("Select Urlop_Nalezny from Zestawienie where ID_tabeli=" + pmId);
	}

	public void ustawUrlopNalezny(int pmId, int pmUrlop) {
		dbAccess.Zapisz("Update Zestawienie set Urlop_Nalezny=" + pmUrlop + " where id_tabeli =" + pmId);

	}
}
