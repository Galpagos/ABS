package pl.home.components.frames.src;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Frames.AbstractOkno;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.time.LocalDate;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import Frames.dbAccess.Components.ListaObecnosciCellRenderer;
import Pracownik.PracownikRepository;
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
	private JFormattedTextField txtFontSize;
	private JFormattedTextField txtData;
	// private JList<PracownikDTO> tblLewa;
	private JList<PracownikDTO> tblPrawa;
	private JPanel mPane;
	// private JScrollPane mScrollPaneLeft;
	private JScrollPane mScrollPaneRight;
	// private JButton btnEdytujLewaKolumna;
	private JButton btnEdytujPrawaKolumna;
	protected DaneDoListyObecnosci mDane;
	private JButton btnEdytujDate;
	private JButton btnBoldLines;
	private JButton btnClearBoldLines;
	private PracownikRepository mRepo;
	private Set<Integer> mBoldLines;
	public SrcListaObecnosci(ListaObecnosciWejscie pmParams) {
		super(pmParams);

	}

	@Override
	protected void onOpen() {
		mRepo = new PracownikRepository();
		mDane = new DaneDoListyObecnosci();
		mBoldLines = new HashSet<>();
		// mDane.setListaLewa(new ArrayList<PracownikDTO>());
		List<Integer> lvInitIdList = Arrays.asList( //
				81, 16, 84, 20, 23, 26, 75, 27, 0, //
				2, 30, 35, 36, 39, 40, 42, 10, 43, 44, 45, 51, 46, 47, 0, //
				1, 22, 4, 5, 0, //
				18, 25, 8, 6, 9, 41, 0, //
				7);

		List<PracownikDTO> lvInitList = lvInitIdList//
				.stream()//
				.map(this::getPracownik)//
				.collect(Collectors.toList());
		mDane.setListaPrawa(lvInitList);
	}

	private PracownikDTO getPracownik(Integer pmId) {
		if (pmId == 0)
			return new PracownikDTO().setNazwa("----------");
		return mRepo.getPracownik(pmId);
	}
	@Override
	protected void przypiszMetody() {
		mokButton.addActionListener(e -> {
			setAlwaysOnTop(false);
			przygotujDane();
			wydrukuj();
			this.dispose();
		});
		// btnEdytujLewaKolumna.addActionListener(e -> {
		// setAlwaysOnTop(false);
		// OPrzygListyPracWejscie lvParams =
		// OPrzygListyPracWejscie.builder().withLista(mDane.getListaLewa()).build();
		// mDane.setListaLewa(new
		// OknoPrzygotowaniaListyPracownikow(lvParams).getListaPrawa());
		// odswiezKontrolki();
		// });
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

		btnBoldLines.addActionListener(e -> {
			for (int i = 0; i < tblPrawa.getSelectedIndices().length; i++)
				mBoldLines.add(tblPrawa.getSelectedIndices()[i]);
			JOptionPane.showMessageDialog(null, "Dodano pogrubienie", "", JOptionPane.INFORMATION_MESSAGE);
			odswiezKontrolki();

		});
		btnClearBoldLines.addActionListener(e -> {
			mBoldLines.clear();
			JOptionPane.showMessageDialog(null, "Usunięto pogrubienia w wydruku", "", JOptionPane.INFORMATION_MESSAGE);
			odswiezKontrolki();

		});
	}

	private void przygotujDane() {
		mDane.setFontSize(Integer.valueOf(txtFontSize.getText()));
		mDane.setStopka(txtStopka.getText());
		mDane.setNaglowek(txtNaglowek.getText());
		mDane.setData((LocalDate) txtData.getValue());
		mDane.setWysokoscWiersza(Integer.valueOf(txtWysokoscWiersza.getText()));
		mDane.setBoldLines(mBoldLines);
	}

	abstract protected void wydrukuj();

	@Override
	protected void odswiezKontrolki() {
		// tblLewa.setModel(createModel(mDane.getListaLewa()));
		tblPrawa.setModel(createModel(mDane.getListaPrawa()));
		tblPrawa.setCellRenderer(new ListaObecnosciCellRenderer(mBoldLines));
		tblPrawa.repaint();
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

		// mScrollPaneLeft = new JScrollPane();
		// mScrollPaneLeft.setBounds(48, 68, 262, 430);
		// getContentPane().add(mScrollPaneLeft);
		// mScrollPaneLeft.setPreferredSize(new Dimension(100, 100));
		// tblLewa = new JList<PracownikDTO>();
		// mScrollPaneLeft.setViewportView(tblLewa);
		mScrollPaneRight = new JScrollPane();
		mScrollPaneRight.setPreferredSize(new Dimension(100, 100));
		mScrollPaneRight.setBounds(40, 70, 500, 430);
		getContentPane().add(mScrollPaneRight);
		tblPrawa = new JList<PracownikDTO>();
		mScrollPaneRight.setViewportView(tblPrawa);

		// btnEdytujLewaKolumna = new JButton("Edytuj");
		// btnEdytujLewaKolumna.setBounds(213, 499, 97, 25);

		btnEdytujPrawaKolumna = new JButton("Edytuj");
		btnEdytujPrawaKolumna.setBounds(440, 505, 100, 25);
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
		lblWysokoKomrki.setBounds(550, 120, 117, 16);
		getContentPane().add(lblWysokoKomrki);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(100);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		txtWysokoscWiersza = new JFormattedTextField(formatter);
		txtWysokoscWiersza.setText("85");
		txtWysokoscWiersza.setBounds(700, 120, 69, 22);
		getContentPane().add(txtWysokoscWiersza);
		txtWysokoscWiersza.setColumns(10);

		JLabel lblFontSize = new JLabel("Rozmiar czcionki:");
		lblFontSize.setBounds(550, 150, 120, 16);
		getContentPane().add(lblFontSize);

		txtFontSize = new JFormattedTextField(formatter);
		txtFontSize.setText("55");
		txtFontSize.setBounds(700, 150, 69, 22);
		getContentPane().add(txtFontSize);
		txtFontSize.setColumns(10);

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(550, 90, 56, 16);
		getContentPane().add(lblData);

		txtData = new JFormattedTextField(LocalDate.now());
		txtData.setBounds(700, 90, 116, 22);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		txtData.setEditable(false);

		btnEdytujDate = new JButton("Edytuj");
		btnEdytujDate.setBounds(840, 90, 29, 20);
		getContentPane().add(btnEdytujDate);

		btnBoldLines = new JButton("Dodaj pogrubienie");
		btnBoldLines.setBounds(550, 180, 200, 20);
		getContentPane().add(btnBoldLines);

		btnClearBoldLines = new JButton("Wyczyść pogrubienia");
		btnClearBoldLines.setBounds(550, 210, 200, 20);
		getContentPane().add(btnClearBoldLines);

	}

	@Override
	protected ListaObecnosciWyjscie budujWyjscie() {

		return null;
	}
}
