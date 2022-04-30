package Frames.dbAccess.Components;

import java.util.Set;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ListaObecnosciCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;
	private Set<Integer> mBoldLines;
	public ListaObecnosciCellRenderer(Set<Integer> pmBoldLines) {
		super();
		mBoldLines = pmBoldLines;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> pmList, Object pmValue, int pmIndex, boolean pmIsSelected, boolean pmCellHasFocus) {

		Component lvWynik = super.getListCellRendererComponent(pmList, pmValue, pmIndex, pmIsSelected, pmCellHasFocus);
		if (mBoldLines.contains(pmIndex))
			lvWynik.setBackground(Color.gray);
		return lvWynik;

	}
}