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
	protected JLabel lblDataUrodzenia1;
	protected JLabel lblUrlopNalezny1;
	protected JLabel lblEtat;
	protected JLabel lblEtat1;

	private JPanel contentPane;
	protected LTable tbAbsencje;
	private JScrollPane mScrollPane;
	protected JSpinner spnRok;
	protected JButton btnModyfikuj;
	protected JButton btnUsun;
	protected JLabel lblGrupy;
	protected JButton btnUstawEtat;
	protected JButton btnDodajAbsencje;

	protected JLabel lblPracownik;
	protected JButton btnUsunGrupe;
	protected JButton btnDodajGrup;

	@Override
	protected void budujOkno() {

		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		utworzOkno();
		btnUsunGrupe = new JButton("Usuń grupę");
		btnUsunGrupe.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUsunGrupe.setBounds(737, 56, 160, 22);
		contentPane.add(btnUsunGrupe);

		lblPracownik = new JLabel("Pracownik: ");
		lblPracownik.setBounds(10, 10, 300, 25);
		contentPane.add(lblPracownik);

		lblDataUrodzenia1 = new JLabel("Data ur.:");
		lblDataUrodzenia1.setBounds(10, 30, 60, 25);
		contentPane.add(lblDataUrodzenia1);

		lblDataUrodzenia = new JLabel("");
		lblDataUrodzenia.setBounds(70, 30, 100, 25);
		contentPane.add(lblDataUrodzenia);

		lblGrupy = new JLabel("Należy do grup: ");
		lblGrupy.setBounds(10, 50, 500, 25);
		contentPane.add(lblGrupy);

		lblUrlopNalezny1 = new JLabel("Urlop należny:");
		lblUrlopNalezny1.setBounds(300, 30, 150, 25);
		contentPane.add(lblUrlopNalezny1);

		lblUrlopNalezny = new JLabel("");
		lblUrlopNalezny.setBounds(400, 30, 50, 25);
		contentPane.add(lblUrlopNalezny);

		lblEtat1 = new JLabel("Etat:");
		lblEtat1.setBounds(300, 10, 50, 25);
		contentPane.add(lblEtat1);

		lblEtat = new JLabel("połowa etatu");
		lblEtat.setBounds(340, 10, 100, 25);
		contentPane.add(lblEtat);

	}

	private void utworzOkno() {
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		spnRok = new JSpinner();
		int lvRok = LocalDate.now().getYear();
		spnRok.setModel(new SpinnerNumberModel(lvRok, 2016, lvRok + 1, 1));
		spnRok.setBounds(118, 397, 97, 22);

		contentPane.setLayout(null);

		mcancelButton.setBounds(785, 377, 97, 25);
		contentPane.add(mcancelButton);
		contentPane.add(mcancelButton);

		contentPane.add(spnRok);

		JLabel lblAbsencjeZaRok = new JLabel("Absencje za rok:");
		lblAbsencjeZaRok.setBounds(22, 400, 96, 16);
		contentPane.add(lblAbsencjeZaRok);

		btnDodajAbsencje = new JButton("Dodaj Absencje");
		btnDodajAbsencje.setBounds(680, 104, 224, 25);
		contentPane.add(btnDodajAbsencje);

		btnModyfikuj = new JButton("Modyfikuj");
		btnModyfikuj.setBounds(680, 144, 224, 25);
		contentPane.add(btnModyfikuj);

		btnUsun = new JButton("Usuń");
		btnUsun.setBounds(680, 184, 224, 25);
		contentPane.add(btnUsun);

		btnUstawEtat = new JButton("Modyfikuj dane");
		btnUstawEtat.setBounds(680, 224, 224, 25);
		contentPane.add(btnUstawEtat);

		btnDodajGrup = new JButton("Dodaj grupę");

		btnDodajGrup.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDodajGrup.setBounds(737, 20, 160, 25);
		contentPane.add(btnDodajGrup);
		tbAbsencje = utworzTabele();
		mScrollPane = new JScrollPane(tbAbsencje);
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setBounds(12, 100, 650, 284);
		contentPane.add(mScrollPane);
	}

	@Override
	protected void odswiezKontrolki() {

	}

	@Override
	protected void przypiszMetody() {

		btnDodajAbsencje.addActionListener(lvE -> dodajAbsencje());
		btnUsunGrupe.addActionListener(e -> usunGrupe());
		btnUstawEtat.addActionListener(lvE -> ustawDane());
		btnModyfikuj.addActionListener(lvE -> modyfikujAbsencje());
		btnUsun.addActionListener(lvE -> usunAbsencje());
		btnDodajGrup.addActionListener(lvE -> przypiszGrupe());
		spnRok.addChangeListener(lvE -> ustawTabele());
	}

	protected abstract void ustawDane();

	protected abstract void ustawTabele();

	protected abstract void przypiszGrupe();

	protected abstract void usunAbsencje();

	protected abstract void modyfikujAbsencje();

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
