package dbAccess.Components.OknoPrzygotowniaLustyPracownikow;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Grupy.GrupaDTO;
import PrzygotowanieDanych.PracownikDTO;

public class OknoPrzygotowaniaListyPracownikow implements InterfejsPrzygotowaniaListyPracownikow
{
	JDialog mOkno;
	JList<PracownikDTO> mTabLewa;
	JList<PracownikDTO> mTabPrawa;
	List<PracownikDTO> mListaLewa;
	List<PracownikDTO> mListaPrawa;
	ObslugaPrzygotowaniaListyPracownikow mOblsluga;

	private final JPanel contentPanel = new JPanel();
	private JButton mokButton;
	private JButton mcancelButton;

	public OknoPrzygotowaniaListyPracownikow(String pmNazwa, List<PracownikDTO> pmListaPrawa)
	{

		mListaLewa = new ArrayList<PracownikDTO>();
		mListaPrawa = pmListaPrawa;
		mOblsluga = new ObslugaPrzygotowaniaListyPracownikow(this);

		mOkno = new JDialog();
		mOkno.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		mOkno.setTitle(pmNazwa);
		mOkno.setModalityType(ModalityType.APPLICATION_MODAL);

		mOkno.setBounds(100, 100, 681, 525);
		mOkno.getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 689, 472);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mOkno.getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JPanel mArrowPanel = new JPanel();
			mArrowPanel.setBounds(243, 0, 189, 434);
			contentPanel.add(mArrowPanel);
			mArrowPanel.setLayout(null);

			JButton btnWszyscyWybrani = new JButton("Dodaj wszystkich");
			btnWszyscyWybrani.addActionListener(e -> mOblsluga.wszyscyWPrawo());
			btnWszyscyWybrani.setBounds(12, 122, 165, 25);
			mArrowPanel.add(btnWszyscyWybrani);

			JButton btnJedenWybrany = new JButton("Dodaj wybranych");
			btnJedenWybrany.addActionListener(e -> mOblsluga.dodajWPrawo());
			btnJedenWybrany.setBounds(12, 160, 165, 25);
			mArrowPanel.add(btnJedenWybrany);

			JButton btnJedenOdrzucony = new JButton("Usu\u0144");
			btnJedenOdrzucony.addActionListener(e -> mOblsluga.usunZPrawa());
			btnJedenOdrzucony.setBounds(12, 199, 165, 25);
			mArrowPanel.add(btnJedenOdrzucony);

			JButton btnWszyscyOdrzuceni = new JButton("Wyczy\u015B\u0107");
			btnWszyscyOdrzuceni.addActionListener(e -> mOblsluga.wyczyscLewa());
			btnWszyscyOdrzuceni.setBounds(12, 237, 165, 25);
			mArrowPanel.add(btnWszyscyOdrzuceni);

			JButton btnPuste = new JButton("Dodaj przerw\u0119");
			btnPuste.addActionListener(e -> mOblsluga.dodajPrzerwe());
			btnPuste.setBounds(12, 275, 165, 25);
			mArrowPanel.add(btnPuste);

			JLabel lblAbyZaznaczyKilka = new JLabel(
					"<html>Aby zaznaczy\u0107 kilka os\u00F3b \r\nwybierz <b>Ctrl</b> lub <b>Ctrl<b/>+<b>Shitft</b></html>");
			lblAbyZaznaczyKilka.setBounds(12, 13, 165, 97);
			mArrowPanel.add(lblAbyZaznaczyKilka);
		}

		JScrollPane mScrollPane1 = new JScrollPane();
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
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 433, 689, 52);
			contentPanel.add(buttonPane);

			JComboBox<GrupaDTO> cbWyborGrupy = new JComboBox(mOblsluga.pobierzGrupy());
			cbWyborGrupy.setBounds(12, 13, 154, 25);
			cbWyborGrupy.setSelectedItem(" ");
			cbWyborGrupy.addActionListener(e -> {
				mOblsluga.zasilTabele((GrupaDTO) cbWyborGrupy.getSelectedItem());
				odswiezTabLewa();

			});
			{
				mokButton = new JButton("OK");
				mokButton.setBounds(487, 13, 77, 25);
				mokButton.setActionCommand("OK");
				mokButton.addActionListener(e -> mOkno.dispose());
				mOkno.getRootPane().setDefaultButton(mokButton);
			}
			{
				mcancelButton = new JButton("Cancel");
				mcancelButton.addActionListener(e -> {
					mListaPrawa = new ArrayList<PracownikDTO>();
					mOkno.dispose();
				});
				mcancelButton.setBounds(576, 12, 77, 25);
				mcancelButton.setVerticalTextPosition(SwingConstants.BOTTOM);
				mcancelButton.setVerticalAlignment(SwingConstants.BOTTOM);
				mcancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
				mcancelButton.setActionCommand("Cancel");
			}
			buttonPane.setLayout(null);
			buttonPane.add(cbWyborGrupy);
			buttonPane.add(mokButton);
			buttonPane.add(mcancelButton);
		}

		init();

		mOkno.setVisible(true);

	}

	public OknoPrzygotowaniaListyPracownikow(String pmString)
	{
		this(pmString, new ArrayList<PracownikDTO>());
	}

	private void init()
	{
		GrupaDTO lvStart = new GrupaDTO();
		lvStart.setNazwa("Wszyscy");
		mOblsluga.zasilTabele(lvStart);
		odswiezTabLewa();
		odswiezTabPrawa();
	}

	public List<PracownikDTO> getListaLewa()
	{
		return mListaLewa;
	}

	public List<PracownikDTO> WybierzPracownikow()
	{
		return mListaPrawa;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes", "serial" })
	public void odswiezTabLewa()
	{
		mTabLewa.setModel(new javax.swing.AbstractListModel()
		{
			PracownikDTO[] strings = mListaLewa.toArray(new PracownikDTO[mListaLewa.size()]);

			public int getSize()
			{
				return strings.length;
			}

			public Object getElementAt(int i)
			{
				return strings[i];
			}
		});
	}

	public List<PracownikDTO> getListaPrawa()
	{
		return mListaPrawa;
	}

	public void setListaPrawa(List<PracownikDTO> pmListaPrawa)
	{
		mListaPrawa = pmListaPrawa;

	}

	@Override
	public void setListaLewa(List<PracownikDTO> pmLista)
	{
		mListaLewa = pmLista;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes", "serial" })
	public void odswiezTabPrawa()
	{
		mTabPrawa.setModel(new javax.swing.AbstractListModel()
		{
			PracownikDTO[] strings = mListaPrawa.toArray(new PracownikDTO[mListaPrawa.size()]);

			public int getSize()
			{
				return strings.length;
			}

			public Object getElementAt(int i)
			{
				return strings[i];
			}
		});
	}

	public List<PracownikDTO> getSelectionLewa()
	{
		return mTabLewa.getSelectedValuesList();
	}

	public int[] getSelectionLewaInt()
	{
		return mTabLewa.getSelectedIndices();
	}

	public int[] getSelectionPrawaInt()
	{
		return mTabPrawa.getSelectedIndices();
	}
}
