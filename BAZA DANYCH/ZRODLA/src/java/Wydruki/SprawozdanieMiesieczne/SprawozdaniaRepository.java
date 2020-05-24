package Wydruki.SprawozdanieMiesieczne;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.utils.Interval;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import enums.SLRodzajeAbsencji;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;

public class SprawozdaniaRepository {
	public List<AbsencjaDTO> getAbsencjeDlaPracownika(int pmIdPrac, List<SLRodzajeAbsencji> pmList) {

		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(AbsencjeColumns.ID_tabeli, AbsencjeColumns.ID_pracownika, AbsencjeColumns.Od_kiedy,
						AbsencjeColumns.Do_kiedy, AbsencjeColumns.RODZAJ, AbsencjeColumns.EKWIWALENT)//
				.andWarunek(AbsencjeColumns.ID_pracownika, pmIdPrac)//
				.andWarunek(warunekNaModul(pmList))//
				.execute();

		return lvWynik.stream()//
				.map(lvRecord -> parsujAbsencje(lvRecord))//
				.collect(Collectors.toList());
	}

	private AbsencjaDTO parsujAbsencje(LRecord pmRecord) {
		return AbsencjaDTO//
				.builder()//
				.setId(pmRecord.getAsInteger(AbsencjeColumns.ID_tabeli))//
				.setIdPracownika(pmRecord.getAsInteger(AbsencjeColumns.ID_pracownika))//
				.setOkres(new Interval(pmRecord.getAsDate(AbsencjeColumns.Od_kiedy),
						pmRecord.getAsDate(AbsencjeColumns.Do_kiedy)))//
				.setProcent(SLEkwiwalentZaUrlop.getByKod(pmRecord.getAsString(AbsencjeColumns.EKWIWALENT)))//
				.setRodzaj(SLRodzajeAbsencji.getByKod(pmRecord.getAsString(AbsencjeColumns.RODZAJ)));
	}

	public List<Interval> getDniWolne() {
		LRecordSet lvWynik = QueryBuilder.SELECT()//
				.select(DniWolneColumns.Data)//
				.execute();
		List<Interval> lvDniWolne = new ArrayList<>();
		for (LRecord lvElem : lvWynik) {
			LocalDate lvDT = lvElem.getAsLocalDate(DniWolneColumns.Data);
			Interval lvInterval = new Interval(lvDT, lvDT);
			lvDniWolne.add(lvInterval);
		}
		return lvDniWolne;
	}

	private String warunekNaModul(List<SLRodzajeAbsencji> pmLista) {
		if (pmLista == null || pmLista.size() == 0)
			return "";
		StringBuilder lvWarunek = new StringBuilder();
		lvWarunek.append(" RODZAJ ");
		lvWarunek.append(" IN ( 'TEST");
		for (SLRodzajeAbsencji lvRodzaj : pmLista) {
			lvWarunek.append("','");
			lvWarunek.append(lvRodzaj.getKod());
		}
		lvWarunek.append("')");
		return lvWarunek.toString();

	}

	public String getUrlopNalezny(int pmId) {
		return QueryBuilder.SELECT().select(ZestawienieColumns.Urlop_Nalezny)
				.andWarunek(ZestawienieColumns.ID_tabeli, pmId).execute().getAsString(ZestawienieColumns.Urlop_Nalezny);

	}
}
