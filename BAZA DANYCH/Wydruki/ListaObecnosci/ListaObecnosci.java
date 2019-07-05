package ListaObecnosci;

import java.awt.Dimension;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Pracownik.ObslugaPracownka;
import PrzygotowanieDanych.PracownikDTO;
import PrzygotowanieDanych.PustePole;
import dbAccess.Components.ResultTableWindow;

public class ListaObecnosci
{
	private DefaultTableModel mModel = new DefaultTableModel();
	private List<PracownikDTO> mListaLewa;
	private List<PracownikDTO> mListaPrawa;
	private DateTime mDataObecnosci;
	private List<PracownikDTO> mListaNieobecnosci;

	public ListaObecnosci(DateTime pmDataObecnosci, List<PracownikDTO> pmLewaLista, List<PracownikDTO> pmListaPrawa)
	{
		mListaLewa = pmLewaLista;
		mListaPrawa = pmListaPrawa;
		mDataObecnosci = pmDataObecnosci;
		mListaNieobecnosci = new ObslugaPracownka().getListaNieobecnych(mDataObecnosci.toDate());

		wyrownajListy();
		utworzNaglowek();
		uzupelnijRecordy();
		pokazResult();
	}

	private void uzupelnijRecordy()
	{
		Object[] lvRekord = new Object[9];
		for (int i = 0; i < mListaLewa.size(); i++)
		{
			if (mListaLewa.get(i).getId() != 0)
			{
				lvRekord[0] = mListaLewa.get(i);
				lvRekord[1] = "";
				lvRekord[2] = "";
				if (czyObecny(mListaLewa.get(i)))
					lvRekord[3] = "";
				else
					lvRekord[3] = "NB";
			} else
			{
				lvRekord[0] = new PustePole();
				lvRekord[1] = new PustePole();
				lvRekord[2] = new PustePole();
				lvRekord[3] = new PustePole();
			}
			lvRekord[4] = new PustePole();

			if (mListaPrawa.get(i).getId() != 0)
			{
				lvRekord[5] = mListaPrawa.get(i);
				lvRekord[6] = "";
				lvRekord[7] = "";
				if (czyObecny(mListaPrawa.get(i)))
					lvRekord[8] = "";
				else
					lvRekord[8] = "NB";
			} else
			{
				lvRekord[5] = new PustePole();
				lvRekord[6] = new PustePole();
				lvRekord[7] = new PustePole();
				lvRekord[8] = new PustePole();
			}
			mModel.addRow(lvRekord);
		}
	}

	private void utworzNaglowek()
	{
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

	private void wyrownajListy()
	{
		PracownikDTO lvPusty = new PracownikDTO();
		while (mListaLewa.size() != mListaPrawa.size())
		{
			if (mListaLewa.size() < mListaPrawa.size())
				mListaLewa.add(lvPusty);
			else
				mListaPrawa.add(lvPusty);
		}
	}

	private boolean czyObecny(PracownikDTO pmPracownik)
	{
		long lvIlosc = mListaNieobecnosci.stream()//
				.filter(e -> e.getId() == pmPracownik.getId()).count();

		return (lvIlosc == 0);

	}

	public void pokazResult()
	{
		ResultTableWindow mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.getMtable().setShowGrid(false);
		mOknoWyniku.getMtable().setIntercellSpacing(new Dimension(0, 0));
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.getMtable().setBorder(BorderFactory.createEmptyBorder());
		mOknoWyniku.getMtable().setRowHeight(40);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE, d MMMM yyyy");
		String lvData = mDataObecnosci.toString(fmt);

		mOknoWyniku
				.setHeader(new MessageFormat("Lista obecnoœci                                              " + lvData));
		mOknoWyniku.setFinal(new MessageFormat(
				"Proszê nie podpisywaæ innych pracowników. Ka¿dy z pracowników ma obowi¹zek potwierdziæ swoj¹ obecnoœæ w³asnorêcznym podpisem. "));
		mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new CellRenderListyObecnosci());
		JTableHeader header = mOknoWyniku.getMtable().getTableHeader();
		header.setDefaultRenderer(new CellRenderListyObecnosci());
		mOknoWyniku.getMtable().setRowSelectionAllowed(false);
		mOknoWyniku.setTytul("Lista obecnoœci");

		mOknoWyniku.getMtable().getColumnModel().getColumn(3).setPreferredWidth(350);
		mOknoWyniku.getMtable().getColumnModel().getColumn(8).setPreferredWidth(350);
		mOknoWyniku.getMtable().getColumnModel().getColumn(0).setPreferredWidth(200);
		mOknoWyniku.getMtable().getColumnModel().getColumn(5).setPreferredWidth(200);
		mOknoWyniku.getMtable().getColumnModel().getColumn(4).setPreferredWidth(250);

		mOknoWyniku.pokazWynik();
	}

}
