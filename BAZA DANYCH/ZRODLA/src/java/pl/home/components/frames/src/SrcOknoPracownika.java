package pl.home.components.frames.src;

import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.AbstractOkno;

import java.awt.Font;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import pl.home.components.frames.parameters.OPracWejscie;
import pl.home.components.frames.parameters.OPracWyjscie;

@SuppressWarnings("serial")
public abstract class SrcOknoPracownika extends AbstractOkno<OPracWejscie, OPracWyjscie> {

	protected PracownikDTO mPracownik;

	public SrcOknoPracownika(OPracWejscie pmParams) {
		super(pmParams);
	}

	protected JLabel lblDataUrodzenia;
	protected JLabel lblUrlopNalezny;
	private JPanel contentPane;
	protected LTable tbAbsencje;
	private JScrollPane mScrollPane;
	protected JSpinner spnRok;
	protected JButton btnModyfikuj;
	protected JButton btnUsun;
	protected JLabel lblGrupy;

	protected JButton btnDodajAbsencje;

	protected JLabel lblPracownik;
	protected JButton btnUsunGrupe;
	protected JButton btnUstawDatUrodzenia;
	protected JButton btnUstawUrlop;
	protected JButton btnDodajGrup;

	@Override
	protected void budujOkno() {

		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		utworzOkno();
		lblGrupy.setText("");
		btnUsunGrupe = new JButton("Usuń grupę");
		btnUsunGrupe.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUsunGrupe.setBounds(737, 56, 160, 22);
		contentPane.add(btnUsunGrupe);
		btnUstawDatUrodzenia = new JButton("Ustaw datę urodzenia");

		btnUstawDatUrodzenia.setBounds(315, 20, 223, 25);
		contentPane.add(btnUstawDatUrodzenia);

		btnUstawUrlop = new JButton("Ustaw urlop należny");

		btnUstawUrlop.setBounds(315, 55, 223, 25);
		contentPane.add(btnUstawUrlop);

		lblDataUrodzenia = new JLabel("");
		lblDataUrodzenia.setBounds(557, 24, 168, 16);
		contentPane.add(lblDataUrodzenia);

		lblUrlopNalezny = new JLabel("");
		lblUrlopNalezny.setBounds(550, 59, 175, 16);
		contentPane.add(lblUrlopNalezny);

	}

	private void utworzOkno() {
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		spnRok = new JSpinner();
		spnRok.setModel(new SpinnerNumberModel(2020, 2016, 2025, 1));
		spnRok.setBounds(118, 397, 97, 22);

		contentPane.setLayout(null);

		lblPracownik = new JLabel("Pracownik: ");
		lblPracownik.setBounds(12, 13, 334, 39);
		contentPane.add(lblPracownik);

		mcancelButton.setBounds(785, 377, 97, 25);
		contentPane.add(mcancelButton);
		contentPane.add(mcancelButton);

		contentPane.add(spnRok);

		JLabel lblAbsencjeZaRok = new JLabel("Absencje za rok:");
		lblAbsencjeZaRok.setBounds(22, 400, 96, 16);
		contentPane.add(lblAbsencjeZaRok);

		btnDodajAbsencje = new JButton("Dodaj Absencje");
		btnDodajAbsencje.setBounds(773, 104, 124, 25);
		contentPane.add(btnDodajAbsencje);

		btnModyfikuj = new JButton("Modyfikuj");

		btnModyfikuj.setBounds(773, 142, 124, 25);
		contentPane.add(btnModyfikuj);

		btnUsun = new JButton("Usuń");

		btnUsun.setBounds(773, 182, 124, 25);
		contentPane.add(btnUsun);

		lblGrupy = new JLabel("Należy do grup: ");
		lblGrupy.setBounds(12, 48, 334, 39);
		contentPane.add(lblGrupy);

		btnDodajGrup = new JButton("Dodaj grupę");

		btnDodajGrup.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDodajGrup.setBounds(737, 20, 160, 25);
		contentPane.add(btnDodajGrup);
		tbAbsencje = utworzTabele();
		mScrollPane = new JScrollPane(tbAbsencje);
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setBounds(12, 100, 749, 284);
		contentPane.add(mScrollPane);
	}

	@Override
	protected void odswiezKontrolki() {

	}

	@Override
	protected void przypiszMetody() {

		btnDodajAbsencje.addActionListener(lvE -> dodajAbsencje());
		btnUsunGrupe.addActionListener(e -> usunGrupe());
		btnUstawDatUrodzenia.addActionListener(lvE -> ustawDateUrodzenia());
		btnUstawUrlop.addActionListener(lvE -> ustawUrlopNalezny());
		btnModyfikuj.addActionListener(lvE -> modyfikujAbsencje());
		btnUsun.addActionListener(lvE -> usunAbsencje());
		btnDodajGrup.addActionListener(lvE -> przypiszGrupe());
		spnRok.addChangeListener(lvE -> ustawTabele());
	}

	protected abstract void ustawTabele();

	protected abstract void przypiszGrupe();

	protected abstract void usunAbsencje();

	protected abstract void modyfikujAbsencje();

	protected abstract void ustawUrlopNalezny();

	protected abstract void ustawDateUrodzenia();

	protected abstract void usunGrupe();

	protected abstract void dodajAbsencje();

	@Override
	protected void onOpen() {

	}

	private LTable utworzTabele() {

		LTable lvTabela = new LTable(getZapytanieDoTabeli());
		lvTabela.hideColumn(AbsencjeColumns.ID_tabeli);
		lvTabela.odswiez();

		return lvTabela;

	}

	protected LRecordSet getZapytanieDoTabeli() {
		return QueryBuilder.SELECT()//
				.select(AbsencjeColumns.ID_tabeli, AbsencjeColumns.RODZAJ, AbsencjeColumns.Od_kiedy, AbsencjeColumns.Do_kiedy, AbsencjeColumns.EKWIWALENT)//
				.andWarunek(AbsencjeColumns.ID_pracownika, mPracownik.getId())//
				.andAfterOrEqual(AbsencjeColumns.Od_kiedy, LocalDate.of((int) spnRok.getValue(), 1, 1))//
				.andBeforeOrEqual(AbsencjeColumns.Do_kiedy, YearMonth.of((int) spnRok.getValue(), 12).atEndOfMonth())//
				.execute();

	}

	@Override
	protected void readParams() {

	}

	@Override
	protected OPracWyjscie budujWyjscie() {

		return OPracWyjscie.builder().build();
	}

}
