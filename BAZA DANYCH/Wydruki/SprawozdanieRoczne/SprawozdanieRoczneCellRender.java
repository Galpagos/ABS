package SprawozdanieRoczne;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class SprawozdanieRoczneCellRender extends JLabel implements TableCellRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7885825154010083379L;
	String[] mOpis;

	@Override
	public Component getTableCellRendererComponent(JTable pmTable, Object pmValue, boolean pmIsSelected,
			boolean pmHasFocus, int pmRow, int pmColumn)
	{

		setOpaque(true);
		if (pmColumn >= 1)
			setHorizontalAlignment(SwingConstants.CENTER);
		else
			setHorizontalAlignment(SwingConstants.LEFT);

		if (pmRow % 2 == 0)
		{
			setBackground(Color.white);
			setForeground(Color.black);
		} else
		{
			setBackground(new Color(242, 242, 242));
			setForeground(Color.black);
		}

		if (pmRow == pmTable.getSelectedRow())
		{
			setBackground(new Color(57, 105, 138));
			setForeground(Color.white);
		}

		mOpis = pmValue.toString().split("<X>");

		setText(mOpis[0]);
		if (mOpis.length > 1 && !mOpis[1].equals("<html></html>"))
			setToolTipText(mOpis[1]);
		return this;
	}
}
