package Absencja;

import java.time.LocalDate;
import java.util.Optional;

import Datownik.Interval;
import Enums.SLRodzajeAbsencji;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import pl.home.Database.components.AccessDB;
import pl.home.Database.components.LRecordSet;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;

public class AbsencjaRepositoryDB extends AccessDB implements AbsencjaRepositor {

	@Override
	public Object[][] getAbsencjePracownika(int pmId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[][] getAbsencjePoId(int pmId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dodajAbsencje(AbsencjaDTO pmAbs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void usunAbsencje(int pmID) {
		// TODO Auto-generated method stub

	}

	@Override
	public int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Optional<AbsencjaDTO> pobierzAbsencjePoId(int pmId) {
		LRecordSet lvDane = executeQuery(
				"Select Id_tabeli, Id_pracownika,Od_kiedy,Do_kiedy,RODZAJ,EKWIWALENT from Absencje where id_tabeli="
						+ pmId);
		if (lvDane == null || lvDane.isEmpty())
			return Optional.empty();
		return Optional.of(AbsencjaDTO.builder()//
				.setId(lvDane.getAsInteger(AbsencjeColumns.ID_tabeli))//
				.setIdPracownika(lvDane.getAsInteger(AbsencjeColumns.ID_pracownika))//
				.setRodzaj(SLRodzajeAbsencji.getByKod(lvDane.getAsString(AbsencjeColumns.RODZAJ)))//
				.setProcent(SLEkwiwalentZaUrlop.getByKod(lvDane.getAsString(AbsencjeColumns.EKWIWALENT)))//
				.setOkres(new Interval(lvDane.getAsTimestamp(AbsencjeColumns.Od_kiedy),
						lvDane.getAsTimestamp(AbsencjeColumns.Do_kiedy))));
	}

	@Override
	public int ileDniWolnych(LocalDate pmLocalDate, LocalDate pmLocalDate2) {

		return 0;
	}

}
