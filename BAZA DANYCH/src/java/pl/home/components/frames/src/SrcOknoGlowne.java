package pl.home.components.frames.src;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Data_Zwolnienia;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;

import Frames.dbAccess.Components.LTable;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.DbSelect;
import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;

@SuppressWarnings("serial")
public abstract class SrcOknoGlowne extends JFrame {

	protected LTable tbPracownicy;
	protected JButton btnDodajPracownika;
	protected JButton btnPokazPracownika;
	protected JButton btnUsunPracownika;
	protected JButton btnZatrudnij;
	protected JCheckBox cbCzyUsunieci;
	JScrollPane mScrollPane;
	PracownikDTO mPracownik = null;
	protected JTextField mFiltrPracownika;
	JPanel mContentPane;
	JPanel mButtonPanel;
	protected JButton btnWyjcie;
	protected JButton btnPokazNieobecnych;
	protected JButton btnDodajDzienWolny;
	protected JButton btnPokazDniWolne;
	protected JButton btnSprawozdanie;
	protected JButton btnZwolnij;

	public SrcOknoGlowne() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 560);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mContentPane);

		mContentPane.setLayout(null);

		mButtonPanel = new JPanel();
		mButtonPanel.setBounds(255, 5, 547, 503);
		mContentPane.add(mButtonPanel);
		mButtonPanel.setLayout(null);

		btnPokazPracownika = new JButton("Pokaz Pracownika");
		btnPokazPracownika.setBounds(46, 92, 191, 39);
		mButtonPanel.add(btnPokazPracownika);

		btnDodajPracownika = new JButton("Dodaj Pracownika");
		btnDodajPracownika.setBounds(46, 40, 191, 39);
		mButtonPanel.add(btnDodajPracownika);

		btnUsunPracownika = new JButton("Usu\u0144 Pracownika");
		btnUsunPracownika.setBounds(46, 144, 191, 39);
		mButtonPanel.add(btnUsunPracownika);

		btnWyjcie = new JButton("Wyj\u015Bcie");
		btnWyjcie.setBounds(402, 444, 97, 25);
		mButtonPanel.add(btnWyjcie);

		btnPokazNieobecnych = new JButton("Poka\u017C nieobecnych");
		btnPokazNieobecnych.setBounds(46, 250, 191, 39);
		mButtonPanel.add(btnPokazNieobecnych);

		btnDodajDzienWolny = new JButton("Dodaj Dzie\u0144 Wolny");
		btnDodajDzienWolny.setBounds(282, 40, 179, 39);
		mButtonPanel.add(btnDodajDzienWolny);

		btnPokazDniWolne = new JButton("Poka\u017C Dni Wolne");
		btnPokazDniWolne.setBounds(282, 92, 179, 39);
		mButtonPanel.add(btnPokazDniWolne);

		btnSprawozdanie = new JButton("Sprawozdanie");
		btnSprawozdanie.setBounds(282, 151, 140, 25);
		mButtonPanel.add(btnSprawozdanie);

		btnZwolnij = new JButton("Zwolnij Pracownika");
		btnZwolnij.setBounds(46, 198, 191, 39);
		mButtonPanel.add(btnZwolnij);

		btnZatrudnij = new JButton("Zatrudnij Pracownika");
		btnZatrudnij.setBounds(46, 198, 191, 39);
		mButtonPanel.add(btnZatrudnij);

		mFiltrPracownika = new JTextField("");
		mFiltrPracownika.setBounds(71, 468, 105, 27);
		mContentPane.add(mFiltrPracownika);
		mFiltrPracownika.setColumns(10);

		cbCzyUsunieci = new JCheckBox("<HTML>Poka¿ </b>zwolnionych</HTML>");
		cbCzyUsunieci.setBounds(180, 468, 105, 27);
		mContentPane.add(cbCzyUsunieci);

		JLabel lblFiltr = new JLabel("Filtr:");
		lblFiltr.setBounds(30, 458, 115, 46);
		mContentPane.add(lblFiltr);
		tbPracownicy = utworzTabele();
		mScrollPane = new JScrollPane(tbPracownicy);

		mScrollPane.setAutoscrolls(true);
		mScrollPane.setPreferredSize(new Dimension(250, 202));
		mScrollPane.setMaximumSize(new Dimension(300, 267));
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mScrollPane.setBounds(5, 5, 250, 438);

		mContentPane.add(mScrollPane);
	}

	private LTable utworzTabele() {

		DbSelect lvZapytanieLS = QueryBuilder//
				.SELECT()//
				.select(ID_tabeli, Pracownik)//
				.andWarunek(Pracownik + " like \"%" + mFiltrPracownika.getText() + "%\"");

		if (!cbCzyUsunieci.isSelected())
			lvZapytanieLS = lvZapytanieLS.andWarunek(Data_Zwolnienia, null);

		LTable lvTabela = new LTable(lvZapytanieLS.execute());
		lvTabela.hideColumn(ZestawienieColumns.ID_tabeli);
		lvTabela.addSorter(Pracownik, SortOrder.ASCENDING);
		lvTabela.odswiez();
		lvTabela.repaint();

		return lvTabela;
	}
}
