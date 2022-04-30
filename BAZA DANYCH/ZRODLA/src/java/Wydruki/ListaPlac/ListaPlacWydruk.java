package Wydruki.ListaPlac;

import java.util.Arrays;
import java.util.List;

import java.time.YearMonth;

import javax.swing.table.DefaultTableModel;

import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import Wydruki.SprawozdanieMiesieczne.wynikWResultTableWindow;
import enums.RodzajWydruku;
import pl.home.ListaPlac.ListaPlac;
import pl.home.ListaPlac.MiesiecznaPlacaPracownika;
import pl.home.components.frames.mainframes.ReportsResult;
import pl.home.components.frames.parameters.ReportsResultIn;

public class ListaPlacWydruk implements wynikWResultTableWindow {
	private DaneDoSprawozdaniaMiesiecznego mDane;
	DefaultTableModel mModel = new DefaultTableModel();

	public ListaPlacWydruk(DaneDoSprawozdaniaMiesiecznego pmDane) {

		mDane = pmDane;

		przygotujNaglowek();
		utworzWierszeTabeli();
		pokazResult();

	}

	private void utworzWierszeTabeli() {
		YearMonth lvMiesiac = YearMonth.from(mDane.getData());

		ListaPlac lvPlace = new ListaPlac(lvMiesiac);

		List<MiesiecznaPlacaPracownika> lvPlacePracownikow = lvPlace.wyliczWyplate(mDane.getListaPracownikow());
		lvPlacePracownikow//
				.stream()//
				.forEachOrdered(this::utworzRekord);
	}

	private void utworzRekord(MiesiecznaPlacaPracownika pmWyplata) {

		Object[] lvRekord = new Object[5];
		lvRekord[0] = pmWyplata.getPracownik();
		lvRekord[1] = pmWyplata.getKwotaZaPrace();
		lvRekord[2] = pmWyplata.getKwotaChorobowa();
		lvRekord[3] = pmWyplata.getKwotaZaUrlopy();
		lvRekord[4] = pmWyplata.getKwotaRazem();
		mModel.addRow(lvRekord);

	}

	private void przygotujNaglowek() {
		Arrays.stream(ListaPlacColumnEnum.values())//
				.forEach(lvEnumValue -> mModel.addColumn(lvEnumValue.getColumnName()));
	}

	@Override
	public Object[] przeliczWierszTabeli(PracownikDTO pmLvPrac) {

		return null;
	}

	private void pokazResult() {
		ReportsResultIn lvParams = ReportsResultIn//
				.builder()//
				.dane(this)//
				.model(mModel)//
				.rodzajWydruku(RodzajWydruku.LISTA_PLAC)//
				.tytul("Lista Plac " + mDane.getData())//
				.build();

		new ReportsResult(lvParams).get();
	}
}
