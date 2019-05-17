package dbAccess.Components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ResultTableWindow extends JFrame
{

	private final JPanel contentPanel;
	private JTable mtable;

	public ResultTableWindow()
	{
		contentPanel = (JPanel) getContentPane();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 5, 20));
		setAlwaysOnTop(true);

		{
			JScrollPane scrollPane = new JScrollPane();

			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				mtable = new JTable();
				// mtable.setDefaultRenderer(Object.class, new
				// SprawozdanieMiesieczneCellRender());
				scrollPane.setViewportView(mtable);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0)
					{
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent pmE)
					{
						dispose();

					}
				});
				buttonPane.add(cancelButton);
			}
		}
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void ustawTabele(DefaultTableModel pmTab)
	{
		mtable.setModel(pmTab);
		mtable.getColumnModel().getColumn(0).setMinWidth(200);
		mtable.setCellSelectionEnabled(false);
		mtable.setColumnSelectionAllowed(true);
		mtable.setRowSelectionAllowed(true);
	}

	public void pokazWynik()
	{
		try
		{
			setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void setTytul(String pmTytul)
	{
		setTitle(pmTytul);
	}

	public JTable getMtable()
	{
		return mtable;
	}

	public void setMtable(JTable pmMtable)
	{
		mtable = pmMtable;
	}

}
