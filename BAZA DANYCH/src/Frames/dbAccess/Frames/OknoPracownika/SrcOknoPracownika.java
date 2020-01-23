package Frames.dbAccess.Frames.OknoPracownika;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import Frames.dbAccess.Components.AbstractOkno;
import Frames.dbAccess.Components.ScriptParams;

@SuppressWarnings("serial")
public class SrcOknoPracownika extends AbstractOkno {

	public SrcOknoPracownika(ScriptParams pmParams) {
		super(pmParams);
	}

	protected JLabel lblDataUrodzenia;
	protected JLabel lblUrlopNalezny;
	private JPanel contentPane;
	protected JTable tbAbsencje;
	private JScrollPane mScrollPane;
	protected JButton mbtnWyjscie;
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
		btnUsunGrupe = new JButton("Usu\u0144 grup\u0119");
		btnUsunGrupe.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUsunGrupe.setBounds(737, 56, 160, 22);
		contentPane.add(btnUsunGrupe);
		btnUstawDatUrodzenia = new JButton("Ustaw dat\u0119 urodzenia");

		btnUstawDatUrodzenia.setBounds(315, 20, 223, 25);
		contentPane.add(btnUstawDatUrodzenia);

		btnUstawUrlop = new JButton("Ustaw urlop nale¿ny");

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
		tbAbsencje = new JTable();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 927, 473);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		spnRok = new JSpinner();
		spnRok.setModel(new SpinnerNumberModel(2020, 2016, 2025, 1));
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

		mbtnWyjscie = new JButton("Wr\u00F3\u0107");

		mbtnWyjscie.setBounds(785, 377, 97, 25);
		contentPane.add(mbtnWyjscie);

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

		btnUsun = new JButton("Usu\u0144");

		btnUsun.setBounds(773, 182, 124, 25);
		contentPane.add(btnUsun);

		lblGrupy = new JLabel("Nale\u017Cy do grup: ");
		lblGrupy.setBounds(12, 48, 334, 39);
		contentPane.add(lblGrupy);

		btnDodajGrup = new JButton("Dodaj grup\u0119");

		btnDodajGrup.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDodajGrup.setBounds(737, 20, 160, 25);
		contentPane.add(btnDodajGrup);

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
		mbtnWyjscie.addActionListener(lvE -> dispose());
		btnModyfikuj.addActionListener(lvE -> modyfikujAbsencje());
		btnUsun.addActionListener(lvE -> usunAbsencje());
		btnDodajGrup.addActionListener(lvE -> przypiszGrupe());
		spnRok.addChangeListener(lvE -> ustawTabele());
	}

	protected void ustawTabele() {

	}

	protected void przypiszGrupe() {

	}

	protected void usunAbsencje() {

	}

	protected void modyfikujAbsencje() {

	}

	protected void ustawUrlopNalezny() {

	}

	protected void ustawDateUrodzenia() {

	}

	protected void usunGrupe() {

	}

	protected void dodajAbsencje() {

	}

	@Override
	protected void onOpen() {

	}

	@Override
	protected void readParams() {

	}
}
