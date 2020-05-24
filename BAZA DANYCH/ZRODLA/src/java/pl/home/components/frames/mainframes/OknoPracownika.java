package pl.home.components.frames.mainframes;

import java.sql.Timestamp;
import java.util.Date;

import Frames.dbAccess.Frames.OknoPracownika.InterfejsOknaPracownika;
import Frames.dbAccess.Frames.OknoPracownika.ObslugaOknaPracownika;
import ProjektGlowny.commons.utils.Interval;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.SLRodzajeAbsencji;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.parameters.OPracWejscie;
import pl.home.components.frames.src.SrcOknoPracownika;

public class OknoPracownika extends SrcOknoPracownika implements InterfejsOknaPracownika {

	private static final long serialVersionUID = 1L;
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
		ustawTylkoTabele();
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
		ustawTabele();
	}

	private void odswiezLblDataUrodzenia() {
		lblDataUrodzenia.setText(mObsluga.getDataUrodzenia(mPracownik));
	}

	private void ustawTylkoTabele() {

		tbAbsencje.reload(getZapytanieDoTabeli());

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
