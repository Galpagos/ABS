package dbAccess.Frames.Absencja;

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

import Enums.Komunikat;
import Enums.SLRodzajeAbsencji;
import Parsery.ParseryDB;
import dbAccess.Absencja;
import dbAccess.AbsencjaBean;
import dbAccess.ZestawienieBean;
import dbAccess.dbAccess;
import dbAccess.Components.DatePicker;

@SuppressWarnings("serial")
public class OknoAbsencji extends JDialog
{
	private JLabel lblAbsencjaPracownika;
	private JPanel contentPane;
	private JFormattedTextField mDataOd;
	private JFormattedTextField mDataDo;
	private OknoAbsencji mOknoAbsencji;

	public OknoAbsencji(Absencja pmAbsencja, ZestawienieBean pmPracownik)
	{
		mOknoAbsencji = this;
		setModalityType(ModalityType.APPLICATION_MODAL);
		ustawOkno(mOknoAbsencji, pmAbsencja, pmPracownik);
		setVisible(true);
	}

	public void ustawOkno(JDialog pmFrameDiagog, Absencja pmAbsencja, ZestawienieBean pmPracownik)
	{
		contentPane = new JPanel();
		pmFrameDiagog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pmFrameDiagog.setBounds(100, 100, 450, 300);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pmFrameDiagog.setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<SLRodzajeAbsencji> cbRodzajAbsencji = new JComboBox<SLRodzajeAbsencji>(SLRodzajeAbsencji.values());
		cbRodzajAbsencji.setSelectedItem(pmAbsencja.getRodzajAbsencji());
		cbRodzajAbsencji.setBounds(169, 115, 214, 25);
		contentPane.add(cbRodzajAbsencji);

		JButton btnWyjcie = new JButton("Wyj\u015Bcie");
		btnWyjcie.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pmFrameDiagog.dispose();
			}
		});
		btnWyjcie.setBounds(323, 187, 97, 25);
		contentPane.add(btnWyjcie);

		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pmAbsencja.setDataDo((Date) mDataDo.getValue());
				pmAbsencja.setDataOd((Date) mDataOd.getValue());
				pmAbsencja.setRodzajAbsencji((SLRodzajeAbsencji) cbRodzajAbsencji.getSelectedItem());

				String lvDelete = //
						"Delete from " + Absencja.NazwaTabeli//
								+ " where " + Absencja.kolumnaID + "= " + pmAbsencja.getId();
			
				if(dbAccess.GetCount(AbsencjaBean.NazwaTabeli + " where id_tabeli != "+pmAbsencja.getId()+ " and id_pracownika="+pmAbsencja.getIdPracownika()+" and Od_kiedy <= "+ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getDataDo())//
				+" and Do_Kiedy>="+ParseryDB.DateParserToSQL_SELECT(pmAbsencja.getDataOd()))==0)
				{	
				dbAccess.Zapisz(lvDelete);
				dbAccess.Zapisz(pmAbsencja.ZapiszDataSet());
				pmFrameDiagog.dispose();
				}
				else
					Komunikat.Nachodz¹NaSiebieOkresy.pokaz();
				
			
				

			}
		});
		btnZapisz.setBounds(206, 187, 97, 25);
		contentPane.add(btnZapisz);

		lblAbsencjaPracownika = new JLabel("Absencja pracownika: " + pmPracownik.getLvNazwa());
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

		mDataOd = new JFormattedTextField(pmAbsencja.getDataOd());
		mDataOd.setBounds(169, 38, 116, 22);
		contentPane.add(mDataOd);
		mDataOd.setColumns(10);

		mDataDo = new JFormattedTextField(pmAbsencja.getDataDo());
		mDataDo.setBounds(169, 75, 116, 22);
		contentPane.add(mDataDo);
		mDataDo.setColumns(10);

		JButton btnPickDate1 = new JButton("Wybierz datê");
		btnPickDate1.setBounds(297, 36, 116, 20);
		contentPane.add(btnPickDate1);
		btnPickDate1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Date lvData = new DatePicker().setPickedDate();
				if (lvData == null)
					return;
				mDataOd.setValue(lvData);
			}
		});

		JButton btnPickDate2 = new JButton("Wybierz datê");
		btnPickDate2.setBounds(297, 76, 116, 20);
		btnPickDate2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Date lvData = new DatePicker().setPickedDate();
				if (lvData == null)
					return;
				mDataDo.setValue(lvData);
				
			}
		});
		contentPane.add(btnPickDate2);
	}
}
