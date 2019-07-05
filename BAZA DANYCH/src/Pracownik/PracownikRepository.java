package Pracownik;

import java.util.Date;

import javax.swing.JOptionPane;

import Parsery.ParseryDB;
import dbAccess.AbsencjaBean;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;

public class PracownikRepository
{
	public void dodajPracownika(String pmNazwa)
	{
		int lvID = dbAccess.GetNextID("Zestawienie");
		dbAccess.Zapisz("INSERT INTO Zestawienie (ID_tabeli, Pracownik, Urlop_Nalezny) VALUES ( " + lvID + " ,\""
				+ pmNazwa + "\", 26)");
	}

	public void usunPracownika(int pmId)
	{
		String lvNazwa = getPracownikNazwa(pmId);
		JOptionPane.showMessageDialog(null, "Usunieto pracownika " + lvNazwa, "Usuwanie Pracownika",
				JOptionPane.INFORMATION_MESSAGE);
		dbAccess.Zapisz("Delete * from " + AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaIdPracownika
				+ " = " + pmId);
		dbAccess.Zapisz("Delete * from " + ZestawienieBean.getNazwaTabeli() + " where " + ZestawienieBean.getKolumnaID()
				+ " = " + pmId);
	}

	public void zwolnijPracownika(int pmId, Date pmData)
	{
		String lvZapytanie = //
				"UPDATE ZESTAWIENIE SET Data_Zwolnienia = " + ParseryDB.DateParserToSQL_INSERT(pmData)
						+ " where ID_tabeli = " + pmId;
		dbAccess.Zapisz(lvZapytanie);

	}

	public String getPracownikNazwa(int pmId)
	{
		return dbAccess.getRecordSets("Select Pracownik from Zestawienie where id_tabeli=" + pmId)[0][0].toString();

	}

	public Object[][] getListaWszystkichPracownikow()
	{
		// TODO Auto-generated method stub
		return dbAccess.getRecordSets(
				"Select " + ZestawienieBean.getKolumnaID() + "," + ZestawienieBean.getKolumnaNazwaPracownika() + //
						" from " + ZestawienieBean.getNazwaTabeli()
						+ " where Data_Zwolnienia is null order by pracownik asc");
	}

	public Object[][] getListaPracownikowWGrupie(int pmGrupaId)
	{
		return dbAccess.getRecordSets("Select ID_Tabeli, Pracownik "//
				+ "from Zestawienie  zz right join AD_GRUPY_POWIAZANIA ad on zz.id_Tabeli=ad.ID_PRacownika"//
				+ " where zz.Data_Zwolnienia is null and ad.ID_grupy=" + pmGrupaId + " order by pracownik asc");
	}

	public void ustawDateUrodzenia(int pmId, Date pmDataUrodzenia)
	{
		dbAccess.Zapisz("Update Zestawienie set Data_Urodzenia=" + ParseryDB.DateParserToSQL_INSERT(pmDataUrodzenia)
				+ " where id_tabeli =" + pmId);

	}

	public Object[][] getDataUrodzenia(int pmId)
	{
		return dbAccess.getRecordSets("Select Data_Urodzenia from Zestawienie where ID_tabeli=" + pmId);
	}

	public Object[][] getUrlopNalezny(int pmId)
	{
		return dbAccess.getRecordSets("Select Urlop_Nalezny from Zestawienie where ID_tabeli=" + pmId);
	}

	public void ustawUrlopNalezny(int pmId, int pmUrlop)
	{
		dbAccess.Zapisz("Update Zestawienie set Urlop_Nalezny=" + pmUrlop + " where id_tabeli =" + pmId);

	}

	public Object[][] pobierzNieobecnych(Date pmNaKiedy)
	{
		String lvZapytanie = //
				"SELECT zz.id_tabeli, zz." + ZestawienieBean.getKolumnaNazwaPracownika() + ", ab."
						+ AbsencjaBean.kolumnaOdKiedy + ", ab." + AbsencjaBean.kolumnaDoKiedy + ",ab."
						+ AbsencjaBean.kolumnaRodzajAbsencji + //
						" from " + AbsencjaBean.NazwaTabeli + " ab  "//
						+ "INNER JOIN " + ZestawienieBean.getNazwaTabeli() + " zz on ab."
						+ AbsencjaBean.GetKolumnIdPracownika() + "=zz." + ZestawienieBean.getKolumnaID() + //
						" WHERE ab." + AbsencjaBean.kolumnaOdKiedy + " <=" + ParseryDB.DateParserToSQL_SELECT(pmNaKiedy)//
						+ " and ab." + AbsencjaBean.kolumnaDoKiedy + ">=" + ParseryDB.DateParserToSQL_SELECT(pmNaKiedy);
		return dbAccess.getRecordSets(lvZapytanie);
	}
}
