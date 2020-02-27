package Frames.dbAccess.Frames.OknoGlowne;

import java.util.Date;

import javax.swing.JOptionPane;

import dbAccess.DniWolneBean;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns;
import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;

public class RepositoryOknaGlownego {
	public void dodajPracownikaDB(String pmNazwa) {
		QueryBuilder.INSERT()//
				.set(ZestawienieColumns.Urlop_Nalezny, 26)//
				.set(ZestawienieColumns.ID_tabeli, QueryBuilder.getNextId(ZestawienieColumns.ID_tabeli))//
				.set(ZestawienieColumns.Pracownik, pmNazwa)//
				.execute();
	}

	public void usunPracownikaDB(int pmId) {

		JOptionPane.showMessageDialog(null, "Usunieto pracownika oraz jego absencje!", "Usuwanie Pracownika",
				JOptionPane.INFORMATION_MESSAGE);
		QueryBuilder.DELETE()//
				.andWarunek(AbsencjeColumns.ID_pracownika, pmId)//
				.execute();

		QueryBuilder.DELETE()//
				.andWarunek(ZestawienieColumns.ID_tabeli, pmId)//
				.execute();
	}

	public void zapiszNowyDzienWolny(DniWolneBean pmDzien) {
		QueryBuilder.INSERT()//
				.set(DniWolneColumns.ID_tabeli, QueryBuilder.getNextId(DniWolneColumns.ID_tabeli))//
				.set(DniWolneColumns.Data, pmDzien.getData())//
				.execute();

	}

	public void zwolnijPracownika(int pmId, Date pmData) {
		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.Data_Zwolnienia, pmData)//
				.andWarunek(ZestawienieColumns.ID_tabeli, pmId)//
				.execute();

	}
}
