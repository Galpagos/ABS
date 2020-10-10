package pl.home.DniWolne;

import static dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns.Data;
import static dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns.ID_DN_WN;
import static dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns.Opis;

import java.util.List;
import java.util.stream.Collectors;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;

public class DniWolneRepository extends AccessDB {

	public List<DzienWolnyDTO> pobierzOstatnieDniWolne() {
		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_DN_WN, Data, Opis)//
				.top(20)//
				.orderBy(Data, false)//
				.execute();

		return parsujeListeDni(lvWynik);
	}

	private DzienWolnyDTO parsujDzien(LRecord pmRecord) {
		DzienWolnyDTO lvDzien = new DzienWolnyDTO();
		lvDzien.setData(pmRecord.getAsLocalDate(Data));
		lvDzien.setOpis(pmRecord.getAsString(Opis));
		lvDzien.setIdTabeli(pmRecord.getAsInteger(ID_DN_WN));

		return lvDzien;
	}

	private List<DzienWolnyDTO> parsujeListeDni(LRecordSet pmSet) {
		return pmSet.stream()//
				.map(lvRecord -> parsujDzien(lvRecord))//
				.collect(Collectors.toList());
	}
}
