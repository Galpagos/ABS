package dbAccess.Frames.OknoGlowne;

import java.util.Date;

import javax.swing.JOptionPane;

import Parsery.ParseryDB;
import dbAccess.AbsencjaBean;
import dbAccess.DniWolneBean;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;

public class RepositoryOknaGlownego
{
	public void dodajPracownikaDB(String pmNazwa)
	{
		ZestawienieBean lvPracownik = new ZestawienieBean();
		lvPracownik.setLvID(dbAccess.GetNextID(ZestawienieBean.getNazwaTabeli()));
		lvPracownik.setLvNazwa(pmNazwa);
		dbAccess.Zapisz(lvPracownik.ZapisDataSetu());
	}

	public void usunPracownikaDB(ZestawienieBean pmPracownik)
	{
		int liczbaAbsencji = dbAccess.GetCount(AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaIdPracownika
				+ " = " + pmPracownik.getLvID());
		JOptionPane.showMessageDialog(null,
				"Usunieto pracownika " + pmPracownik.getLvNazwa() + "\n oraz " + liczbaAbsencji + " jego absencji!",
				"Usuwanie Pracownika", JOptionPane.INFORMATION_MESSAGE);
		dbAccess.Zapisz("Delete * from " + AbsencjaBean.NazwaTabeli + " where " + AbsencjaBean.kolumnaIdPracownika
				+ " = " + pmPracownik.getLvID());
		dbAccess.Zapisz("Delete * from " + ZestawienieBean.getNazwaTabeli() + " where " + ZestawienieBean.getKolumnaID()
				+ " = " + pmPracownik.getLvID());
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

	public void zwolnijPracownika(ZestawienieBean pmPracownikZTabeli, Date pmData)
	{
		String lvZapytanie = //
				"UPDATE ZESTAWIENIE SET Data_Zwolnienia = " + ParseryDB.DateParserToSQL_INSERT(pmData)
						+ " where ID_tabeli = " + pmPracownikZTabeli.getLvID();
		dbAccess.Zapisz(lvZapytanie);

	}
}
