package ListaObecnosci;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CellRenderListyObecnosci extends JLabel implements TableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable pmTable, Object pmValue, boolean pmIsSelected,
			boolean pmHasFocus, int pmRow, int pmColumn)
	{

		setBorder(BorderFactory.createLineBorder(Color.black));

		setOpaque(true);

		if (pmRow == pmTable.getSelectedRow())
		{
			setBackground(new Color(57, 105, 138));
			setForeground(Color.white);

		}
		if (pmValue != null)
			setText(pmValue.toString());

		return this;
	}
}
