package Frames.dbAccess.Components;

import javax.swing.table.AbstractTableModel;

import pl.home.Database.components.LRecordSet;

public class LTableModel extends AbstractTableModel {

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
		return mData.get(row).getValue(col);
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

	public LTableModel(LRecordSet pmData) {
		mData = pmData;
	}
}
