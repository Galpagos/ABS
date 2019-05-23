package dbAccess.Frames.OknoGlowne;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Wersja.Przekod;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;
import dbAccess.dbAccess.MyTableModel;

@SuppressWarnings("serial")
public class SrcOknoGlowne extends JFrame implements InterfejsOknaGlownego
{
	ObslugaOknaGlownego mObsluga = new ObslugaOknaGlownego(this);

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					SrcOknoGlowne mFrame = new SrcOknoGlowne();
					mFrame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private JTable tbPracownicy;
	private JButton btnDodajPracownika;
	private JButton btnPokazPracownika;
	private JButton btnUsunPracownika;
	private JScrollPane mScrollPane;
	private ZestawienieBean mPracownik;
	private JTextField mFiltrPracownika;

	public SrcOknoGlowne() throws SQLException
	{
		addWindowListener(new WindowListener()
		{
			
			@Override
			public void windowOpened(WindowEvent pmArg0)
			{
				try
				{
					Przekod.Wykonaj();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void windowIconified(WindowEvent pmArg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent pmArg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent pmArg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent pmArg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent pmArg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent pmArg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		JPanel contentPane;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		tbPracownicy = new JTable();
		tbPracownicy.setAutoCreateRowSorter(true);
		mFiltrPracownika = new JTextField("");
		ustawTabele(tbPracownicy);
		mScrollPane = new JScrollPane(tbPracownicy);
		mScrollPane.setAutoscrolls(true);
		mScrollPane.setPreferredSize(new Dimension(250, 202));
		mScrollPane.setMaximumSize(new Dimension(300, 267));
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mScrollPane.setBounds(5, 5, 250, 438);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		contentPane.setLayout(null);

		contentPane.add(mScrollPane);

		JPanel mButtonPanel = new JPanel();
		mButtonPanel.setBounds(255, 5, 547, 503);
		contentPane.add(mButtonPanel);
		mButtonPanel.setLayout(null);

		btnPokazPracownika = new JButton("Pokaz Pracownika");
		btnPokazPracownika.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.pokazPracownika();
			}
		});
		btnPokazPracownika.setBounds(46, 92, 191, 39);
		mButtonPanel.add(btnPokazPracownika);

		btnDodajPracownika = new JButton("Dodaj Pracownika");
		btnDodajPracownika.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.dodajPracownika();
			}
		});
		
		btnDodajPracownika.setBounds(46, 40, 191, 39);
		mButtonPanel.add(btnDodajPracownika);

		btnUsunPracownika = new JButton("Usu\u0144 Pracownika");
		btnUsunPracownika.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.usunPracownika();
			}
		});
		btnUsunPracownika.setBounds(46, 144, 191, 39);
		mButtonPanel.add(btnUsunPracownika);

		JButton btnWyjcie = new JButton("Wyj\u015Bcie");
		btnWyjcie.addActionListener(event -> {
			System.exit(0);
		});
		btnWyjcie.setBounds(402, 444, 97, 25);
		mButtonPanel.add(btnWyjcie);

		JButton btnPokazNieobecnych = new JButton("Poka\u017C nieobecnych");
		btnPokazNieobecnych.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					mObsluga.pokazNieobecnych();
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
		});
		btnPokazNieobecnych.setBounds(46, 250, 191, 39);
		mButtonPanel.add(btnPokazNieobecnych);

		JButton btnDodajDzienWolny = new JButton("Dodaj Dzie\u0144 Wolny");
		btnDodajDzienWolny.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mObsluga.dodajDzienWolny();
			}
		});
		btnDodajDzienWolny.setBounds(282, 40, 179, 39);
		mButtonPanel.add(btnDodajDzienWolny);

		JButton btnPokazDniWolne = new JButton("Poka\u017C Dni Wolne");
		btnPokazDniWolne.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.pokazDniWolne();
			}
		});
		btnPokazDniWolne.setBounds(282, 92, 179, 39);
		mButtonPanel.add(btnPokazDniWolne);

		JButton btnSprawozdanie = new JButton("Sprawozdanie");
		btnSprawozdanie.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.sprawozdanie();
			}
		});
		btnSprawozdanie.setBounds(282, 151, 140, 25);
		mButtonPanel.add(btnSprawozdanie);

		JButton btnZwolnij = new JButton("ZwolnijPracownika");
		btnZwolnij.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.zwolnijPracownika();
			}
		});
		btnZwolnij.setBounds(46, 198, 191, 39);
		mButtonPanel.add(btnZwolnij);

		mFiltrPracownika.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent pmE)
			{
				ustawTabele(tbPracownicy);
			}

			@Override
			public void insertUpdate(DocumentEvent pmE)
			{
				ustawTabele(tbPracownicy);
			}

			@Override
			public void removeUpdate(DocumentEvent pmE)
			{
				ustawTabele(tbPracownicy);
			}
		});
		mFiltrPracownika.setBounds(91, 468, 105, 27);
		contentPane.add(mFiltrPracownika);
		mFiltrPracownika.setColumns(10);

		JLabel lblFiltr = new JLabel("Filtr:");
		lblFiltr.setBounds(30, 458, 115, 46);
		contentPane.add(lblFiltr);
	}

	public JButton getBtnDodajPracownika()
	{
		return btnDodajPracownika;
	}

	public JButton getBtnPokazPracownika()
	{
		return btnPokazPracownika;
	}

	public JButton getBtnUsunPracownika()
	{
		return btnUsunPracownika;
	}

	public JTextField getFiltrPracownika()
	{
		return mFiltrPracownika;
	}

	public ZestawienieBean getPracownik()
	{
		return mPracownik;
	}

	public JTable getTbPracownicy()
	{
		return tbPracownicy;
	}

	public void setBtnDodajPracownika(JButton pmBtnDodajPracownika)
	{
		btnDodajPracownika = pmBtnDodajPracownika;
	}

	public void setBtnPokazPracownika(JButton pmBtnPokazPracownika)
	{
		btnPokazPracownika = pmBtnPokazPracownika;
	}

	public void setBtnUsunPracownika(JButton pmBtnUsunPracownika)
	{
		btnUsunPracownika = pmBtnUsunPracownika;
	}

	public void setFiltrPracownika(JTextField pmFiltrPracownika)
	{
		mFiltrPracownika = pmFiltrPracownika;
	}

	public void setPracownik(ZestawienieBean pmPracownik)
	{
		mPracownik = pmPracownik;
	}

	public void setTbPracownicy(JTable pmTbPracownicy)
	{
		tbPracownicy = pmTbPracownicy;
	}

	private JTable ustawTabele(JTable pmTabela)
	{
		try
		{
			String lvZapytanie = //
					"Select " + ZestawienieBean.getKolumnaID() + "," + ZestawienieBean.getKolumnaNazwaPracownika()//
							+ " from " + ZestawienieBean.getNazwaTabeli()//
							+ " where " + ZestawienieBean.getKolumnaNazwaPracownika() + " like \"%"
							+ mFiltrPracownika.getText() + "%\"" + //
							" AND Data_Zwolnienia is null";
			MyTableModel lvDTM = dbAccess.modelTabeliDB(lvZapytanie);
			pmTabela.setModel(lvDTM);
			lvDTM.fireTableDataChanged();
			TableColumnModel lvTcm = pmTabela.getColumnModel();
			for (int lvKolumna = pmTabela.getColumnCount() - 1; lvKolumna >= 0; lvKolumna--)
			{

				if (pmTabela.getColumnName(lvKolumna).contains("ID"))
				{
					TableColumn k = lvTcm.getColumn(lvKolumna);
					lvTcm.removeColumn(k);
				}
			}

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(pmTabela.getModel());
			pmTabela.setRowSorter(sorter);

			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
			sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			pmTabela.repaint();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pmTabela;
	}

	@Override
	public int getZaznaczenieTabeli()
	{
		return tbPracownicy.getSelectedRow();
	}

	@Override
	public ZestawienieBean getPracownikZTabeli()
	{
		ZestawienieBean lvPracownik = new ZestawienieBean();
		int lvRow = tbPracownicy.convertRowIndexToModel(getZaznaczenieTabeli());
		lvPracownik.setLvID((Integer) tbPracownicy.getModel().getValueAt(lvRow, 0));
		lvPracownik.setLvNazwa((String) tbPracownicy.getModel().getValueAt(lvRow, 1));

		return lvPracownik;
	}

	@Override
	public void odswiezTabele()
	{
		ustawTabele(tbPracownicy);
	}
}
