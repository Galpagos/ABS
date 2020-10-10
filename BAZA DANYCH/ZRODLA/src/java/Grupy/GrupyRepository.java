package Grupy;

import static dbAccesspl.home.Database.Table.Zestawienie.GrupyColumns.ID_GRP;
import static dbAccesspl.home.Database.Table.Zestawienie.GrupyColumns.Nazwa;

import java.util.List;
import java.util.stream.Collectors;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import dbAccesspl.home.Database.Table.Zestawienie.GrupyPowiazaniaColumns;

class GrupyRepository extends AccessDB {

	List<GrupaDTO> pobierzGrupy() {
		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_GRP, Nazwa)//
				.execute();

		return parsujGrupy(lvWynik);
	}

	List<GrupaDTO> pobierzGrupyPracownika(int pmId) {
		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_GRP, Nazwa)//
				.joinOn(GrupyPowiazaniaColumns.ID_GRUPY, ID_GRP)//
				.andWarunek(GrupyPowiazaniaColumns.ID_PRACOWNIKA, pmId)//
				.execute();

		return parsujGrupy(lvWynik);
	}

	void dodajGrupe(String pmNazwa) {
		QueryBuilder.INSERT()//
				.set(ID_GRP, QueryBuilder.getNextId(ID_GRP))//
				.set(Nazwa, pmNazwa)//
				.execute();
	}

	public void usunGrupe(int pmId) {
		QueryBuilder.DELETE()//
				.andWarunek(GrupyPowiazaniaColumns.ID_GRUPY, pmId)//
				.execute();
		QueryBuilder.DELETE()//
				.andWarunek(ID_GRP, pmId)//
				.execute();
	}

	public void ustawGrupePracownikowi(int pmIdPracownika, GrupaDTO pmGrupa) {
		QueryBuilder.INSERT()//
				.set(GrupyPowiazaniaColumns.ID_GRUPY, pmGrupa.getID())//
				.set(GrupyPowiazaniaColumns.ID_PRACOWNIKA, pmIdPracownika)//
				.execute();
	}

	public void usunGrupePracownikowi(int pmIdPracownika, GrupaDTO pmGrupa) {
		QueryBuilder.DELETE()//
				.andWarunek(GrupyPowiazaniaColumns.ID_GRUPY, pmGrupa.getID())//
				.andWarunek(GrupyPowiazaniaColumns.ID_PRACOWNIKA, pmIdPracownika)//
				.execute();
	}

	private List<GrupaDTO> parsujGrupy(LRecordSet pmSet) {
		return pmSet.stream()//
				.map(lvRecord -> parsujGrupe(lvRecord))//
				.collect(Collectors.toList());
	}

	private GrupaDTO parsujGrupe(LRecord pmRecord) {
		return new GrupaDTO().setID(pmRecord.getAsInteger(ID_GRP)).setNazwa(pmRecord.getAsString(Nazwa));
	}

}
