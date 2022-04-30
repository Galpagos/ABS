package Wydruki.ListaObecnosci;

import java.util.HashSet;
import java.util.Set;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import Wydruki.PrzygotowanieDanych.PustePole;

public class CellRenderListyObecnosci extends JLabel implements TableCellRenderer {

	private Set<Integer> mBoldLines;
	private static final long serialVersionUID = 1L;
	private int mSize = 24;

	public CellRenderListyObecnosci() {
		mBoldLines = new HashSet<>();
	}

	@Override
	public Component getTableCellRendererComponent(JTable pmTable, Object pmValue, boolean pmIsSelected, boolean pmHasFocus, int pmRow, int pmColumn) {
		if (pmValue != null && (pmValue.getClass() == PustePole.class || (pmRow == -1 && pmColumn == 4))) {
			setBorder(BorderFactory.createEmptyBorder());
			setText("");
		} else
			setBorder(BorderFactory.createLineBorder(Color.black));

		setOpaque(true);

		setBackground(Color.white);
		setForeground(Color.black);

		if (mBoldLines.contains(pmRow))
			setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, Color.black));
		if (pmValue != null && pmValue.getClass() != PustePole.class)
			setText("  " + pmValue.toString());
		setHorizontalAlignment(SwingConstants.LEFT);
		setFont(new Font("TimesRoman", Font.PLAIN, mSize));
		if (pmRow == -1) {
			setFont(new Font("TimesRoman", Font.BOLD, mSize));
			setHorizontalAlignment(CENTER);
		}

		if ("NB".equals(pmValue) || "podpis".equals(pmValue)) {
			setFont(new Font("TimesRoman", Font.BOLD, mSize));
			setForeground(Color.red);
			setHorizontalAlignment(CENTER);
		}
		return this;
	}

	public CellRenderListyObecnosci setSize(Integer pmSize) {
		if (pmSize != null)
			mSize = pmSize;
		return this;
	}

	public CellRenderListyObecnosci setBoldLines(Set<Integer> pmBoldLines) {
		if (pmBoldLines != null)
			mBoldLines = pmBoldLines;
		return this;
	}
}
