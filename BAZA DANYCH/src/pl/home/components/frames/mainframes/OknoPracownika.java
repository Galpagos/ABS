package pl.home.components.frames.mainframes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import Datownik.Interval;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.LTableModel;
import Frames.dbAccess.Frames.OknoPracownika.InterfejsOknaPracownika;
import Frames.dbAccess.Frames.OknoPracownika.ObslugaOknaPracownika;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.DbSelect;
import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.parameters.OPracWejscie;
import pl.home.components.frames.src.SrcOknoPracownika;

public class OknoPracownika extends SrcOknoPracownika implements InterfejsOknaPracownika {

	private static final long serialVersionUID = 1L;
	private PracownikDTO mPracownik;
	private ObslugaOknaPracownika mObsluga;

	public OknoPracownika(OPracWejscie pmParams) {
		super(pmParams);

	}

	@Override
	protected void readParams() {
		super.readParams();
		mPracownik = mParams.getPracownik();
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

	private void odswiezLblDataUrodzenia() {
		lblDataUrodzenia.setText(mObsluga.getDataUrodzenia(mPracownik));
	}

	private JTable ustawTabele(JTable pmTabela, PracownikDTO pmPracownik) {

		DbSelect lvZapytanie = QueryBuilder.SELECT()//
				.select(AbsencjeColumns.ID_tabeli, AbsencjeColumns.RODZAJ, AbsencjeColumns.Od_kiedy,
						AbsencjeColumns.Do_kiedy, AbsencjeColumns.EKWIWALENT)//
				.andWarunek(AbsencjeColumns.ID_pracownika, pmPracownik.getId())//
				.andAfterOrEqual(AbsencjeColumns.Od_kiedy, LocalDate.of((int) spnRok.getValue(), 1, 1))//
				.andBeforeOrEqual(AbsencjeColumns.Do_kiedy, YearMonth.of((int) spnRok.getValue(), 12).atEndOfMonth());

//		String lvZapytanie = "Select ID_tabeli, RODZAJ, Od_Kiedy,Do_kiedy,EKWIWALENT from Absencje where "//
//				+ AbsencjaBean.kolumnaIdPracownika + " = " + pmPracownik.getId()//
//				+ " AND (Year([" + AbsencjaBean.kolumnaDoKiedy + "]) = " + spnRok.getValue()//
//				+ " OR Year([" + AbsencjaBean.kolumnaOdKiedy + "]) = " + spnRok.getValue() + ")";
		TableModel lvDTM;

		// lvDTM = dbAccess.modelTabeliDB(lvZapytanie);

		lvDTM = new LTableModel(lvZapytanie.execute());
		pmTabela.setModel(lvDTM);
		TableColumnModel lvTcm = pmTabela.getColumnModel();
		if (lvTcm.getColumnCount() != 0) {
			lvTcm.removeColumn(lvTcm.getColumn(0));
		}
		if (lvDTM.getRowCount() > 0)
			pmTabela.setRowSelectionInterval(0, 0);
		pmTabela.repaint();

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
		lvAbsencja.setOkres(new Interval(lvOd, lvDo));
		lvAbsencja.setIdPracownika(mPracownik.getId());
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji
				.AbsencjaPoNazwie((String) tbAbsencje.getModel().getValueAt(lvRow, 1));
		lvAbsencja.setRodzaj(lvRodzajAbs);
		lvAbsencja.setProcent(SLEkwiwalentZaUrlop.getByKod(getProcentZTabeli(lvRow)));
		lvAbsencja.setIdPracownika(mPracownik.getId());
		return lvAbsencja;
	}

	private String getProcentZTabeli(int lvRow) {
		return tbAbsencje.getModel().getValueAt(lvRow, 4) == null ? "0"
				: tbAbsencje.getModel().getValueAt(lvRow, 4).toString();
	}

	@Override
	public PracownikDTO getPracownika() {
		return mPracownik;
	}

}
