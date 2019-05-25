package dbAccess.Frames.OknoGlowne;

import java.util.Date;

import javax.swing.JOptionPane;

import Parsery.ParseryDB;
import PrzygotowanieDanych.PracownikDTO;
import dbAccess.AbsencjaBean;
import dbAccess.DniWolneBean;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;

public class RepositoryOknaGlownego
{
	public void dodajPracownikaDB(String pmNazwa)
	{
		PracownikDTO lvPracownik = new PracownikDTO();
		lvPracownik.setId(dbAccess.GetNextID("Zestawienie"));
		lvPracownik.setNazwa(pmNazwa);
		dbAccess.Zapisz(lvPracownik.ZapisDataSetu());
	}

	public void usunPracownikaDB(int pmId)
	{
		int liczbaAbsencji = dbAccess
				.GetCount(AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaIdPracownika + " = " + pmId);
		JOptionPane.showMessageDialog(null,
				"Usunieto pracownika " + pmId + "\n oraz " + liczbaAbsencji + " jego absencji!", "Usuwanie Pracownika",
				JOptionPane.INFORMATION_MESSAGE);
		dbAccess.Zapisz("Delete * from " + AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaIdPracownika
				+ " = " + pmId);
		dbAccess.Zapisz("Delete * from " + ZestawienieBean.getNazwaTabeli() + " where " + ZestawienieBean.getKolumnaID()
				+ " = " + pmId);
	}

	public Object[][] pobierzNieobecnych(Date pmNaKiedy)
	{
		String lvZapytanie = //
				"SELECT zz." + ZestawienieBean.getKolumnaNazwaPracownika() + ", ab." + AbsencjaBean.kolumnaOdKiedy
						+ ", ab." + AbsencjaBean.kolumnaDoKiedy + ",ab." + AbsencjaBean.kolumnaRodzajAbsencji + //
						" from " + AbsencjaBean.NazwaTabeli + " ab  "//
						+ "INNER JOIN " + ZestawienieBean.getNazwaTabeli() + " zz on ab."
						+ AbsencjaBean.GetKolumnIdPracownika() + "=zz." + ZestawienieBean.getKolumnaID() + //
						" WHERE ab." + AbsencjaBean.kolumnaOdKiedy + " <=" + ParseryDB.DateParserToSQL_SELECT(pmNaKiedy)//
						+ " and ab." + AbsencjaBean.kolumnaDoKiedy + ">=" + ParseryDB.DateParserToSQL_SELECT(pmNaKiedy);
		return dbAccess.getRecordSets(lvZapytanie);
	}

	public void zapiszNowyDzienWolny(DniWolneBean pmDzien)
	{
		pmDzien.setId(dbAccess.GetNextID(DniWolneBean.NazwaTabeli));
		dbAccess.Zapisz(pmDzien.ZapiszDataSet());
	}

	public Object[][] pobierzDniWolne()
	{
		String lvZapytanie = //
				"SELECT top 5 TB.* from " + DniWolneBean.NazwaTabeli + " TB order by " + DniWolneBean.kolumnaData
						+ " DESC";
		return dbAccess.getRecordSets(lvZapytanie);
	}

	public void zwolnijPracownika(int pmId, Date pmData)
	{
		String lvZapytanie = //
				"UPDATE ZESTAWIENIE SET Data_Zwolnienia = " + ParseryDB.DateParserToSQL_INSERT(pmData)
						+ " where ID_tabeli = " + pmId;
		dbAccess.Zapisz(lvZapytanie);

	}
}
