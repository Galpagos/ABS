package Frames.dbAccess.Frames.Absencja;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.AbstractOkno;
import Frames.dbAccess.Components.DatePicker;
import Frames.dbAccess.Components.ScriptParams;

@SuppressWarnings("serial")
public abstract class SrcOknoAbsencji extends AbstractOkno {
	private JLabel lblAbsencjaPracownika;
	private JPanel contentPane;
	protected JFormattedTextField mDataOd;
	protected JFormattedTextField mDataDo;

	protected JButton btnWyjcie;
	protected JButton btnZapisz;
	protected JButton btnPickDate1;
	protected JButton btnPickDate2;
	protected JComboBox<SLRodzajeAbsencji> cbRodzajAbsencji;

	public SrcOknoAbsencji(ScriptParams pmParams) {
		super(pmParams);
	}

	@Override
	protected void przypiszMetody() {
		btnZapisz.addActionListener(e -> zapiszAbsencje());
		btnPickDate1.addActionListener(lvE -> {
			Date lvData = new DatePicker().setPickedDate();
			if (lvData != null)
				mDataOd.setValue(lvData);
		});

		btnPickDate2.addActionListener(lvE -> {
			Date lvData = new DatePicker().setPickedDate();
			if (lvData != null)
				mDataDo.setValue(lvData);
		});

	}

	protected void zapiszAbsencje() {

	}

	@Override
	protected void budujOkno() {

		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cbRodzajAbsencji = new JComboBox<SLRodzajeAbsencji>(SLRodzajeAbsencji.values());
		cbRodzajAbsencji.setBounds(169, 115, 214, 25);
		contentPane.add(cbRodzajAbsencji);

		btnWyjcie = new JButton("Wyj\u015Bcie");
		btnWyjcie.addActionListener(e -> dispose());
		btnWyjcie.setBounds(323, 187, 97, 25);
		contentPane.add(btnWyjcie);

		btnZapisz = new JButton("Zapisz");
		btnZapisz.setBounds(206, 187, 97, 25);
		contentPane.add(btnZapisz);

		lblAbsencjaPracownika = new JLabel("Absencja pracownika ");
		lblAbsencjaPracownika.setBounds(13, 0, 298, 25);
		contentPane.add(lblAbsencjaPracownika);

		JLabel lblDataOd = new JLabel("Data od:");
		lblDataOd.setBounds(23, 38, 56, 16);
		contentPane.add(lblDataOd);

		JLabel lblDataDo = new JLabel("Data do:");
		lblDataDo.setBounds(23, 78, 56, 16);
		contentPane.add(lblDataDo);

		JLabel lblRodzajAbsencji = new JLabel("Rodzaj Absencji:");
		lblRodzajAbsencji.setBounds(13, 118, 128, 16);
		contentPane.add(lblRodzajAbsencji);

		mDataOd = new JFormattedTextField(new Date());
		mDataOd.setBounds(169, 38, 116, 22);
		contentPane.add(mDataOd);
		mDataOd.setColumns(10);

		mDataDo = new JFormattedTextField(new Date());
		mDataDo.setBounds(169, 75, 116, 22);
		contentPane.add(mDataDo);
		mDataDo.setColumns(10);

		btnPickDate1 = new JButton("Wybierz datê");
		btnPickDate1.setBounds(297, 36, 116, 20);
		contentPane.add(btnPickDate1);

		btnPickDate2 = new JButton("Wybierz datê");
		btnPickDate2.setBounds(297, 76, 116, 20);

		contentPane.add(btnPickDate2);
	}

	@Override
	protected void readParams() {
	}

	@Override
	protected void onOpen() {
	}

	@Override
	protected void odswiezKontrolki() {
	}
}
