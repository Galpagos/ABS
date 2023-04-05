package Pracownik;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ETAT;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.GODZINA_DO;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.GODZINA_OD;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Urlop_Nalezny;

import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.utils.Interval;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.ObiektDanychPracownika;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.GrupyPowiazaniaColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import enums.EtatPracownika;
import enums.SLRodzajeAbsencji;

public class PracownikRepository extends AccessDB {
	public void dodajPracownika(String pmNazwa) {
		QueryBuilder.INSERT()//
				.setFromGenerator(ID_tabeli)//
				.set(Pracownik, pmNazwa)//
				.set(Urlop_Nalezny, 26)//
				.set(ETAT, 1)//
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
				.sorted()//
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
			lvAbsencja.setOkres(new Interval(pmRecord.getAsLocalDate(AbsencjeColumns.Od_kiedy), pmRecord.getAsLocalDate(AbsencjeColumns.Do_kiedy)));
			lvPracownik.setListaAbsencji(Arrays.asList(lvAbsencja));
		}
		lvPracownik.setEtat(EtatPracownika.getByKod(pmRecord.getAsString(ETAT)));
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

	public void ustawEtat(int pmId, EtatPracownika pmEtat) {
		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.ETAT, pmEtat)//
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
				.select(ID_tabeli, Pracownik, AbsencjeColumns.Od_kiedy, AbsencjeColumns.Do_kiedy, AbsencjeColumns.RODZAJ)//
				.joinOn(AbsencjeColumns.ID_pracownika, ID_tabeli).andBeforeOrEqual(AbsencjeColumns.Od_kiedy, pmDataObecnosci)//
				.andAfterOrEqual(AbsencjeColumns.Do_kiedy, pmDataObecnosci)//
				.execute();

		return lvWynik.stream().map(lvRecord -> parsujPracownika(lvRecord)).collect(Collectors.toList());
	}

	public PracownikDTO getPracownik(Integer pmIdPracownika) {

		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(ID_tabeli, //
						ZestawienieColumns.Pracownik, //
						ZestawienieColumns.Urlop_Nalezny, //
						ZestawienieColumns.Data_Urodzenia, //
						ZestawienieColumns.Data_Zatrudnienia, //
						ZestawienieColumns.Data_Zwolnienia, //
						ZestawienieColumns.ETAT)//
				.andWarunek(ID_tabeli, pmIdPracownika)//
				.execute();

		if (lvWynik.isEmpty())
			return null;
		return parsujPracownika(lvWynik.get(0));
	}

	public LocalDate getDataZwolnienia(int pmId) {

		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(ZestawienieColumns.Data_Zwolnienia)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();
		return lvRecordSet.getAsLocalDate(ZestawienieColumns.Data_Zwolnienia);
	}

	public EtatPracownika getEtatPracownika(int pmIdPracownika) {
		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(ZestawienieColumns.ETAT)//
				.andWarunek(ID_tabeli, pmIdPracownika)//
				.execute();
		return EtatPracownika.getByKod(lvRecordSet.getAsString(ZestawienieColumns.ETAT));
	}

	public ObiektDanychPracownika getDanePracownika(int pmId) {
		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(ZestawienieColumns.Data_Urodzenia, Pracownik, ZestawienieColumns.ETAT, ZestawienieColumns.Urlop_Nalezny)//
				.andWarunek(ID_tabeli, pmId)//
				.execute();

		ObiektDanychPracownika lvDane = new ObiektDanychPracownika();
		lvDane.setDataUrodzenia(lvRecordSet.getAsLocalDate(ZestawienieColumns.Data_Urodzenia));
		lvDane.setEtat(EtatPracownika.getByKod(lvRecordSet.getAsString(ZestawienieColumns.ETAT)));
		lvDane.setUrlop(lvRecordSet.getAsInteger(ZestawienieColumns.Urlop_Nalezny));
		lvDane.setIdPracownika(pmId);
		lvDane.setNazwa(lvRecordSet.getAsString(Pracownik));
		return lvDane;
	}

	public void zapiszDanePracownika(ObiektDanychPracownika pmDane) {
		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.ETAT, pmDane.getEtat())//
				.set(ZestawienieColumns.Urlop_Nalezny, pmDane.getUrlop())//
				.set(ZestawienieColumns.Data_Urodzenia, pmDane.getDataUrodzenia())//
				.set(Pracownik, pmDane.getNazwa())//
				.andWarunek(ID_tabeli, pmDane.getIdPracownika())//
				.execute();
	}

	public GodzinyPracyPracownika pobierzGodzinyPracy(Integer pmIdPracownika) {
		LRecordSet lvRecordSet = QueryBuilder.SELECT()//
				.select(GODZINA_OD, GODZINA_DO)//
				.andWarunek(ID_tabeli, pmIdPracownika)//
				.execute();
		return new GodzinyPracyPracownika(lvRecordSet.getAsInteger(GODZINA_OD), lvRecordSet.getAsInteger(GODZINA_DO));

	}

	public void zapiszGodzinyPracy(GodzinyPracyPracownika pmGodziny, Integer pmIdPracownika) {
		QueryBuilder.UPDATE()//
				.set(ZestawienieColumns.GODZINA_OD, pmGodziny.getGodzinaOd())//
				.set(ZestawienieColumns.GODZINA_DO, pmGodziny.getGodzinaDo())//
				.andWarunek(ID_tabeli, pmIdPracownika)//
				.execute();
	}

}
