package Pracownik;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Urlop_Nalezny;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.utils.Interval;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.GrupyPowiazaniaColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import enums.SLRodzajeAbsencji;

public class PracownikRepository extends AccessDB {
	public void dodajPracownika(String pmNazwa) {
		QueryBuilder.INSERT()//
				.setFromGenerator(ID_tabeli)//
				.set(Pracownik, pmNazwa)//
				.set(Urlop_Nalezny, 26)//
				.execute();
	}

	public void usunPracownika(int pmId) {

		QueryBuilder.DELETE()//
				.delete(SystemTablesNames.ABSENCJE)//
				.andWarunek(AbsencjeColumns.ID_pracownika, pmId)//
				.execute();

		QueryBuilder.DELETE()//
				.delete(SystemTablesNames.ZESTAWIENIE)//
				.andWarunek(ZestawienieColumns.ID_tabeli, pmId)//
				.execute();
	}

	public void zwolnijPracownika(int pmId, LocalDate pmData) {

		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.Data_Zwolnienia, pmData)//
				.andWarunek(ZestawienieColumns.ID_tabeli, pmId)//
				.execute();
	}

	public String getPracownikNazwa(int pmId) {
		return QueryBuilder.SELECT()//
				.select(Pracownik)//
				.andWarunek(ID_tabeli, pmId)//
				.execute().getAsString(Pracownik);
	}

	public List<PracownikDTO> getListaWszystkichPracownikow() {

		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_tabeli, Pracownik)//
				.andWarunek(ZestawienieColumns.Data_Zwolnienia, null)//
				.orderBy(Pracownik, true)//
				.execute();

		return lvWynik//
				.stream()//
				.map(lvRecord -> parsujPracownika(lvRecord))//
				.collect(Collectors.toList());

	}

	private PracownikDTO parsujPracownika(LRecord pmRecord) {
		PracownikDTO lvPracownik = new PracownikDTO();
		lvPracownik.setId(pmRecord.getAsInteger(ID_tabeli));
		lvPracownik.setNazwa(pmRecord.getAsString(Pracownik));
		lvPracownik.setDataZwolnienia(pmRecord.getAsLocalDate(ZestawienieColumns.Data_Zwolnienia));
		if (pmRecord.containsKey(AbsencjeColumns.RODZAJ)) {
			AbsencjaDTO lvAbsencja = new AbsencjaDTO();
			lvAbsencja.setRodzaj(SLRodzajeAbsencji.getByKod(pmRecord.getAsString(AbsencjeColumns.RODZAJ)));
			lvAbsencja.setOkres(new Interval(pmRecord.getAsLocalDate(AbsencjeColumns.Od_kiedy),
					pmRecord.getAsLocalDate(AbsencjeColumns.Do_kiedy)));
			lvPracownik.setListaAbsencji(Arrays.asList(lvAbsencja));
		}
		return lvPracownik;
	}

	public List<PracownikDTO> getListaPracownikowWGrupie(int pmGrupaId) {

		LRecordSet lvWynik = //
				QueryBuilder.SELECT()//
						.select(ID_tabeli, Pracownik)//
						.joinOn(GrupyPowiazaniaColumns.ID_PRACOWNIKA, ID_tabeli)//
						.andWarunek(ZestawienieColumns.Data_Zwolnienia, null)//
						.andWarunek(GrupyPowiazaniaColumns.ID_GRUPY, pmGrupaId)//
						.orderBy(Pracownik, true)//
						.execute();

		return lvWynik//
				.stream()//
				.map(lvRecord -> parsujPracownika(lvRecord))//
				.collect(Collectors.toList());
	}

	public void ustawDateUrodzenia(int pmId, Date pmDataUrodzenia) {
		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.Data_Urodzenia, pmDataUrodzenia)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();
	}

	public Date getDataUrodzenia(int pmId) {
		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(ZestawienieColumns.Data_Urodzenia)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();

		return lvRecordSet.getAsDate(ZestawienieColumns.Data_Urodzenia);
	}

	public void ustawUrlopNalezny(int pmId, int pmUrlop) {
		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.Urlop_Nalezny, pmUrlop)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();
	}

	public Integer getUrlopNalezny(int pmId) {
		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(ZestawienieColumns.Urlop_Nalezny)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();

		return lvRecordSet.getAsInteger(ZestawienieColumns.Urlop_Nalezny);
	}

	public List<PracownikDTO> pobierzNieobecnych(LocalDate pmDataObecnosci) {

		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_tabeli, Pracownik, AbsencjeColumns.Od_kiedy, AbsencjeColumns.Do_kiedy,
						AbsencjeColumns.RODZAJ)//
				.joinOn(AbsencjeColumns.ID_pracownika, ID_tabeli)
				.andBeforeOrEqual(AbsencjeColumns.Od_kiedy, pmDataObecnosci)//
				.andAfterOrEqual(AbsencjeColumns.Do_kiedy, pmDataObecnosci)//
				.execute();

		return lvWynik.stream().map(lvRecord -> parsujPracownika(lvRecord)).collect(Collectors.toList());
	}

	public PracownikDTO getPracownik(Integer pmIdPracownika) {

		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_tabeli, ZestawienieColumns.Pracownik, ZestawienieColumns.Urlop_Nalezny,
						ZestawienieColumns.Data_Urodzenia, ZestawienieColumns.Data_Zatrudnienia,
						ZestawienieColumns.Data_Zwolnienia)//
				.andWarunek(ID_tabeli, pmIdPracownika)//
				.execute();

		return parsujPracownika(lvWynik.get(0));
	}

	public LocalDate getDataZwolnienia(int pmId) {

		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(ZestawienieColumns.Data_Zwolnienia)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();
		return lvRecordSet.getAsLocalDate(ZestawienieColumns.Data_Zwolnienia);
	}
}
