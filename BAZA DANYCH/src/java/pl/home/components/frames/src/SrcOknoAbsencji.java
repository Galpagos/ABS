package pl.home.components.frames.src;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.AbstractOkno;
import Frames.dbAccess.Components.DatePicker;
import Frames.dbAccess.Components.MyDataField;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.parameters.OAbsencjiWyjscie;

@SuppressWarnings("serial")
public abstract class SrcOknoAbsencji extends AbstractOkno<OAbsencjiWejscie, OAbsencjiWyjscie> {
	private JLabel lblAbsencjaPracownika;
	private JPanel contentPane;
	protected MyDataField mDataOd;
	protected MyDataField mDataDo;

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
			if (lvData != null)
				mDataOd.setValue(lvData);
		});

		btnPickDate2.addActionListener(lvE -> {
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData != null)
				mDataDo.setValue(lvData);
		});
	}

	@Override
	protected void beforeClose() {
		if (mAccepted)
			zapiszAbsencje();
	}

	public abstract void zapiszAbsencje();

	@Override
	protected void budujOkno() {

		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cbRodzajAbsencji = new JComboBox<SLRodzajeAbsencji>(SLRodzajeAbsencji.values());
		cbRodzajAbsencji.setBounds(169, 118, 214, 25);
		contentPane.add(cbRodzajAbsencji);

		cbProcent = new JComboBox<SLEkwiwalentZaUrlop>(SLEkwiwalentZaUrlop.values());
		cbProcent.setBounds(169, 158, 214, 25);
		contentPane.add(cbProcent);

		mcancelButton.setBounds(323, 237, 97, 25);
		contentPane.add(mcancelButton);

		mokButton.setText("Zapisz");
		mokButton.setBounds(206, 237, 97, 25);
		contentPane.add(mokButton);

		lblAbsencjaPracownika = new JLabel("Absencja pracownika ");
		lblAbsencjaPracownika.setBounds(13, 0, 298, 25);
		contentPane.add(lblAbsencjaPracownika);

		JLabel lblDataOd = new JLabel("Data od:");
		lblDataOd.setBounds(13, 38, 56, 16);
		contentPane.add(lblDataOd);

		JLabel lblDataDo = new JLabel("Data do:");
		lblDataDo.setBounds(13, 78, 56, 16);
		contentPane.add(lblDataDo);

		JLabel lblRodzajAbsencji = new JLabel("Rodzaj Absencji:");
		lblRodzajAbsencji.setBounds(13, 118, 128, 16);
		contentPane.add(lblRodzajAbsencji);

		JLabel lblProcent = new JLabel("Ekwiwalent za nieobecnoœæ:");
		lblProcent.setBounds(13, 158, 128, 16);
		contentPane.add(lblProcent);

		mDataOd = new MyDataField();
		mDataOd.setBounds(169, 38, 116, 22);
		contentPane.add(mDataOd);
		mDataOd.setColumns(10);

		mDataDo = new MyDataField();
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

	@Override
	protected OAbsencjiWyjscie budujWyjscie() {

		return OAbsencjiWyjscie.builder().build();
	}
}
