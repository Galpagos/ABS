package pl.home.components.frames.mainframes;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Data_Zwolnienia;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Frames.dbAccess.Components.JTableModelFromLRecords;
import Frames.dbAccess.Frames.OknoGlowne.InterfejsOknaGlownego;
import Frames.dbAccess.Frames.OknoGlowne.ObslugaOknaGlownego;
import Frames.dbAccess.Frames.OknoGlowne.Przekoder;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.DbSelect;
import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import pl.home.components.frames.src.SrcOknoGlowne;

public class OknoGlowne extends SrcOknoGlowne implements InterfejsOknaGlownego {

	private static final long serialVersionUID = 1L;

	ObslugaOknaGlownego mObsluga = new ObslugaOknaGlownego(this);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new OknoGlowne().setVisible(true);
			}
		});
	}

	public OknoGlowne() {
		super();
		addWindowListener(new Przekoder());
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
		cbCzyUsunieci.addActionListener(lvE -> {
			odswiezTabele();
			odswiezKontrolki();
		});
		btnZatrudnij.addActionListener(lvE -> mObsluga.zatrudnijPracownika());
		tbPracownicy.getSelectionModel().addListSelectionListener(e -> odswiezKontrolki());
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

		DbSelect lvZapytanieLS = QueryBuilder//
				.SELECT()//
				.select(ID_tabeli, Pracownik)//
				.andWarunek(Pracownik + " like \"%" + mFiltrPracownika.getText() + "%\"");

		if (!cbCzyUsunieci.isSelected())
			lvZapytanieLS = lvZapytanieLS.andWarunek(Data_Zwolnienia, null);

		JTableModelFromLRecords lvDTM = new JTableModelFromLRecords(lvZapytanieLS.execute());
		pmTabela.setModel(lvDTM);
		lvDTM.fireTableDataChanged();
		TableColumnModel lvTcm = pmTabela.getColumnModel();
		if (lvTcm.getColumnCount() != 0) {
			lvTcm.removeColumn(lvTcm.getColumn(0));

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(pmTabela.getModel());
			pmTabela.setRowSorter(sorter);

			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
			sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
		}
		if (lvDTM.getRowCount() > 0)
			pmTabela.setRowSelectionInterval(0, 0);
		pmTabela.repaint();

		return pmTabela;
	}

	@Override
	public int getZaznaczenieTabeli() {
		return tbPracownicy.getSelectedRow();
	}

	@Override
	public PracownikDTO getPracownikZTabeli() {
		int lvRow = tbPracownicy.convertRowIndexToModel(getZaznaczenieTabeli());
		Integer lvIdPracownika = (Integer) tbPracownicy.getModel().getValueAt(lvRow, 0);

		return mObsluga.getPracownik(lvIdPracownika);
	}

	@Override
	public void odswiezKontrolki() {
		btnZwolnij.setVisible(getZaznaczenieTabeli() >= 0 && !czyZwolniony(getPracownikZTabeli()));
		btnZatrudnij.setVisible(!btnZwolnij.isVisible());
		btnPokazPracownika.setEnabled(getZaznaczenieTabeli() >= 0);
		btnUsunPracownika.setEnabled(getZaznaczenieTabeli() >= 0);
		btnZwolnij.setEnabled(getZaznaczenieTabeli() >= 0);
		btnZatrudnij.setEnabled(getZaznaczenieTabeli() >= 0);
		repaint();
	}

	private boolean czyZwolniony(PracownikDTO pmPracownik) {

		return pmPracownik.getDataZwolnienia() != null;
	}

	@Override
	public void odswiezTabele() {
		ustawTabele(tbPracownicy);
	}
}
