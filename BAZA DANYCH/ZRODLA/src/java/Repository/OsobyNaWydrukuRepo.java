package Repository;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

import dbAccesspl.home.Database.Table.Zestawienie.DaneWydrukiColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;

public class OsobyNaWydrukuRepo extends AccessDB {

	public List<Integer> pobierzListe(Integer pmWydruk) {

		return QueryBuilder.SELECT()//
				.select(DaneWydrukiColumns.ID_OSOBY, DaneWydrukiColumns.ID_POZYCJI)//
				.andWarunek(DaneWydrukiColumns.ID_WYDRUKU, pmWydruk)//
				.execute().stream()//
				.map(lvRecord -> new Pair<Integer, Integer>(lvRecord.getAsInteger(DaneWydrukiColumns.ID_POZYCJI),
						lvRecord.getAsInteger(DaneWydrukiColumns.ID_OSOBY)))//
				.sorted((a, b) -> a.getKey().compareTo(b.getKey()))//
				.map(Pair::getValue)//
				.collect(Collectors.toList());
	}

	public void zapiszListe(List<Integer> pmLista) {
		QueryBuilder.DELETE().delete(SystemTablesNames.DANE_WYDRUKI).execute();
		for (int i = 0; i < pmLista.size(); i++)
			QueryBuilder.INSERT()//
					.setFromGenerator(DaneWydrukiColumns.ID_tabeli)//
					.set(DaneWydrukiColumns.ID_OSOBY, pmLista.get(i))//
					.set(DaneWydrukiColumns.ID_POZYCJI, i)//
					.set(DaneWydrukiColumns.ID_WYDRUKU, 1)//
					.execute();
	}

}