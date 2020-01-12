package Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import Grupy.GrupaDTO;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;

public class RepositoryPrzygotowaniaListyPracownikow {
	Object[][] getListaPracownikow(Object pmObject) {
		String lvZapytanie;
		if (pmObject.equals(""))
			return new Object[0][0];
		if (pmObject.equals("Wszyscy")) {
			lvZapytanie = //
					"Select " + ZestawienieBean.getKolumnaID() + "," + ZestawienieBean.getKolumnaNazwaPracownika() + //
							" from " + ZestawienieBean.getNazwaTabeli() + " where Data_Zwolnienia is null";//
		} else {
			lvZapytanie = "Select ID_Tabeli, Pracownik "//
					+ "from Zestawienie  zz right join AD_GRUPY_POWIAZANIA ad on zz.id_Tabeli=ad.ID_PRacownika"//
					+ " where zz.Data_Zwolnienia is null and ad.ID_grupy=" + ((GrupaDTO) pmObject).getID();//
		}

		lvZapytanie = lvZapytanie + " order by " + ZestawienieBean.getKolumnaNazwaPracownika();
		return dbAccess.getRecordSets(lvZapytanie);
	}
}
