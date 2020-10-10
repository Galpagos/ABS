package ProjektGlowny.commons.Components;

import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.SystemTables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class LTable extends JTable {

	private static final long serialVersionUID = 1L;
	private List<SystemTables> mListaDoUkrycia;
	private List<RowSorter.SortKey> mSortKeys;

	public LTable(LRecordSet pmRecords) {
		mListaDoUkrycia = new ArrayList<>();
		mSortKeys = new ArrayList<>();
		TableModel lvDTM = new LTableModel(pmRecords);
		setModel(lvDTM);

		setAutoCreateRowSorter(true);
		// TableRowSorter<TableModel> lvSorter = new
		// TableRowSorter<TableModel>(getModel());
		// setRowSorter(lvSorter);
		if (lvDTM.getRowCount() > 0)
			setRowSelectionInterval(0, 0);

	}

	public void hideColumn(SystemTables pmTabela) {
		mListaDoUkrycia.add(pmTabela);
	}

	public void addSorter(SystemTables pmPole, SortOrder pmOrder) {
		TableColumnModel lvTcm = getColumnModel();
		mSortKeys.add(new RowSorter.SortKey(lvTcm.getColumnIndex(pmPole.getColumnName()), pmOrder));
	}

	public void odswiez() {
		TableColumnModel lvTcm = getColumnModel();
		if (lvTcm.getColumnCount() > 0) {
			getRowSorter().setSortKeys(mSortKeys);
			for (SystemTables lvTable : mListaDoUkrycia)
				lvTcm.removeColumn(lvTcm.getColumn(lvTcm.getColumnIndex(lvTable.getColumnName())));

			repaint();
		}
	}

	public void reload(LRecordSet pmRecords) {
		TableModel lvDTM = new LTableModel(pmRecords);
		int lvIndex = getSelectionModel().getLeadSelectionIndex();
		setModel(lvDTM);
		odswiez();
		getSelectionModel().setSelectionInterval(lvIndex, lvIndex);
	}
}
