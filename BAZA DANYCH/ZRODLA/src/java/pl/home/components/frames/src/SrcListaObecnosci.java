package pl.home.components.frames.src;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Frames.AbstractOkno;
import Wydruki.ListaObecnosci.DaneDoListyObecnosci;
import Wydruki.ListaObecnosci.ListaObecnosciConst;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import pl.home.components.frames.mainframes.OknoPrzygotowaniaListyPracownikow;
import pl.home.components.frames.parameters.ListaObecnosciWejscie;
import pl.home.components.frames.parameters.ListaObecnosciWyjscie;
import pl.home.components.frames.parameters.OPrzygListyPracWejscie;

public abstract class SrcListaObecnosci extends AbstractOkno<ListaObecnosciWejscie, ListaObecnosciWyjscie> {

	private static final long serialVersionUID = 1L;
	private JTextField txtNaglowek;
	private JTextField txtStopka;
	private JFormattedTextField txtWysokoscWiersza;
	private JFormattedTextField txtData;
	private JList<PracownikDTO> tblLewa;
	private JList<PracownikDTO> tblPrawa;
	private JPanel mPane;
	private JScrollPane mScrollPaneLeft;
	private JScrollPane mScrollPaneRight;
	private JButton btnEdytujLewaKolumna;
	private JButton btnEdytujPrawaKolumna;
	protected DaneDoListyObecnosci mDane;
	private JButton btnEdytujDate;

	public SrcListaObecnosci(ListaObecnosciWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void beforeClose() {
	}

	@Override
	protected void onOpen() {
		mDane = new DaneDoListyObecnosci();
		mDane.setListaLewa(new ArrayList<PracownikDTO>());
		mDane.setListaPrawa(new ArrayList<PracownikDTO>());
	}

	@Override
	protected void przypiszMetody() {
		mokButton.addActionListener(e -> {
			setAlwaysOnTop(false);
			przygotujDane();
			wydrukuj();
			this.dispose();
		});
		btnEdytujLewaKolumna.addActionListener(e -> {
			setAlwaysOnTop(false);
			OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().withLista(mDane.getListaLewa()).build();
			mDane.setListaLewa(new OknoPrzygotowaniaListyPracownikow(lvParams).getListaPrawa());
			odswiezKontrolki();
		});
		btnEdytujPrawaKolumna.addActionListener(e -> {
			setAlwaysOnTop(false);
			OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().withLista(mDane.getListaPrawa()).build();
			mDane.setListaPrawa(new OknoPrzygotowaniaListyPracownikow(lvParams).getListaPrawa());
			odswiezKontrolki();
		});

		btnEdytujDate.addActionListener(e -> {
			setAlwaysOnTop(false);
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData != null) {
				txtData.setValue(lvData);
				odswiezKontrolki();
			}
		});
	}

	private void przygotujDane() {
		mDane.setWysokoscWiersza((Integer) txtWysokoscWiersza.getValue());
		mDane.setStopka(txtStopka.getText());
		mDane.setNaglowek(txtNaglowek.getText());
		mDane.setData((LocalDate) txtData.getValue());
		mDane.setWysokoscWiersza(Integer.valueOf(txtWysokoscWiersza.getText()));
	}

	abstract protected void wydrukuj();

	@Override
	protected void odswiezKontrolki() {
		tblLewa.setModel(createModel(mDane.getListaLewa()));
		tblPrawa.setModel(createModel(mDane.getListaPrawa()));
	}

	@SuppressWarnings("serial")
	private AbstractListModel<PracownikDTO> createModel(List<PracownikDTO> mLista) {
		return new javax.swing.AbstractListModel<PracownikDTO>() {
			PracownikDTO[] lvText = mLista.toArray(new PracownikDTO[mLista.size()]);

			@Override
			public int getSize() {
				return lvText.length;
			}

			@Override
			public PracownikDTO getElementAt(int i) {
				return lvText[i];
			}
		};
	}

	@Override
	protected void budujOkno() {
		setBounds(100, 100, 977, 673);
		getContentPane().setLayout(null);

		mPane = new JPanel();
		mPane.setBounds(0, 584, 947, 35);
		mPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(mPane);
		mPane.add(mokButton);
		getRootPane().setDefaultButton(mokButton);
		mPane.add(mcancelButton);

		mScrollPaneLeft = new JScrollPane();
		mScrollPaneLeft.setBounds(48, 68, 262, 430);
		getContentPane().add(mScrollPaneLeft);
		mScrollPaneLeft.setPreferredSize(new Dimension(100, 100));
		tblLewa = new JList<PracownikDTO>();
		mScrollPaneLeft.setViewportView(tblLewa);
		mScrollPaneRight = new JScrollPane();
		mScrollPaneRight.setPreferredSize(new Dimension(100, 100));
		mScrollPaneRight.setBounds(413, 68, 262, 430);
		getContentPane().add(mScrollPaneRight);
		tblPrawa = new JList<PracownikDTO>();
		mScrollPaneRight.setViewportView(tblPrawa);

		btnEdytujLewaKolumna = new JButton("Edytuj");
		btnEdytujLewaKolumna.setBounds(213, 499, 97, 25);
		getContentPane().add(btnEdytujLewaKolumna);

		btnEdytujPrawaKolumna = new JButton("Edytuj");
		btnEdytujPrawaKolumna.setBounds(580, 499, 97, 25);
		getContentPane().add(btnEdytujPrawaKolumna);

		JLabel lblNagwek = new JLabel("Nagłówek");
		lblNagwek.setBounds(48, 23, 87, 22);
		getContentPane().add(lblNagwek);

		txtNaglowek = new JTextField(ListaObecnosciConst.NAGLOWEK);
		txtNaglowek.setBounds(112, 20, 835, 35);
		getContentPane().add(txtNaglowek);
		txtNaglowek.setColumns(10);
		JLabel lblStopka = new JLabel("Stopka:");
		lblStopka.setBounds(48, 538, 87, 22);
		getContentPane().add(lblStopka);

		txtStopka = new JTextField(ListaObecnosciConst.STOPKA);
		txtStopka.setColumns(10);
		txtStopka.setBounds(112, 537, 835, 35);
		getContentPane().add(txtStopka);

		JLabel lblWysokoKomrki = new JLabel("Wysokość wiersza:");
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

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(706, 93, 56, 16);
		getContentPane().add(lblData);

		txtData = new JFormattedTextField(LocalDate.now());
		txtData.setBounds(747, 90, 116, 22);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		txtData.setEditable(false);

		btnEdytujDate = new JButton("Edytuj");
		btnEdytujDate.setBounds(875, 89, 29, 20);
		getContentPane().add(btnEdytujDate);
	}

	@Override
	protected ListaObecnosciWyjscie budujWyjscie() {

		return null;
	}

}
