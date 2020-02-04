package Frames.dbAccess.Frames.OknoPracownika;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Datownik.JodaTime;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.ScriptParams;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccess.AbsencjaBean;
import dbAccess.dbAccess;
import dbAccess.dbAccess.MyTableModel;

public class OknoPracownika extends SrcOknoPracownika implements InterfejsOknaPracownika {

	private static final long serialVersionUID = 1L;
	private PracownikDTO mPracownik;
	private ObslugaOknaPracownika mObsluga;

	public OknoPracownika(ScriptParams pmParams) {
		super(pmParams);

	}

	@Override
	protected void readParams() {
		super.readParams();
		setPracownik(mParams);
		mObsluga = new ObslugaOknaPracownika(this);
	}

	@Override
	protected void ustawTabele() {
		ustawTabele(tbAbsencje, mPracownik);
		odswiezKontrolki();
	}

	@Override
	protected void przypiszGrupe() {
		mObsluga.przypiszGrupe();
		odswiezKontrolki();
	}

	@Override
	protected void usunAbsencje() {
		mObsluga.UsunAbsencje();
		odswiezKontrolki();
	}

	@Override
	protected void modyfikujAbsencje() {
		mObsluga.ModyfikujAbsencje();
		odswiezKontrolki();
	}

	@Override
	protected void ustawUrlopNalezny() {
		mObsluga.ustawUrlopNalezny();
		odswiezKontrolki();
	}

	@Override
	protected void dodajAbsencje() {
		mObsluga.DodajAbsencje();
		odswiezKontrolki();
	}

	@Override
	protected void usunGrupe() {
		mObsluga.usunGrupe();
		odswiezKontrolki();
	}

	@Override
	protected void ustawDateUrodzenia() {
		mObsluga.ustawDateUrodzenia();
		odswiezKontrolki();
	}

	@Override
	protected void odswiezKontrolki() {
		super.odswiezKontrolki();
		lblPracownik.setText("Pracownik: " + mPracownik.getNazwa());
		odswiezLblDataUrodzenia();
		lblUrlopNalezny.setText(mObsluga.getUrlop(mPracownik));
		lblGrupy.setText(mObsluga.grupyPracownika());
		ustawTabele(tbAbsencje, mPracownik);
	}

	private void setPracownik(ScriptParams pmParams) {
		mPracownik = (PracownikDTO) pmParams.get(OknoPracownikaParams.PRACOWNIK_DTO);
	}

	private void odswiezLblDataUrodzenia() {
		lblDataUrodzenia.setText(mObsluga.getDataUrodzenia(mPracownik));
	}

	private JTable ustawTabele(JTable pmTabela, PracownikDTO pmPracownik) {
		try {
			String lvZapytanie = "Select ID_tabeli, RODZAJ, Od_Kiedy,Do_kiedy from Absencje where "//
					+ AbsencjaBean.kolumnaIdPracownika + " = " + pmPracownik.getId()//
					+ " AND (Year([" + AbsencjaBean.kolumnaDoKiedy + "]) = " + spnRok.getValue()//
					+ " OR Year([" + AbsencjaBean.kolumnaOdKiedy + "]) = " + spnRok.getValue() + ")";
			MyTableModel lvDTM = dbAccess.modelTabeliDB(lvZapytanie);
			pmTabela.setModel(lvDTM);
			lvDTM.fireTableDataChanged();
			TableColumnModel lvTcm = pmTabela.getColumnModel();
			for (int lvKolumna = pmTabela.getColumnCount() - 1; lvKolumna >= 0; lvKolumna--) {

				if (pmTabela.getColumnName(lvKolumna).contains("ID")) {
					TableColumn k = lvTcm.getColumn(lvKolumna);
					lvTcm.removeColumn(k);
				}
			}
			pmTabela.repaint();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pmTabela;
	}

	@Override
	public int getZaznaczenieTabeli() {
		return tbAbsencje.getSelectedRow();
	}

	@Override
	public AbsencjaDTO getAbsencjeZTabeli() {
		AbsencjaDTO lvAbsencja = new AbsencjaDTO();
		int lvRow = tbAbsencje.convertRowIndexToModel(getZaznaczenieTabeli());
		lvAbsencja.setId((Integer) tbAbsencje.getModel().getValueAt(lvRow, 0));
		Date lvOd = (Timestamp) tbAbsencje.getModel().getValueAt(lvRow, 2);
		Date lvDo = (Timestamp) tbAbsencje.getModel().getValueAt(lvRow, 3);
		lvAbsencja.setOkres(JodaTime.okresOdDo(lvOd, lvDo));
		lvAbsencja.setIdPracownika(mPracownik.getId());
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji
				.AbsencjaPoNazwie((String) tbAbsencje.getModel().getValueAt(lvRow, 1));
		lvAbsencja.setRodzaj(lvRodzajAbs);
		lvAbsencja.setIdPracownika(mPracownik.getId());
		return lvAbsencja;
	}

	@Override
	public PracownikDTO getPracownika() {
		return mPracownik;
	}
}
