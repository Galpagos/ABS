package dbAccess.Components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import SprawozdanieMiesieczne.wynikWResultTableWindow;

@SuppressWarnings("serial")
public class ResultTableWindow extends JFrame
{

	private final JPanel contentPanel;
	private JTable mtable;
	private wynikWResultTableWindow mDane;
	private MessageFormat mheader = new MessageFormat("Sprawozdanie");
	private MessageFormat mFinal = new MessageFormat("");

	public MessageFormat getFinal()
	{
		return mFinal;
	}

	public void setFinal(MessageFormat pmFinal)
	{
		mFinal = pmFinal;
	}

	public wynikWResultTableWindow getDane()
	{
		return mDane;
	}

	public MessageFormat getHeader()
	{
		return mheader;
	}

	public void setHeader(MessageFormat pmMheader)
	{
		mheader = pmMheader;
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
				mtable = new javax.swing.JTable()
				{
					@Override
					public Printable getPrintable(PrintMode printMode, MessageFormat headerFormat,
							MessageFormat footerFormat)
					{
						return new TablePrintable(this, printMode, headerFormat, footerFormat);
					}
				};

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
				{
					JButton btnPrint = new JButton("Drukuj");

					btnPrint.addActionListener(e -> {
						try
						{

							mtable.print(JTable.PrintMode.FIT_WIDTH, mheader, mFinal, true, null, false);
						} catch (HeadlessException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (PrinterException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
					buttonPane.add(btnPrint);
				}
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
		pack();
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
