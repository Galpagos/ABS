package pl.home.DniWolne;

import static dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns.Data;
import static dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns.Opis;

import java.util.List;
import java.util.stream.Collectors;

import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import pl.home.Database.components.AccessDB;
import pl.home.Database.components.LRecord;
import pl.home.Database.components.LRecordSet;

public class DniWolneRepository extends AccessDB {

	public List<DzienWolnyDTO> pobierzOstatnieDniWolne() {
		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_tabeli, Data, Opis)//
				.top(20)//
				.orderBy(Data, false)//
				.execute();

		return parsujeListeDni(lvWynik);
	}

	private DzienWolnyDTO parsujDzien(LRecord pmRecord) {
		DzienWolnyDTO lvDzien = new DzienWolnyDTO();
		lvDzien.setData(pmRecord.getAsLocalDate(Data));
		lvDzien.setOpis(pmRecord.getAsString(Opis));
		lvDzien.setIdTabeli(pmRecord.getAsInteger(ID_tabeli));

		return lvDzien;
	}

	private List<DzienWolnyDTO> parsujeListeDni(LRecordSet pmSet) {
		return pmSet.stream()//
				.map(lvRecord -> parsujDzien(lvRecord))//
				.collect(Collectors.toList());
	}
}
