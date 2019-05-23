package dbAccess.Frames.OknoPracownika;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Enums.SLRodzajeAbsencji;
import dbAccess.Absencja;
import dbAccess.AbsencjaBean;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;
import dbAccess.dbAccess.MyTableModel;

@SuppressWarnings("serial")
public class OknoPracownika extends JDialog implements InterfejsOknaPracownika
{
	private ZestawienieBean mPracownik;
	private JLabel lblDataUrodzenia;
	private JLabel lblUrlopNalezny;
	private JPanel contentPane;
	private JTable tbAbsencje;
	private JScrollPane mScrollPane;
	private JButton mbutton;
	private JSpinner spnRok;
	private JButton btnModyfikuj;
	private JButton btnUsun;
	private JLabel lblGrupy;
	private ObslugaOknaPracownika mObsluga;

	private JLabel lblPracownik;

	public JLabel getLblGrupy()
	{
		return lblGrupy;
	}

	public void setLblGrupy(JLabel pmLblGrupy)
	{
		lblGrupy = pmLblGrupy;
	}

	public OknoPracownika(ZestawienieBean pmPracownik)
	{
		mObsluga = new ObslugaOknaPracownika(this);

		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setPracownik(pmPracownik);
		utworzOkno();
		ustawTabele(tbAbsencje, pmPracownik);
		lblPracownik.setText(lblPracownik.getText() + getPracownik().getLvNazwa());
		lblGrupy.setText(mObsluga.grupyPracownika());

		JButton btnUsunGrupe = new JButton("Usu\u0144 grup\u0119");
		btnUsunGrupe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mObsluga.usunGrupe();
			}
		});
		btnUsunGrupe.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUsunGrupe.setBounds(737, 56, 160, 22);
		contentPane.add(btnUsunGrupe);

		JButton btnUstawDatUrodzenia = new JButton("Ustaw dat\u0119 urodzenia");
		btnUstawDatUrodzenia.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mObsluga.ustawDateUrodzenia();
				odswiezLblDataUrodzenia();

			}
		});
		btnUstawDatUrodzenia.setBounds(315, 20, 223, 25);
		contentPane.add(btnUstawDatUrodzenia);

		JButton btnUstawUrlop = new JButton("Ustaw urlop nale¿ny");
		btnUstawUrlop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.ustawUrlopNalezny();
				odswiezLblUrlop();
			}
		});
		btnUstawUrlop.setBounds(315, 55, 223, 25);
		contentPane.add(btnUstawUrlop);

		lblDataUrodzenia = new JLabel("");
		lblDataUrodzenia.setBounds(557, 24, 168, 16);
		contentPane.add(lblDataUrodzenia);
		odswiezLblDataUrodzenia();

		lblUrlopNalezny = new JLabel("");
		lblUrlopNalezny.setBounds(550, 59, 175, 16);
		contentPane.add(lblUrlopNalezny);
		odswiezLblUrlop();
		setVisible(true);

	}

	private void utworzOkno()
	{
		contentPane = new JPanel();
		tbAbsencje = new JTable();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 927, 473);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		spnRok = new JSpinner();
		spnRok.setModel(new SpinnerNumberModel(2019, 2016, 2020, 1));
		spnRok.setBounds(118, 397, 97, 22);

		tbAbsencje.setAutoCreateRowSorter(true);
		contentPane.setLayout(null);

		mScrollPane = new JScrollPane(tbAbsencje);
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setBounds(12, 100, 749, 284);
		contentPane.add(mScrollPane);

		lblPracownik = new JLabel("Pracownik: ");
		lblPracownik.setBounds(12, 13, 334, 39);
		contentPane.add(lblPracownik);

		mbutton = new JButton("Wr\u00F3\u0107");
		mbutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
			}
		});
		mbutton.setBounds(785, 377, 97, 25);
		contentPane.add(mbutton);

		contentPane.add(spnRok);

		JLabel lblAbsencjeZaRok = new JLabel("Absencje za rok:");
		lblAbsencjeZaRok.setBounds(22, 400, 96, 16);
		contentPane.add(lblAbsencjeZaRok);

		JButton btnDodajAbsencje = new JButton("Dodaj Absencje");
		btnDodajAbsencje.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.DodajAbsencje();
			}
		});
		btnDodajAbsencje.setBounds(773, 104, 124, 25);
		contentPane.add(btnDodajAbsencje);

		btnModyfikuj = new JButton("Modyfikuj");
		btnModyfikuj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mObsluga.ModyfikujAbsencje();
			}
		});
		btnModyfikuj.setBounds(773, 142, 124, 25);
		contentPane.add(btnModyfikuj);

		btnUsun = new JButton("Usu\u0144");
		btnUsun.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mObsluga.UsunAbsencje();
			}
		});
		btnUsun.setBounds(773, 182, 124, 25);
		contentPane.add(btnUsun);

		spnRok.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{
				ustawTabele(tbAbsencje, getPracownik());
			}
		});
		lblGrupy = new JLabel("Nale\u017Cy do grup: ");
		lblGrupy.setBounds(12, 48, 334, 39);
		contentPane.add(lblGrupy);

		JButton btnDodajGrup = new JButton("Dodaj grup\u0119");
		btnDodajGrup.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				mObsluga.przypiszGrupe();
			}
		});
		btnDodajGrup.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDodajGrup.setBounds(737, 20, 160, 25);
		contentPane.add(btnDodajGrup);

	}

	public ZestawienieBean getPracownik()
	{
		return mPracownik;
	}

	public void odswiezTabele()
	{
		ustawTabele(tbAbsencje, mPracownik);
	}

	public void setPracownik(ZestawienieBean pmPracownik)
	{
		mPracownik = pmPracownik;
	}

	private JTable ustawTabele(JTable pmTabela, ZestawienieBean pmPracownik)
	{
		try
		{
			String lvZapytanie = "Select "//
					+ Absencja.kolumnaID + ", "//
					+ Absencja.kolumnaRodzajAbsencji + ", "//
					+ Absencja.kolumnaOdKiedy + ","//
					+ Absencja.kolumnaDoKiedy//
					+ " from " + AbsencjaBean.NazwaTabeli + " where "//
					+ AbsencjaBean.kolumnaIdPracownika + " = " + pmPracownik.getLvID()//
					+ " AND (Year([" + AbsencjaBean.kolumnaDoKiedy + "]) = " + spnRok.getValue()//
					+ " OR Year([" + AbsencjaBean.kolumnaOdKiedy + "]) = " + spnRok.getValue() + ")";
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
			pmTabela.repaint();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pmTabela;
	}

	@Override
	public ZestawienieBean getPracownika()
	{
		return mPracownik;
	}

	@Override
	public int getZaznaczenieTabeli()
	{
		return tbAbsencje.getSelectedRow();
	}

	@Override
	public Absencja getAbsencjeZTabeli()
	{
		Absencja lvAbsencja = new Absencja();
		int lvRow = tbAbsencje.convertRowIndexToModel(getZaznaczenieTabeli());
		lvAbsencja.setId((Integer) tbAbsencje.getModel().getValueAt(lvRow, 0));
		lvAbsencja.setDataOd((Timestamp) tbAbsencje.getModel().getValueAt(lvRow, 2));
		lvAbsencja.setDataDo((Timestamp) tbAbsencje.getModel().getValueAt(lvRow, 3));
		lvAbsencja.setIdPracownika(mPracownik.getLvID());
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji
				.AbsencjaPoNazwie((String) tbAbsencje.getModel().getValueAt(lvRow, 1));
		lvAbsencja.setRodzajAbsencji(lvRodzajAbs);
		return lvAbsencja;
	}

	public void odswiezLblDataUrodzenia()
	{
		lblDataUrodzenia.setText(mObsluga.getDataUrodzenia(mPracownik));
	}

	public void odswiezLblUrlop()
	{
		lblUrlopNalezny.setText(mObsluga.getUrlop(mPracownik));
	}
}
