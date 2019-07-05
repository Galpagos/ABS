package ListaObecnosci;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import PrzygotowanieDanych.PustePole;

public class CellRenderListyObecnosci extends JLabel implements TableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mSize = 18;

	@Override
	public Component getTableCellRendererComponent(JTable pmTable, Object pmValue, boolean pmIsSelected,
			boolean pmHasFocus, int pmRow, int pmColumn)
	{
		if (pmValue.getClass() == PustePole.class || (pmRow == -1 && pmColumn == 4))
		{
			setBorder(BorderFactory.createEmptyBorder());
			setText("");
		} else
			setBorder(BorderFactory.createLineBorder(Color.black));

		setOpaque(true);

		setBackground(Color.white);
		setForeground(Color.black);

		if (pmValue != null && pmValue.getClass() != PustePole.class)
			setText(pmValue.toString());

		setFont(new Font("TimesRoman", Font.PLAIN, mSize));
		if (pmRow == -1)
		{
			setFont(new Font("TimesRoman", Font.BOLD, mSize));
			setHorizontalAlignment(CENTER);
		}

		if (pmValue.equals("NB"))
		{
			setFont(new Font("TimesRoman", Font.BOLD, mSize));
			setForeground(Color.red);
			setHorizontalAlignment(CENTER);
		}
		return this;
	}

	public CellRenderListyObecnosci setSize(int pmSize)
	{
		mSize = pmSize;
		return this;
	}
}
