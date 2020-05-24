package Wydruki.ListaPlac;

import java.time.YearMonth;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import Frames.dbAccess.Components.ResultTableWindow;
import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import Wydruki.SprawozdanieMiesieczne.wynikWResultTableWindow;
import pl.home.ListaPlac.ListaPlac;
import pl.home.ListaPlac.MiesiecznaPlacaPracownika;

public class ListaPlacWydruk implements wynikWResultTableWindow {
	private ResultTableWindow mOknoWyniku;
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
				.forEachOrdered(lvWyplata -> utworzRekord(lvWyplata));
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
		mModel.addColumn("Pracownik");
		mModel.addColumn("Kwota za pracę");
		mModel.addColumn("Kwota za chorobę");
		mModel.addColumn("Kwota za urlopy");
		mModel.addColumn("Razem");

	}

	@Override
	public Object[] przeliczWierszTabeli(PracownikDTO pmLvPrac) {

		return null;
	}

	private void pokazResult() {
		mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.setDane(this);
		// mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new
		// SprawozdanieRoczneCellRender());
		// mOknoWyniku.getMtable().setDefaultRenderer(PustePole.class, new
		// CellRenderPustePole());
		mOknoWyniku.setTytul("Lista Plac " + mDane.getData());
		mOknoWyniku.pokazWynik();
	}
}
