package Frames.dbAccess.Frames.OknoGlowne;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;
import dbAccess.dbAccess.MyTableModel;

public class OknoGlowne extends SrcOknoGlowne implements InterfejsOknaGlownego {

	private static final long serialVersionUID = 1L;

	ObslugaOknaGlownego mObsluga = new ObslugaOknaGlownego(this);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new OknoGlowne().setVisible(true);
			}
		});
	}

	public OknoGlowne() {
		super();
		// addWindowListener(new Przekoder());
		ustawTabele(tbPracownicy);
		btnPokazPracownika.addActionListener(lvE -> mObsluga.pokazPracownika());
		btnDodajPracownika.addActionListener(lvE -> mObsluga.dodajPracownika());
		btnUsunPracownika.addActionListener(lvE -> mObsluga.usunPracownika());
		btnWyjcie.addActionListener(lvE -> System.exit(0));
		btnPokazNieobecnych.addActionListener(lvE -> mObsluga.pokazNieobecnych());
		btnDodajDzienWolny.addActionListener(lvE -> mObsluga.dodajDzienWolny());
		btnPokazDniWolne.addActionListener(lvE -> mObsluga.pokazDniWolne());
		btnSprawozdanie.addActionListener(lvE -> mObsluga.sprawozdanie());
		btnZwolnij.addActionListener(lvE -> mObsluga.zwolnijPracownika());
		mFiltrPracownika.getDocument().addDocumentListener(ustawListenerFiltruPracownika());
		repaint();
	}

	private DocumentListener ustawListenerFiltruPracownika() {
		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent pmE) {
				ustawTabele(tbPracownicy);
			}

			@Override
			public void insertUpdate(DocumentEvent pmE) {
				ustawTabele(tbPracownicy);
			}

			@Override
			public void removeUpdate(DocumentEvent pmE) {
				ustawTabele(tbPracownicy);
			}
		};
	}

	private JTable ustawTabele(JTable pmTabela) {
		try {
			String lvZapytanie = //
					"Select " + ZestawienieBean.getKolumnaID() + "," + ZestawienieBean.getKolumnaNazwaPracownika()//
							+ " from " + ZestawienieBean.getNazwaTabeli()//
							+ " where " + ZestawienieBean.getKolumnaNazwaPracownika() + " like \"%"
							+ mFiltrPracownika.getText() + "%\"" + //
							" AND Data_Zwolnienia is null";
			MyTableModel lvDTM = dbAccess.modelTabeliDB(lvZapytanie);
			pmTabela.setModel(lvDTM);
			lvDTM.fireTableDataChanged();
			TableColumnModel lvTcm = pmTabela.getColumnModel();
			for (int lvKolumna = pmTabela.getColumnCount() - 1; lvKolumna >= 0; lvKolumna--) {

				if (pmTabela.getColumnName(lvKolumna).contains("ID")) {
					TableColumn k = lvTcm.getColumn(lvKolumna);
					lvTcm.removeColumn(k);
				}
			}

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(pmTabela.getModel());
			pmTabela.setRowSorter(sorter);

			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
			sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			pmTabela.repaint();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pmTabela;
	}

	@Override
	public int getZaznaczenieTabeli() {
		return tbPracownicy.getSelectedRow();
	}

	@Override
	public PracownikDTO getPracownikZTabeli() {
		PracownikDTO lvPracownik = new PracownikDTO();
		int lvRow = tbPracownicy.convertRowIndexToModel(getZaznaczenieTabeli());
		lvPracownik.setId((Integer) tbPracownicy.getModel().getValueAt(lvRow, 0));
		lvPracownik.setNazwa((String) tbPracownicy.getModel().getValueAt(lvRow, 1));

		return lvPracownik;
	}

	@Override
	public void odswiezTabele() {
		ustawTabele(tbPracownicy);
	}
}
