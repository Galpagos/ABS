package pl.home.components.frames.mainframes;

import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Data_Zwolnienia;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.ID_tabeli;
import static dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns.Pracownik;

import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.DbSelect;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.config.Config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.EventQueue;

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

	ObslugaOknaGlownego mObsluga = new ObslugaOknaGlownego(this);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Config.setSystemTableNames(SystemTablesNames.ABSENCJE);
				new OknoGlowne().setVisible(true);
			}
		});
	}

	public OknoGlowne() {
		super();
		addWindowListener(new Przekoder());
		ustawTabele(tbPracownicy);
		btnPokazPracownika.addActionListener(lvE -> mObsluga.pokazPracownika());
		btnDodajPracownika.addActionListener(lvE -> mObsluga.dodajPracownika());
		btnUsunPracownika.addActionListener(lvE -> mObsluga.usunPracownika());
		btnWyjcie.addActionListener(lvE -> System.exit(0));
		btnPokazNieobecnych.addActionListener(lvE -> mObsluga.pokazNieobecnych());
		btnDodajDzienWolny.addActionListener(lvE -> mObsluga.dodajDzienWolny());
		btnPokazDniWolne.addActionListener(lvE -> mObsluga.pokazDniWolne());
		btnSprawozdanie.addActionListener(lvE -> mObsluga.sprawozdanie());
		btnZwolnij.addActionListener(lvE -> mObsluga.zwolnijPracownika());
		mFiltrPracownika.getDocument().addDocumentListener(ustawListenerFiltruPracownika());
		cbCzyUsunieci.addActionListener(lvE -> {
			odswiezTabele();
			odswiezKontrolki();
		});
		btnZatrudnij.addActionListener(lvE -> mObsluga.zatrudnijPracownika());
		tbPracownicy.getSelectionModel().addListSelectionListener(e -> odswiezKontrolki());
		btnDodajMasowaAbsencje.addActionListener(lvE -> mObsluga.dodajMasowaAbsencje());
		repaint();
		odswiezKontrolki();
	}

	private DocumentListener ustawListenerFiltruPracownika() {
		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent pmE) {
				ustawTabele(tbPracownicy);
			}

			@Override
			public void insertUpdate(DocumentEvent pmE) {
				ustawTabele(tbPracownicy);
			}

			@Override
			public void removeUpdate(DocumentEvent pmE) {
				ustawTabele(tbPracownicy);
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
		btnZwolnij.setVisible(getZaznaczenieTabeli() >= 0 && !czyZwolniony(getPracownikZTabeli()));
		btnZatrudnij.setVisible(!btnZwolnij.isVisible());
		btnPokazPracownika.setEnabled(getZaznaczenieTabeli() >= 0);
		btnUsunPracownika.setEnabled(getZaznaczenieTabeli() >= 0);
		btnZwolnij.setEnabled(getZaznaczenieTabeli() >= 0);
		btnZatrudnij.setEnabled(getZaznaczenieTabeli() >= 0);
		btnDodajMasowaAbsencje.setVisible(tbPracownicy.getSelectedRows().length > 1);
		repaint();
	}

	private boolean czyZwolniony(PracownikDTO pmPracownik) {

		return pmPracownik.getDataZwolnienia() != null;
	}

	@Override
	public void odswiezTabele() {
		ustawTabele(tbPracownicy);
	}
}
