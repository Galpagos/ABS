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
				.andWarunek(Pracownik + " like \"%" + mFiltrPracownika.getText() + "%\"")//
				.andWarunek(Data_Zwolnienia, null);

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
		pmTabela.repaint();

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
