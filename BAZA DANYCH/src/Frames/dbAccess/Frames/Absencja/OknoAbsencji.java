package Frames.dbAccess.Frames.Absencja;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Absencja.ObslugaAbsencji;
import Absencja.WalidatorAbsenci;
import Datownik.JodaTime;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.DatePicker;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

@SuppressWarnings("serial")
public class OknoAbsencji extends JDialog {
	private JLabel lblAbsencjaPracownika;
	private JPanel contentPane;
	private JFormattedTextField mDataOd;
	private JFormattedTextField mDataDo;
	private OknoAbsencji mOknoAbsencji;
	private ObslugaAbsencji mObslugaAbsencji = new ObslugaAbsencji();
	private WalidatorAbsenci mWalidator = new WalidatorAbsenci();

	public OknoAbsencji(AbsencjaDTO pmABS) {
		mOknoAbsencji = this;
		setModalityType(ModalityType.APPLICATION_MODAL);
		ustawOkno(mOknoAbsencji, pmABS);
		setVisible(true);
	}

	public void ustawOkno(JDialog pmFrameDiagog, AbsencjaDTO pmAbsencja) {
		contentPane = new JPanel();
		pmFrameDiagog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pmFrameDiagog.setBounds(100, 100, 450, 300);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pmFrameDiagog.setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<SLRodzajeAbsencji> cbRodzajAbsencji = new JComboBox<SLRodzajeAbsencji>(SLRodzajeAbsencji.values());
		cbRodzajAbsencji.setSelectedItem(pmAbsencja.getRodzaj());
		cbRodzajAbsencji.setBounds(169, 115, 214, 25);
		contentPane.add(cbRodzajAbsencji);

		JButton btnWyjcie = new JButton("Wyj\u015Bcie");
		btnWyjcie.addActionListener(e -> pmFrameDiagog.dispose());
		btnWyjcie.setBounds(323, 187, 97, 25);
		contentPane.add(btnWyjcie);

		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(e -> {
			Date lvOd = (Date) mDataOd.getValue();
			Date lvDo = (Date) mDataDo.getValue();

			if (!mWalidator.czyPrawidloweDaty(lvOd, lvDo))
				return;
			new AbsencjaDTO();
			AbsencjaDTO lvNowaAbsencja = AbsencjaDTO.builder().setId(pmAbsencja.getId())
					.setIdPracownika(pmAbsencja.getIdPracownika());
			lvNowaAbsencja.setOkres(JodaTime.okresOdDo(lvOd, lvDo));
			lvNowaAbsencja.setRodzaj((SLRodzajeAbsencji) cbRodzajAbsencji.getSelectedItem());

			if (mWalidator.czyWystepujeAbsencjaWOkresie(lvNowaAbsencja))
				return;
			AbsencjaDTO lvUsuwana = mObslugaAbsencji.pobierzAbsencjePoId(pmAbsencja.getId());
			mObslugaAbsencji.usunAbsencje(pmAbsencja.getId(), true);
			if (mWalidator.czyPrzekraczaLimity(lvNowaAbsencja)) {
				mObslugaAbsencji.dodajAbsencje(lvUsuwana);
			} else {
				mWalidator.czyDniL4Ciagiem(lvNowaAbsencja);
				mObslugaAbsencji.dodajAbsencje(lvNowaAbsencja);
				pmAbsencja.setOkres(lvNowaAbsencja.getOkres());
				pmAbsencja.setRodzaj(lvNowaAbsencja.getRodzaj());
				pmFrameDiagog.dispose();
			}
		});
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

		mDataOd = new JFormattedTextField(pmAbsencja.getOkres().getStart().toDate());
		mDataOd.setBounds(169, 38, 116, 22);
		contentPane.add(mDataOd);
		mDataOd.setColumns(10);

		mDataDo = new JFormattedTextField(pmAbsencja.getOkres().getEnd().toDate());
		mDataDo.setBounds(169, 75, 116, 22);
		contentPane.add(mDataDo);
		mDataDo.setColumns(10);

		JButton btnPickDate1 = new JButton("Wybierz datê");
		btnPickDate1.setBounds(297, 36, 116, 20);
		contentPane.add(btnPickDate1);
		btnPickDate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Date lvData = new DatePicker().setPickedDate();
				if (lvData == null)
					return;
				mDataOd.setValue(lvData);
			}
		});

		JButton btnPickDate2 = new JButton("Wybierz datê");
		btnPickDate2.setBounds(297, 76, 116, 20);
		btnPickDate2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Date lvData = new DatePicker().setPickedDate();
				if (lvData == null)
					return;

				mDataDo.setValue(lvData);

			}
		});
		contentPane.add(btnPickDate2);
	}
}
