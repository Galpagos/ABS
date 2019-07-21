package ListaObecnosci;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import PrzygotowanieDanych.PracownikDTO;
import dbAccess.Components.DatePicker;
import dbAccess.Components.OknoPrzygotowniaLustyPracownikow.OknoPrzygotowaniaListyPracownikow;

public class OknoListaObecnosci extends JDialog implements iDaneDoListyObecnosci
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNaglowek;
	private JTextField txtStopka;
	private JFormattedTextField txtWysokoscWiersza;
	private JFormattedTextField txtData;
	private JList<PracownikDTO> tblLewa;
	private JList<PracownikDTO> tblPrawa;

	private List<PracownikDTO> mListaLewa = new ArrayList<PracownikDTO>();
	private List<PracownikDTO> mListaPrawa = new ArrayList<PracownikDTO>();
	private LocalDate mDataSprawozdania = LocalDate.now();
	private String mNaglowek = "Lista obecnoœci                                               <DATA>";
	private String mStopka = "Proszê nie podpisywaæ innych pracowników. Ka¿dy z pracowników ma obowi¹zek potwierdziæ swoj¹ obecnoœæ w³asnorêcznym podpisem. ";
	private Integer mWysokoscWiersza = 40;

	public void setListaLewa(List<PracownikDTO> pmListaLewa)
	{
		mListaLewa = pmListaLewa;
	}

	public void setListaPrawa(List<PracownikDTO> pmListaPrawa)
	{
		mListaPrawa = pmListaPrawa;
	}

	public void setDataSprawozdania(LocalDate pmDataSprawozdania)
	{
		mDataSprawozdania = pmDataSprawozdania;
	}

	public void setNaglowek(String pmNaglowek)
	{
		mNaglowek = pmNaglowek;
	}

	public void setStopka(String pmStopka)
	{
		mStopka = pmStopka;
	}

	private ObslugaOknaListyObecnosci mObsluga = new ObslugaOknaListyObecnosci(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			OknoListaObecnosci dialog = new OknoListaObecnosci();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			dialog.setVisible(true);
			dialog.setAlwaysOnTop(true);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OknoListaObecnosci()
	{
		setBounds(100, 100, 977, 673);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 584, 947, 35);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(e -> {
					setAlwaysOnTop(false);
					mObsluga.wywolajWydruk(this);
					this.dispose();
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
				btnCancel.addActionListener(e -> this.dispose());
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(48, 68, 262, 430);
			getContentPane().add(scrollPane);
			scrollPane.setPreferredSize(new Dimension(100, 100));

			tblLewa = new JList<PracownikDTO>();
			scrollPane.setViewportView(tblLewa);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 100));
		scrollPane.setBounds(413, 68, 262, 430);
		getContentPane().add(scrollPane);

		tblPrawa = new JList<PracownikDTO>();
		scrollPane.setViewportView(tblPrawa);

		JButton btnEdytujLewaKolumna = new JButton("Edytuj");
		btnEdytujLewaKolumna.setBounds(213, 499, 97, 25);
		getContentPane().add(btnEdytujLewaKolumna);
		btnEdytujLewaKolumna.addActionListener(e -> {
			setAlwaysOnTop(false);
			mListaLewa = new OknoPrzygotowaniaListyPracownikow("Wybierz pracowników", mListaLewa).getListaPrawa();
			odswiezKontrolki();
		});

		JButton btnEdytujPrawaKolumna = new JButton("Edytuj");
		btnEdytujPrawaKolumna.setBounds(580, 499, 97, 25);
		getContentPane().add(btnEdytujPrawaKolumna);
		btnEdytujPrawaKolumna.addActionListener(e -> {
			setAlwaysOnTop(false);
			mListaPrawa = new OknoPrzygotowaniaListyPracownikow("Wybierz pracowników", mListaPrawa).getListaPrawa();
			odswiezKontrolki();
		});

		JLabel lblNagwek = new JLabel("Nag\u0142\u00F3wek:");
		lblNagwek.setBounds(48, 23, 87, 22);
		getContentPane().add(lblNagwek);

		txtNaglowek = new JTextField(mNaglowek);
		txtNaglowek.setBounds(112, 20, 835, 35);
		getContentPane().add(txtNaglowek);
		txtNaglowek.setColumns(10);
		txtNaglowek.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void removeUpdate(DocumentEvent pmE)
			{
				mNaglowek = txtNaglowek.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent pmE)
			{
				mNaglowek = txtNaglowek.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent pmE)
			{
				mNaglowek = txtNaglowek.getText();
			}
		});

		JLabel lblStopka = new JLabel("Stopka:");
		lblStopka.setBounds(48, 538, 87, 22);
		getContentPane().add(lblStopka);

		txtStopka = new JTextField(mStopka);
		txtStopka.setColumns(10);
		txtStopka.setBounds(112, 537, 835, 35);
		getContentPane().add(txtStopka);
		txtStopka.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent pmE)
			{
				mStopka = txtStopka.getText();

			}

			@Override
			public void insertUpdate(DocumentEvent pmE)
			{
				mStopka = txtStopka.getText();

			}

			@Override
			public void changedUpdate(DocumentEvent pmE)
			{
				mStopka = txtStopka.getText();
			}
		});

		JLabel lblWysokoKomrki = new JLabel("Wysoko\u015B\u0107 wiersza:");
		lblWysokoKomrki.setBounds(706, 408, 117, 16);
		getContentPane().add(lblWysokoKomrki);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(100);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		txtWysokoscWiersza = new JFormattedTextField(formatter);
		txtWysokoscWiersza.setText("40");
		txtWysokoscWiersza.setBounds(835, 405, 69, 22);
		getContentPane().add(txtWysokoscWiersza);
		txtWysokoscWiersza.setColumns(10);
		txtWysokoscWiersza.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent pmE)
			{
				mWysokoscWiersza = (Integer) txtWysokoscWiersza.getValue();
			}

			@Override
			public void insertUpdate(DocumentEvent pmE)
			{
				mWysokoscWiersza = (Integer) txtWysokoscWiersza.getValue();
			}

			@Override
			public void changedUpdate(DocumentEvent pmE)
			{
				mWysokoscWiersza = (Integer) txtWysokoscWiersza.getValue();
			}
		});

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(706, 93, 56, 16);
		getContentPane().add(lblData);

		txtData = new JFormattedTextField(LocalDate.now());
		txtData.setBounds(747, 90, 116, 22);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		txtData.setEditable(false);

		JButton btnEdytujDate = new JButton("Edytuj");
		btnEdytujDate.setBounds(875, 89, 29, 20);
		getContentPane().add(btnEdytujDate);
		btnEdytujDate.addActionListener(e -> {
			setAlwaysOnTop(false);
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData != null)
			{
				mDataSprawozdania = lvData;
				odswiezKontrolki();
			}
		});
	}

	@Override
	public List<PracownikDTO> getListaLewa()
	{
		return mListaLewa;
	}

	@Override
	public List<PracownikDTO> getListaPrawa()
	{
		return mListaPrawa;
	}

	@Override
	public LocalDate getData()
	{
		return mDataSprawozdania;
	}

	@Override
	public String getNaglowek()
	{
		return mNaglowek;
	}

	@Override
	public String getStopka()
	{
		return mStopka;
	}

	@Override
	public int getWysokoscWiersza()
	{
		return mWysokoscWiersza;
	}

	@SuppressWarnings(
	{ "serial" })
	public void odswiezKontrolki()
	{
		txtNaglowek.setText(mNaglowek);
		txtStopka.setText(mStopka);
		txtWysokoscWiersza.setText(mWysokoscWiersza.toString());
		tblLewa.setModel(new javax.swing.AbstractListModel<PracownikDTO>()
		{
			PracownikDTO[] strings = mListaLewa.toArray(new PracownikDTO[mListaLewa.size()]);

			public int getSize()
			{
				return strings.length;
			}

			public PracownikDTO getElementAt(int i)
			{
				return strings[i];
			}
		});
		tblPrawa.setModel(new javax.swing.AbstractListModel<PracownikDTO>()
		{
			PracownikDTO[] strings = mListaPrawa.toArray(new PracownikDTO[mListaPrawa.size()]);

			public int getSize()
			{
				return strings.length;
			}

			public PracownikDTO getElementAt(int i)
			{
				return strings[i];
			}
		});
		txtData.setText(mDataSprawozdania.toString());
	}

}
