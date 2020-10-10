package pl.home.components.frames.src;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Data_Zwolnienia;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_PRAC;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;

import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.DbSelect;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.AbstractOkno;
import ProjektGlowny.commons.Frames.InterfejsAbstractOkno;
import ProjektGlowny.commons.Frames.ParametryWejscia;
import ProjektGlowny.commons.Frames.ParametryWyjscia;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;

@SuppressWarnings("serial")
public abstract class SrcOknoGlowne extends AbstractOkno<ParametryWejscia, ParametryWyjscia> implements InterfejsAbstractOkno {
	public SrcOknoGlowne(ParametryWejscia pmParams) {
		super(pmParams);
	}
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
	protected JButton btnDodajMasowaAbsencje;
	protected JButton btnOneDayView;

	@Override
	protected void budujOkno() {
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

		btnUsunPracownika = new JButton("Usuń Pracownika");
		btnUsunPracownika.setBounds(46, 144, 191, 39);
		mButtonPanel.add(btnUsunPracownika);

		mcancelButton.setBounds(402, 444, 97, 25);
		mButtonPanel.add(mcancelButton);

		btnPokazNieobecnych = new JButton("Pokaż nieobecnych");
		btnPokazNieobecnych.setBounds(46, 250, 191, 39);
		mButtonPanel.add(btnPokazNieobecnych);

		btnDodajMasowaAbsencje = new JButton("Dodaj Absencję");
		btnDodajMasowaAbsencje.setBounds(46, 302, 191, 39);
		mButtonPanel.add(btnDodajMasowaAbsencje);

		btnDodajDzienWolny = new JButton("Dodaj Dzień Wolny");
		btnDodajDzienWolny.setBounds(282, 40, 179, 39);
		mButtonPanel.add(btnDodajDzienWolny);

		btnPokazDniWolne = new JButton("Pokaż Dni Wolne");
		btnPokazDniWolne.setBounds(282, 92, 179, 39);
		mButtonPanel.add(btnPokazDniWolne);

		btnSprawozdanie = new JButton("Sprawozdanie");
		btnSprawozdanie.setBounds(282, 151, 179, 39);
		mButtonPanel.add(btnSprawozdanie);

		btnOneDayView = new JButton("Weryfikuj obecności");
		btnOneDayView.setBounds(282, 210, 179, 39);
		mButtonPanel.add(btnOneDayView);

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

		cbCzyUsunieci = new JCheckBox("<HTML>Pokaż </b>zwolnionych</HTML>");
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
				.select(ID_PRAC, Pracownik)//
				.andWarunek(Pracownik + " like \"%" + mFiltrPracownika.getText() + "%\"");

		if (!cbCzyUsunieci.isSelected())
			lvZapytanieLS = lvZapytanieLS.andWarunek(Data_Zwolnienia, null);

		LTable lvTabela = new LTable(lvZapytanieLS.execute());
		lvTabela.hideColumn(ZestawienieColumns.ID_PRAC);
		lvTabela.addSorter(Pracownik, SortOrder.ASCENDING);
		lvTabela.odswiez();
		lvTabela.repaint();

		return lvTabela;
	}
}
