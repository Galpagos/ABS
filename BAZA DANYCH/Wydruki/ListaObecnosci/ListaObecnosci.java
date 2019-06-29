package ListaObecnosci;

import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;

import PrzygotowanieDanych.PustePole;
import dbAccess.Components.ResultTableWindow;

public class ListaObecnosci
{
	private DefaultTableModel mModel;

	public void pokazResult()
	{
		ResultTableWindow mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.getMtable().setShowGrid(false);
		mOknoWyniku.getMtable().setIntercellSpacing(new Dimension(0, 0));
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new CellRenderListyObecnosci());
		mOknoWyniku.getMtable().setDefaultRenderer(PustePole.class, new CellRenderPustePole());
		mOknoWyniku.setTytul("Lista obecnoœci");
		mOknoWyniku.pokazWynik();
	}

}
