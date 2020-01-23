package Frames.dbAccess.Frames.OknoGlowne;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

@SuppressWarnings("serial")
public class SrcOknoGlowne extends JFrame {

	JTable tbPracownicy;
	JButton btnDodajPracownika;
	JButton btnPokazPracownika;
	JButton btnUsunPracownika;
	JScrollPane mScrollPane;
	PracownikDTO mPracownik = null;
	JTextField mFiltrPracownika;
	JPanel mContentPane;
	JPanel mButtonPanel;
	JButton btnWyjcie;
	JButton btnPokazNieobecnych;
	JButton btnDodajDzienWolny;
	JButton btnPokazDniWolne;
	JButton btnSprawozdanie;
	JButton btnZwolnij;

	public SrcOknoGlowne() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 560);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mContentPane);

		tbPracownicy = new JTable();
		tbPracownicy.setAutoCreateRowSorter(true);
		mScrollPane = new JScrollPane(tbPracownicy);
		mScrollPane.setAutoscrolls(true);
		mScrollPane.setPreferredSize(new Dimension(250, 202));
		mScrollPane.setMaximumSize(new Dimension(300, 267));
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mScrollPane.setBounds(5, 5, 250, 438);

		DefaultTableCellRenderer lvRightRenderer = new DefaultTableCellRenderer();
		lvRightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		mContentPane.setLayout(null);

		mContentPane.add(mScrollPane);

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

		mFiltrPracownika = new JTextField("");
		mFiltrPracownika.setBounds(91, 468, 105, 27);
		mContentPane.add(mFiltrPracownika);
		mFiltrPracownika.setColumns(10);

		JLabel lblFiltr = new JLabel("Filtr:");
		lblFiltr.setBounds(30, 458, 115, 46);
		mContentPane.add(lblFiltr);
	}
}
