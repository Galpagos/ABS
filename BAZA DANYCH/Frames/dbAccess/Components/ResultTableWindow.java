package dbAccess.Components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Pracownik.ObslugaPracownka;
import PrzygotowanieDanych.AbsencjaDTO;
import PrzygotowanieDanych.PracownikDTO;
import SprawozdanieMiesieczne.wynikWResultTableWindow;

@SuppressWarnings("serial")
public class ResultTableWindow extends JFrame
{

	private final JPanel contentPanel;
	private JTable mtable;
	private wynikWResultTableWindow mDane;

	public wynikWResultTableWindow getDane()
	{
		return mDane;
	}

	public void setDane(wynikWResultTableWindow pmDane)
	{
		mDane = pmDane;
	}

	public ResultTableWindow()
	{
		contentPanel = (JPanel) getContentPane();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 5, 20));

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
		mtable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent pmE)
			{
				JTable target = (JTable) pmE.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();

				if ((target.getValueAt(row, column) != null)
						&& target.getValueAt(row, column).getClass() == PracownikDTO.class)
				{
					PracownikDTO lvPracownik = (PracownikDTO) target.getValueAt(row, column);
					new ObslugaPracownka().pokazPracownika(lvPracownik);
					int k = target.getModel().getColumnCount();
					Object[] lvPrzeliczonyWiersz = mDane.przeliczWierszTabeli(lvPracownik);
					for (int i = 0; i < k; i++)
					{
						target.getModel().setValueAt(lvPrzeliczonyWiersz[i], row, i);
					}
				}
				if ((target.getValueAt(row, column) != null)
						&& target.getValueAt(row, column).getClass() == AbsencjaDTO.class)
				{
					PracownikDTO lvPracownik = (PracownikDTO) target.getValueAt(row, 0);
					new ObslugaPracownka().pokazPracownika(lvPracownik);
					int k = target.getModel().getColumnCount();
					Object[] lvPrzeliczonyWiersz = mDane.przeliczWierszTabeli(lvPracownik);
					for (int i = 0; i < k; i++)
					{
						target.getModel().setValueAt(lvPrzeliczonyWiersz[i], row, i);
					}
					repaint();
				}
			}
		});

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
