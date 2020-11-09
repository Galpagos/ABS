package Wydruki.ListaObecnosci;

import java.util.List;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableModel;

import Frames.dbAccess.Components.ResultTableWindow;
import Pracownik.ObslugaPracownka;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import Wydruki.PrzygotowanieDanych.PustePole;
import enums.RodzajWydruku;
import pl.home.components.frames.mainframes.ReportsResult;
import pl.home.components.frames.parameters.ReportsResultIn;

public class ListaObecnosci {
	private DefaultTableModel mModel = new DefaultTableModel();
	private List<PracownikDTO> mListaLewa;
	private List<PracownikDTO> mListaPrawa;
	private LocalDate mDataObecnosci;
	private List<PracownikDTO> mListaNieobecnosci;
	private DaneDoListyObecnosci mDane;
	private ResultTableWindow mOknoWyniku;

	public ListaObecnosci(DaneDoListyObecnosci pmDane) {
		mDane = pmDane;

		mListaNieobecnosci = new ObslugaPracownka().getListaNieobecnych(pmDane.getData());
		mListaLewa = pmDane.getListaLewa();
		mListaPrawa = pmDane.getListaPrawa();
		mDataObecnosci = pmDane.getData();

		wyrownajListy();
		utworzNaglowek();
		uzupelnijRecordy();
		pokazResult();
	}

	private void uzupelnijRecordy() {
		Object[] lvRekord = new Object[9];
		for (int i = 0; i < mListaLewa.size(); i++) {
			if (mListaLewa.get(i).getId() != 0) {
				lvRekord[0] = mListaLewa.get(i);
				lvRekord[1] = "";
				lvRekord[2] = "";
				if (czyObecny(mListaLewa.get(i)))
					lvRekord[3] = "";
				else
					lvRekord[3] = "NB";
			} else {
				lvRekord[0] = new PustePole();
				lvRekord[1] = new PustePole();
				lvRekord[2] = new PustePole();
				lvRekord[3] = new PustePole();
			}
			lvRekord[4] = new PustePole();

			if (mListaPrawa.get(i).getId() != 0) {
				lvRekord[5] = mListaPrawa.get(i);
				lvRekord[6] = "";
				lvRekord[7] = "";
				if (czyObecny(mListaPrawa.get(i)))
					lvRekord[8] = "";
				else
					lvRekord[8] = "NB";
			} else {
				lvRekord[5] = new PustePole();
				lvRekord[6] = new PustePole();
				lvRekord[7] = new PustePole();
				lvRekord[8] = new PustePole();
			}
			mModel.addRow(lvRekord);
		}
	}

	private void utworzNaglowek() {
		mModel.addColumn("Nazwisko, Imię");
		mModel.addColumn("Od");
		mModel.addColumn("Do");
		mModel.addColumn("Podpis");
		mModel.addColumn("");
		mModel.addColumn("Nazwisko, Imię");
		mModel.addColumn("Od");
		mModel.addColumn("Do");
		mModel.addColumn("Podpis");
	}

	private void wyrownajListy() {
		PracownikDTO lvPusty = new PracownikDTO();
		while (mListaLewa.size() != mListaPrawa.size()) {
			if (mListaLewa.size() < mListaPrawa.size())
				mListaLewa.add(lvPusty);
			else
				mListaPrawa.add(lvPusty);
		}
	}

	private boolean czyObecny(PracownikDTO pmPracownik) {
		long lvIlosc = mListaNieobecnosci.stream()//
				.filter(e -> e.getId() == pmPracownik.getId()).count();

		return (lvIlosc == 0);

	}

	private void formatujOknoWyniku() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
		String lvData = mDataObecnosci.format(fmt);

		ReportsResultIn lvParams = ReportsResultIn//
				.builder()//
				.rodzajWydruku(RodzajWydruku.LISTA_OBECNOSCI)//
				.model(mModel)//
				.wysokoscWiersza(mDane.getWysokoscWiersza())//
				.header(new MessageFormat(mDane.getNaglowek().replaceAll("<DATA>", mDane.getData().toString())))//
				.textFinal(new MessageFormat(mDane.getStopka()))//
				.tytul("Lista obecności " + lvData)//
				.build();

		new ReportsResult(lvParams).get();
	}

	public void pokazResult() {
		formatujOknoWyniku();
	}

}
