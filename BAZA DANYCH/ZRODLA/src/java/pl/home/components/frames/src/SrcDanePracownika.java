package pl.home.components.frames.src;

import static Utils.GridUtils.gridCons;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Frames.AbstractOkno;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Frames.dbAccess.Components.MyDataField;
import Frames.dbAccess.Components.MyIntField;
import enums.EtatPracownika;
import pl.home.components.frames.parameters.ODanePracownikaWejscie;
import pl.home.components.frames.parameters.ODanePracownikaWyjscie;

public abstract class SrcDanePracownika extends AbstractOkno<ODanePracownikaWejscie, ODanePracownikaWyjscie> {

	private static final long serialVersionUID = 4137119733821838969L;

	private JPanel mContentPane;
	protected MyDataField mDataUrodzenia;
	protected JLabel lblDataUrodzenia;
	protected JButton btnPickDate1;
	protected JLabel lblNazwa;
	protected JTextField txtNazwa;
	protected JLabel lblEtat;
	protected JLabel lblUrlop;

	private JLabel lblGodzinaOd;
	private JLabel lblGodzinaDo;

	protected MyIntField txtGodzinaOd;
	protected MyIntField txtGodzinaDo;

	protected JComboBox<EtatPracownika> txtEtat;
	protected MyIntField txtUlrop;

	public SrcDanePracownika(ODanePracownikaWejscie pmParams) {
		super(pmParams);

	}

	@Override
	protected void onOpen() {
	}

	@Override
	protected void przypiszMetody() {
		btnPickDate1.addActionListener(lvE -> {
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData != null) {
				mDataUrodzenia.setValue(lvData);
			}
		});
	}

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void budujOkno() {
		initOkno();
		initBottomPanel();
		initCenter();

	}

	private void initOkno() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Dane pracownika " + mParamsIn.getPracownik().getNazwa());
		mContentPane = new JPanel(new BorderLayout());
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mContentPane);
	}

	private void initBottomPanel() {
		JPanel mBottomPanel = new JPanel(new GridLayout(1, 6, 10, 10));
		mBottomPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mokButton.setText("Zapisz");
		mcancelButton.setText("Anuluj");
		mBottomPanel.add(mokButton);
		mBottomPanel.add(mcancelButton);

		mContentPane.add(mBottomPanel, BorderLayout.PAGE_END);
	}

	private void initCenter() {
		JPanel lvPanel = new JPanel(new GridBagLayout());
		lvPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		lblNazwa = new JLabel("Nazwisko i imię");
		txtNazwa = new JTextField(mParamsIn.getPracownik().getNazwa());
		txtNazwa.setPreferredSize(new Dimension(200, 20));
		txtNazwa.setSize(new Dimension(200, 20));
		txtNazwa.setMaximumSize(new Dimension(200, 20));
		txtNazwa.setMinimumSize(new Dimension(200, 20));
		lblDataUrodzenia = new JLabel("Data urodzenia");
		mDataUrodzenia = new MyDataField();
		btnPickDate1 = new JButton("Wybierz datę");
		lvPanel.add(lblNazwa, gridCons(0, 0));
		lvPanel.add(txtNazwa, gridCons(1, 0));
		lvPanel.add(lblDataUrodzenia, gridCons(0, 1));
		lvPanel.add(mDataUrodzenia, gridCons(1, 1));
		lvPanel.add(btnPickDate1, gridCons(3, 1));

		lblEtat = new JLabel("Etat");
		txtEtat = new JComboBox<>(EtatPracownika.values());

		lvPanel.add(lblEtat, gridCons(0, 2));
		lvPanel.add(txtEtat, gridCons(1, 2));

		lblUrlop = new JLabel("Urlop należny");
		txtUlrop = new MyIntField();

		lvPanel.add(lblUrlop, gridCons(0, 3));
		lvPanel.add(txtUlrop, gridCons(1, 3));

		lblGodzinaOd = new JLabel("Godzina pracy od:");
		txtGodzinaOd = new MyIntField();

		lvPanel.add(lblGodzinaOd, gridCons(0, 4));
		lvPanel.add(txtGodzinaOd, gridCons(1, 4));

		lblGodzinaDo = new JLabel("Godzina pracy do:");
		txtGodzinaDo = new MyIntField();

		lvPanel.add(lblGodzinaDo, gridCons(0, 5));
		lvPanel.add(txtGodzinaDo, gridCons(1, 5));

		mContentPane.add(lvPanel, BorderLayout.CENTER);
	}

	@Override
	protected ODanePracownikaWyjscie budujWyjscie() {
		return new ODanePracownikaWyjscie();
	}
}
