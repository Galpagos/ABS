package ProjektGlowny.commons.Components;

import javax.swing.table.AbstractTableModel;

import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.SystemTables;

class LTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private LRecordSet mData;

	@Override
	public int getColumnCount() {
		return mData.size() == 0 ? 0 : mData.get(0).size();
	}

	@Override
	public int getRowCount() {
		return mData.size();
	}

	@Override
	public String getColumnName(int col) {
		return mData.get(0).getColumnName(col);
	}

	@Override
	public Object getValueAt(int row, int col) {

		Object lvValue = mData.get(row).getValue(col);
		if (lvValue == null)
			return null;
		SystemTables lvKolumna = mData.get(row).getColumn(col);
		if (lvKolumna.getOpisFunkcja() == null)
			return lvValue;

		return lvKolumna.getOpisFunkcja().apply(String.valueOf(lvValue)).getOpis();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		for (int row = 0; row < getRowCount(); row++) {
			Object o = getValueAt(row, c);

			if (o != null) {
				return o.getClass();
			}
		}

		return Object.class;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	LTableModel(LRecordSet pmData) {
		mData = pmData;
	}
}
