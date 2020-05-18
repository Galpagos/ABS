package pl.home.components.frames.src;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow.InterfejsPrzygotowaniaListyPracownikow;
import Grupy.GrupaDTO;
import ProjektGlowny.commons.Frames.AbstractOkno;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import pl.home.components.frames.parameters.OPrzygListyPracWejscie;
import pl.home.components.frames.parameters.OPrzygListyPracWyjscie;

public abstract class SrcOknoPrzygotowaniaListyPracownikow extends
		AbstractOkno<OPrzygListyPracWejscie, OPrzygListyPracWyjscie> implements InterfejsPrzygotowaniaListyPracownikow {

	private static final long serialVersionUID = 4904737240355272360L;
	JList<PracownikDTO> mTabLewa;
	JList<PracownikDTO> mTabPrawa;
	List<PracownikDTO> mListaLewa;
	protected List<PracownikDTO> mListaPrawa;
	protected JComboBox<GrupaDTO> cbWyborGrupy;
	private JPanel contentPanel;

	private JButton btnWszyscyWybrani;
	private JButton btnJedenWybrany;
	private JButton btnJedenOdrzucony;
	private JButton btnWszyscyOdrzuceni;
	private JButton btnPuste;
	private JLabel lblAbyZaznaczyKilka;
	private JScrollPane mScrollPane1;

	public SrcOknoPrzygotowaniaListyPracownikow(OPrzygListyPracWejscie pmParams) {
		super(pmParams);

	}

	@Override
	protected void beforeClose() {
	}

	public void dSrcOknoPrzygotowaniaListyPracownikow() {

	}

	@Override
	public List<PracownikDTO> getListaLewa() {
		return mListaLewa;
	}

	public List<PracownikDTO> WybierzPracownikow() {
		return mListaPrawa;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public void odswiezTabLewa() {
		mTabLewa.setModel(new javax.swing.AbstractListModel() {
			PracownikDTO[] strings = mListaLewa.toArray(new PracownikDTO[mListaLewa.size()]);

			@Override
			public int getSize() {
				return strings.length;
			}

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}
		});
	}

	@Override
	public List<PracownikDTO> getListaPrawa() {
		return mListaPrawa;
	}

	@Override
	public void setListaPrawa(List<PracownikDTO> pmListaPrawa) {
		mListaPrawa = pmListaPrawa;

	}

	@Override
	public void setListaLewa(List<PracownikDTO> pmLista) {
		mListaLewa = pmLista;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public void odswiezTabPrawa() {
		mTabPrawa.setModel(new javax.swing.AbstractListModel() {
			PracownikDTO[] strings = mListaPrawa.toArray(new PracownikDTO[mListaPrawa.size()]);

			@Override
			public int getSize() {
				return strings.length;
			}

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}
		});
	}

	@Override
	public List<PracownikDTO> getSelectionLewa() {
		return mTabLewa.getSelectedValuesList();
	}

	@Override
	public int[] getSelectionLewaInt() {
		return mTabLewa.getSelectedIndices();
	}

	public int[] getSelectionPrawaInt() {
		return mTabPrawa.getSelectedIndices();
	}

	@Override
	protected void readParams() {
		mListaPrawa = new ArrayList<PracownikDTO>();
		mListaPrawa.addAll(mParams.getLista());
	}

	@Override
	protected void przypiszMetody() {
		btnWszyscyWybrani.addActionListener(e -> wszyscyWPrawo());
		btnJedenWybrany.addActionListener(e -> jedenwPrawo());
		btnJedenOdrzucony.addActionListener(e -> usunZPrawo());
		btnWszyscyOdrzuceni.addActionListener(e -> wyczyscLewa());
		btnPuste.addActionListener(e -> dodajPrzerwe());

		cbWyborGrupy.addActionListener(e -> wyborGRupy());

	}

	protected abstract void wyborGRupy();

	protected abstract void dodajPrzerwe();

	protected abstract void wyczyscLewa();

	protected abstract void usunZPrawo();

	protected abstract void jedenwPrawo();

	protected abstract void wszyscyWPrawo();

	@Override
	protected void odswiezKontrolki() {
		odswiezTabLewa();
		odswiezTabPrawa();
	}

	@Override
	protected void budujOkno() {

		mListaLewa = new ArrayList<PracownikDTO>();

		setTitle(mParams.getNazwa());
		contentPanel = new JPanel();
		setBounds(100, 100, 681, 525);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 689, 472);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JPanel mArrowPanel = new JPanel();
		mArrowPanel.setBounds(243, 0, 189, 434);
		contentPanel.add(mArrowPanel);
		mArrowPanel.setLayout(null);

		btnWszyscyWybrani = new JButton("Dodaj wszystkich");
		btnWszyscyWybrani.setBounds(12, 122, 165, 25);
		mArrowPanel.add(btnWszyscyWybrani);

		btnJedenWybrany = new JButton("Dodaj wybranych");
		btnJedenWybrany.setBounds(12, 160, 165, 25);
		mArrowPanel.add(btnJedenWybrany);

		btnJedenOdrzucony = new JButton("Usuń");
		btnJedenOdrzucony.setBounds(12, 199, 165, 25);
		mArrowPanel.add(btnJedenOdrzucony);

		btnWszyscyOdrzuceni = new JButton("Wyczyść");
		btnWszyscyOdrzuceni.setBounds(12, 237, 165, 25);
		mArrowPanel.add(btnWszyscyOdrzuceni);

		btnPuste = new JButton("Dodaj przerw\u0119");
		btnPuste.setBounds(12, 275, 165, 25);
		mArrowPanel.add(btnPuste);

		lblAbyZaznaczyKilka = new JLabel(
				"<html>Aby zaznaczyć kilka osób \r\nwybierz <b>Ctrl</b> lub <b>Ctrl<b/>+<b>Shitft</b></html>");
		lblAbyZaznaczyKilka.setBounds(12, 13, 165, 97);
		mArrowPanel.add(lblAbyZaznaczyKilka);

		mScrollPane1 = new JScrollPane();
		mScrollPane1.setBounds(12, 13, 230, 418);
		contentPanel.add(mScrollPane1);

		mTabLewa = new JList<PracownikDTO>();
		mTabLewa.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		mScrollPane1.setViewportView(mTabLewa);

		JScrollPane mScrollPane2 = new JScrollPane();
		mScrollPane2.setBounds(430, 13, 230, 421);
		contentPanel.add(mScrollPane2);

		mTabPrawa = new JList<PracownikDTO>();
		mTabPrawa.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		mScrollPane2.setViewportView(mTabPrawa);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 433, 689, 52);
		contentPanel.add(buttonPane);

		cbWyborGrupy = new JComboBox<GrupaDTO>();
		cbWyborGrupy.setBounds(12, 13, 154, 25);
		cbWyborGrupy.setSelectedItem(" ");

		mokButton.setBounds(487, 13, 77, 25);

		getRootPane().setDefaultButton(mokButton);

		mcancelButton.setBounds(576, 12, 77, 25);

		buttonPane.setLayout(null);
		buttonPane.add(cbWyborGrupy);
		buttonPane.add(mokButton);
		buttonPane.add(mcancelButton);

	}

	@Override
	protected void onOpen() {
	}

}
