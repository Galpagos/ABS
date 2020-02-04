package Frames.dbAccess.Components;

import javax.swing.table.AbstractTableModel;

import pl.home.Database.components.LRecordSet;

public class JTableModelFromLRecords extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	LRecordSet mRecords;

	public JTableModelFromLRecords(LRecordSet pmRecords) {
		mRecords = pmRecords;
	}

	@Override
	public int getRowCount() {
		return mRecords.size();
	}

	@Override
	public int getColumnCount() {
		return mRecords.getRecord().size();
	}

	@Override
	public Object getValueAt(int pmRowIndex, int pmColumnIndex) {
		return mRecords.get(pmRowIndex).getValue(pmColumnIndex + 1);
	}

	@Override
	public Class<?> getColumnClass(int c) {
		Object o = getValueAt(0, c);
		if (o != null) {
			return o.getClass();
		}
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
}
