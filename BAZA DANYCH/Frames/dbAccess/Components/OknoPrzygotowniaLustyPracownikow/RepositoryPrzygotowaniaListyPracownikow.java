package dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;

public class RepositoryPrzygotowaniaListyPracownikow
{
	Object[][] getListaPracownikow(Object pmObject)
	{
		if (pmObject.equals(""))
			return new Object[0][0];
		String lvZapytanie = //
				"Select " + ZestawienieBean.getKolumnaID() + "," + ZestawienieBean.getKolumnaNazwaPracownika() + //
						" from " + ZestawienieBean.getNazwaTabeli() + " where Data_Zwolnienia is null";//
		if (!pmObject.equals("Wszyscy") && !pmObject.equals(""))
		{
			System.out.println("Nie znalaz³em grupy");
		}

		lvZapytanie = lvZapytanie + " order by " + ZestawienieBean.getKolumnaNazwaPracownika();
		return dbAccess.getRecordSets(lvZapytanie);
	}
}
