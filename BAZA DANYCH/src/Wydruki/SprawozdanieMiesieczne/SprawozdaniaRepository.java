package Wydruki.SprawozdanieMiesieczne;

import java.util.List;

import Enums.SLRodzajeAbsencji;
import dbAccess.AbsencjaBean;
import dbAccess.DniWolneBean;
import dbAccess.dbAccess;

public class SprawozdaniaRepository {
	public Object[][] getAbsencjeDlaPracownika(int pmIdPrac, List<SLRodzajeAbsencji> pmList) {
		StringBuilder lvZapytanie = new StringBuilder()//
				.append("Select ")//
				.append(AbsencjaBean.kolumnaID)//
				.append(",")//
				.append(AbsencjaBean.kolumnaIdPracownika)//
				.append(",")//
				.append(AbsencjaBean.kolumnaOdKiedy).append(",")//
				.append(AbsencjaBean.kolumnaDoKiedy).append(",")//
				.append(AbsencjaBean.kolumnaRodzajAbsencji) //
				.append(" from ")//
				.append(AbsencjaBean.NazwaTabeli)//
				.append(" where ").append(AbsencjaBean.kolumnaIdPracownika).append("=")//
				.append(pmIdPrac)//
				.append(warunekNaModul(pmList));

		return dbAccess.getRecordSets(lvZapytanie.toString());
	}

	public Object[][] getDniWolne() {
		String lvZapytanie = //
				"Select " + DniWolneBean.kolumnaData //
						+ " from " + DniWolneBean.NazwaTabeli;
		return dbAccess.getRecordSets(lvZapytanie);
	}

	private String warunekNaModul(List<SLRodzajeAbsencji> pmLista) {
		if (pmLista == null || pmLista.size() == 0)
			return "";
		StringBuilder lvWarunek = new StringBuilder();
		lvWarunek.append(" AND ");
		lvWarunek.append(AbsencjaBean.kolumnaRodzajAbsencji);
		lvWarunek.append(" IN ( 'TEST");
		for (SLRodzajeAbsencji lvRodzaj : pmLista) {
			lvWarunek.append("','");
			lvWarunek.append(lvRodzaj.getKod());
		}
		lvWarunek.append("')");
		return lvWarunek.toString();

	}

	public String getUrlopNalezny(int pmId) {
		return dbAccess.getRecordSets("Select Urlop_Nalezny from Zestawienie where id_tabeli=" + pmId)[0][0].toString();

	}
}
