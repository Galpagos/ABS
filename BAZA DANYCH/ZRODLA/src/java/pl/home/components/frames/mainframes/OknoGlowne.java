package pl.home.components.frames.mainframes;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Data_Zwolnienia;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;

import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.DbSelect;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.ParametryWyjscia;
import ProjektGlowny.commons.config.Config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Frames.dbAccess.Frames.OknoGlowne.InterfejsOknaGlownego;
import Frames.dbAccess.Frames.OknoGlowne.ObslugaOknaGlownego;
import Frames.dbAccess.Frames.OknoGlowne.Przekoder;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;
import pl.home.components.frames.src.SrcOknoGlowne;

public class OknoGlowne extends SrcOknoGlowne implements InterfejsOknaGlownego {

	private static final long serialVersionUID = 1L;

	ObslugaOknaGlownego mObsluga = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Config.setSystemTableNames(SystemTablesNames.ABSENCJE);
				JFrame lvFrame = new JFrame();
				ImageIcon lvIcon = new ImageIcon("C:\\GITHUB\\BAZA DANYCH\\ZRODLA\\src\\main\\resources\\icon.png");
				lvFrame.setIconImage(lvIcon.getImage());
				lvFrame.setUndecorated(true);
				lvFrame.setVisible(true);
				lvFrame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent we) {
						// if (new SrcLogowanie(null).get().isAccepted())
						new OknoGlowne().get();
						System.exit(0);
					}
				});
				lvFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			}
		});
	}

	public OknoGlowne() {
		super(null);
	}

	private DocumentListener ustawListenerFiltruPracownika() {
		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent pmE) {
				odswiezTabele();
			}

			@Override
			public void insertUpdate(DocumentEvent pmE) {
				odswiezTabele();
			}

			@Override
			public void removeUpdate(DocumentEvent pmE) {
				odswiezTabele();
			}
		};
	}

	private void ustawTabele(LTable pmTabela) {

		DbSelect lvZapytanieLS = QueryBuilder//
				.SELECT()//
				.select(ID_tabeli, Pracownik)//
				.andWarunek(Pracownik + " like \"%" + mFiltrPracownika.getText() + "%\"");

		if (!cbCzyUsunieci.isSelected())
			lvZapytanieLS = lvZapytanieLS.andWarunek(Data_Zwolnienia, null);

		pmTabela.reload(lvZapytanieLS.execute());
	}

	@Override
	public int getZaznaczenieTabeli() {
		return tbPracownicy.getSelectedRow();
	}

	@Override
	public List<PracownikDTO> getPracownicyZTabeli() {
		int[] lvTableIndex = tbPracownicy.getSelectedRows();
		return Arrays.stream(lvTableIndex)//
				.boxed()//
				.map(lvIndeks -> pobierzPracownika(lvIndeks))//
				.collect(Collectors.toList());
	}

	public PracownikDTO pobierzPracownika(int pmIndeksZTabeli) {
		int lvRow = tbPracownicy.convertRowIndexToModel(pmIndeksZTabeli);
		Integer lvIdPracownika = (Integer) tbPracownicy.getModel().getValueAt(lvRow, 0);
		return mObsluga.getPracownik(lvIdPracownika);
	}

	@Override
	public PracownikDTO getPracownikZTabeli() {
		return pobierzPracownika(getZaznaczenieTabeli());
	}

	@Override
	public void odswiezKontrolki() {
		btnZwolnij.setVisible(czyJedenRekord() && !czyZwolniony(getPracownikZTabeli()));
		btnZatrudnij.setVisible(!btnZwolnij.isVisible());
		btnPokazPracownika.setEnabled(czyJedenRekord());
		btnUsunPracownika.setEnabled(czyJedenRekord());
		btnZwolnij.setEnabled(czyJedenRekord());
		btnZatrudnij.setEnabled(czyJedenRekord());
		btnDodajMasowaAbsencje.setVisible(tbPracownicy.getSelectedRows().length > 1);
		repaint();
	}

	private boolean czyJedenRekord() {
		return tbPracownicy.getSelectedRowCount() == 1 && tbPracownicy.getRowCount() != 0;
	}

	private boolean czyZwolniony(PracownikDTO pmPracownik) {

		return pmPracownik.getDataZwolnienia() != null;
	}

	@Override
	public void odswiezTabele() {
		ustawTabele(tbPracownicy);
		odswiezKontrolki();
	}

	@Override
	protected void beforeClose() {

	}

	@Override
	protected ParametryWyjscia budujWyjscie() {
		return null;
	}

	@Override
	protected void onOpen() {

	}

	@Override
	protected void przypiszMetody() {
		addWindowListener(new Przekoder());
		ustawTabele(tbPracownicy);
		btnPokazPracownika.addActionListener(lvE -> mObsluga.pokazPracownika());
		btnDodajPracownika.addActionListener(lvE -> mObsluga.dodajPracownika());
		btnUsunPracownika.addActionListener(lvE -> mObsluga.usunPracownika());
		btnPokazNieobecnych.addActionListener(lvE -> mObsluga.pokazNieobecnych());
		btnDodajDzienWolny.addActionListener(lvE -> mObsluga.dodajDzienWolny());
		btnPokazDniWolne.addActionListener(lvE -> mObsluga.pokazDniWolne());
		btnSprawozdanie.addActionListener(lvE -> mObsluga.sprawozdanie());
		btnZwolnij.addActionListener(lvE -> mObsluga.zwolnijPracownika());
		mFiltrPracownika.getDocument().addDocumentListener(ustawListenerFiltruPracownika());
		cbCzyUsunieci.addActionListener(lvE -> odswiezKontrolki());
		btnZatrudnij.addActionListener(lvE -> mObsluga.zatrudnijPracownika());
		tbPracownicy.getSelectionModel().addListSelectionListener(e -> odswiezKontrolki());
		btnDodajMasowaAbsencje.addActionListener(lvE -> mObsluga.dodajMasowaAbsencje());
		btnOneDayView.addActionListener(lvE -> mObsluga.showOneDayView());
		btnOneMonthView.addActionListener(lvE -> mObsluga.showOneMonthView());
		btnSaturdayView.addActionListener(lvE -> mObsluga.showSaturdayView());
	}

	@Override
	protected void readParams() {
		mObsluga = new ObslugaOknaGlownego(this);
	}
}
