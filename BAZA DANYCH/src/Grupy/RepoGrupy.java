package Grupy;

import dbAccess.dbAccess;

public class RepoGrupy
{

	public void dodajGrupe(String pmNazwa)
	{
		dbAccess.Zapisz("Insert into AD_GRUPY ( ID_tabeli, Nazwa) values ( " + dbAccess.GetNextID("AD_GRUPY") + ", \""
				+ pmNazwa + "\")");
	}

	public Object[][] getGrupy()
	{
		String lvSelect = "Select ID_tabeli,Nazwa from AD_GRUPY";
		return dbAccess.getRecordSets(lvSelect);
	}

	public Object[][] pobierzGrupy(int pmLvID)
	{
		String lvZapytanie = "Select W.ID_tabeli, W.Nazwa from AD_GRUPY W left join AD_GRUPY_POWIAZANIA WP on W.ID_tabeli=WP.ID_GRUPY where WP.ID_PRACOWNIKA="
				+ pmLvID;
		return dbAccess.getRecordSets(lvZapytanie);
	}

	public void usunGrupe(GrupaDTO pmId)
	{
		dbAccess.DeleteRow("AD_GRUPY", pmId.getID());

	}

	public void ustawGrupePracownikowi(int pmLvIdPracownika, GrupaDTO pmGrupa)
	{
		dbAccess.Zapisz("Insert into AD_GRUPY_POWIAZANIA (ID_PRACOWNIKA, ID_GRUPY) values (" + pmLvIdPracownika + ","
				+ pmGrupa.getID() + ")");

	}

	public void usunGrupePracownikowi(int pmLvIdPracownika, GrupaDTO pmGrupa)
	{
		dbAccess.Zapisz("DELETE FROM AD_GRUPY_POWIAZANIA WHERE ID_PRACOWNIKA= " + pmLvIdPracownika + "	 aND ID_GRUPY= "
				+ pmGrupa.getID() + "");

	}
}
