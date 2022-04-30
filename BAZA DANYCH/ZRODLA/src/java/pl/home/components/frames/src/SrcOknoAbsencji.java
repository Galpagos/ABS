package pl.home.components.frames.src;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Frames.AbstractOkno;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Frames.dbAccess.Components.MyDataField;
import enums.SLRodzajeAbsencji;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.parameters.OAbsencjiWyjscie;

@SuppressWarnings("serial")
public abstract class SrcOknoAbsencji extends AbstractOkno<OAbsencjiWejscie, OAbsencjiWyjscie> {
	private JPanel contentPane;
	protected MyDataField mDataOd;
	protected MyDataField mDataDo;
	protected JLabel lblDataOd;
	protected JLabel lblDataDo;
	protected JButton btnPickDate1;
	protected JButton btnPickDate2;
	protected JComboBox<SLRodzajeAbsencji> cbRodzajAbsencji;
	protected JComboBox<SLEkwiwalentZaUrlop> cbProcent;

	public SrcOknoAbsencji(OAbsencjiWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void przypiszMetody() {
		btnPickDate1.addActionListener(lvE -> {
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData != null) {
				mDataOd.setValue(lvData);
				if (mDataDo.getDateValue().isBefore(lvData))
					mDataDo.setValue(lvData);
			}
		});

		btnPickDate2.addActionListener(lvE -> {
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData != null) {
				mDataDo.setValue(lvData);
				if (mDataOd.getDateValue().isAfter(lvData))
					mDataOd.setValue(lvData);
			}
		});
	}

	@Override
	protected void beforeClose() {
		if (mAccepted)
			zapiszAbsencje();
		super.beforeClose();
	}

	public abstract void zapiszAbsencje();

	@Override
	protected void budujOkno() {

		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 400);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cbRodzajAbsencji = new JComboBox<SLRodzajeAbsencji>(SLRodzajeAbsencji.values());
		cbRodzajAbsencji.setBounds(209, 118, 214, 25);
		contentPane.add(cbRodzajAbsencji);

		cbProcent = new JComboBox<SLEkwiwalentZaUrlop>(SLEkwiwalentZaUrlop.values());
		cbProcent.setBounds(209, 158, 214, 25);
		contentPane.add(cbProcent);

		mcancelButton.setBounds(323, 237, 97, 25);
		contentPane.add(mcancelButton);

		mokButton.setText("Zapisz");
		mokButton.setBounds(206, 237, 97, 25);
		contentPane.add(mokButton);

		setTitle("Absencja pracownika ");

		lblDataOd = new JLabel("Data od:");
		lblDataOd.setBounds(13, 38, 168, 16);
		contentPane.add(lblDataOd);

		lblDataDo = new JLabel("Data do:");
		lblDataDo.setBounds(13, 78, 168, 16);
		contentPane.add(lblDataDo);

		JLabel lblRodzajAbsencji = new JLabel("Rodzaj Absencji:");
		lblRodzajAbsencji.setBounds(13, 118, 168, 16);
		contentPane.add(lblRodzajAbsencji);

		JLabel lblProcent = new JLabel("Ekwiwalent za nieobecność:");
		lblProcent.setBounds(13, 158, 168, 16);
		contentPane.add(lblProcent);

		mDataOd = new MyDataField();
		mDataOd.setBounds(209, 38, 116, 22);
		contentPane.add(mDataOd);
		mDataOd.setColumns(10);

		mDataDo = new MyDataField();
		mDataDo.setBounds(209, 75, 116, 22);
		contentPane.add(mDataDo);
		mDataDo.setColumns(10);

		btnPickDate1 = new JButton("Wybierz datę");
		btnPickDate1.setBounds(340, 36, 116, 20);
		contentPane.add(btnPickDate1);

		btnPickDate2 = new JButton("Wybierz datę");
		btnPickDate2.setBounds(340, 76, 116, 20);

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

	@Override
	protected OAbsencjiWyjscie budujWyjscie() {

		return OAbsencjiWyjscie.builder().build();
	}
}
