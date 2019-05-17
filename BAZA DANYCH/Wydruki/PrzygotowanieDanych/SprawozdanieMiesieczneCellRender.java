package PrzygotowanieDanych;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import Enums.SLRodzajeAbsencji;

public class SprawozdanieMiesieczneCellRender extends JLabel implements TableCellRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7885825154010083379L;
	String mOpis;

	@Override
	public Component getTableCellRendererComponent(JTable pmTable, Object pmValue, boolean pmIsSelected,
			boolean pmHasFocus, int pmRow, int pmColumn)
	{
		if (pmColumn >= 1)
			setHorizontalAlignment(SwingConstants.CENTER);
		else
			setHorizontalAlignment(SwingConstants.LEFT);
		mOpis = "";
		setOpaque(true);

		if (pmRow % 2 == 0)
		{
			setBackground(Color.white);
			setForeground(Color.black);
		} else
		{
			setBackground(new Color(242, 242, 242));
			setForeground(Color.black);
		}

		if (pmValue != null)
		{
			if (pmValue.toString() != "")
			{
				Color newColor;
				if (pmValue.toString() == "Sobota" || pmValue.toString() == "Niedziela")
				{
					newColor = new Color(255, 255, 0);
					if (pmValue.toString() == "Sobota")
					{
						mOpis = "Sb";
						setToolTipText("Sobota");
					} else
					{
						mOpis = "Ndz";
						setToolTipText("Niedziela");
					}
				} else
				{
					SLRodzajeAbsencji lvRodzaj = SLRodzajeAbsencji.AbsencjaPoNazwie(pmValue.toString());
					if (lvRodzaj != null)
					{
						newColor = lvRodzaj.getColor();
						mOpis = lvRodzaj.getSkrot();
						setToolTipText(lvRodzaj.toString());
					} else
					{
						newColor = getBackground();
					}

				}
				setBackground(newColor);
			}

		}
		if (pmRow == pmTable.getSelectedRow())
		{
			setBackground(new Color(57, 105, 138));
			setForeground(Color.white);

		}
		if (pmValue != null && mOpis == "")
			mOpis = pmValue.toString();
		setText(mOpis);

		return this;
	}
}
