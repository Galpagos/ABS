package Wydruki.ListaObecnosci;

import java.awt.Dimension;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Frames.dbAccess.Components.ResultTableWindow;
import Pracownik.ObslugaPracownka;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import Wydruki.PrzygotowanieDanych.PustePole;

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
		mModel.addColumn("Nazwisko, Imiê");
		mModel.addColumn("Od");
		mModel.addColumn("Do");
		mModel.addColumn("Podpis");
		mModel.addColumn("");
		mModel.addColumn("Nazwisko, Imiê");
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
		mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.getMtable().setShowGrid(false);
		mOknoWyniku.getMtable().setIntercellSpacing(new Dimension(0, 0));
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.getMtable().setBorder(BorderFactory.createEmptyBorder());
		mOknoWyniku.getMtable().setRowHeight(Integer.valueOf(mDane.getWysokoscWiersza()));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
		String lvData = mDataObecnosci.format(fmt);

		mOknoWyniku.setHeader(new MessageFormat(mDane.getNaglowek().replaceAll("<DATA>", mDane.getData().toString())));
		mOknoWyniku.setFinal(new MessageFormat(mDane.getStopka()));
		mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new CellRenderListyObecnosci());
		JTableHeader header = mOknoWyniku.getMtable().getTableHeader();
		header.setDefaultRenderer(new CellRenderListyObecnosci());
		mOknoWyniku.getMtable().setRowSelectionAllowed(false);
		mOknoWyniku.setTytul("Lista obecnoœci " + lvData);

		mOknoWyniku.getMtable().getColumnModel().getColumn(3).setPreferredWidth(350);
		mOknoWyniku.getMtable().getColumnModel().getColumn(8).setPreferredWidth(350);
		mOknoWyniku.getMtable().getColumnModel().getColumn(0).setPreferredWidth(200);
		mOknoWyniku.getMtable().getColumnModel().getColumn(5).setPreferredWidth(200);
		mOknoWyniku.getMtable().getColumnModel().getColumn(4).setPreferredWidth(250);

	}

	public void pokazResult() {
		formatujOknoWyniku();
		mOknoWyniku.pokazWynik();
	}

}
