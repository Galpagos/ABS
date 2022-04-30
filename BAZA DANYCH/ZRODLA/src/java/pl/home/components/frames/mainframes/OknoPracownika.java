package pl.home.components.frames.mainframes;

import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.sql.Timestamp;

import Frames.dbAccess.Frames.OknoPracownika.InterfejsOknaPracownika;
import Frames.dbAccess.Frames.OknoPracownika.ObslugaOknaPracownika;
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
		mPracownik = mParamsIn.getPracownik();
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
		mObsluga.modyfikujAbsencje();
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
	protected void odswiezKontrolki() {
		super.odswiezKontrolki();
		lblPracownik.setText("Pracownik: " + mPracownik.getNazwa());
		odswiezLblDataUrodzenia();
		lblUrlopNalezny.setText(mObsluga.getUrlop(mPracownik));
		lblGrupy.setText(mObsluga.grupyPracownika());
		lblEtat.setText(mObsluga.getEtat(mPracownik));
		ustawTabele();
	}

	private void odswiezLblDataUrodzenia() {
		lblDataUrodzenia.setText(mObsluga.getDataUrodzenia(mPracownik));
	}

	private void ustawTylkoTabele() {
		tbAbsencje.reload(getZapytanieDoTabeli());
	}

	@Override
	public int[] getZaznaczenieTabeli() {
		return tbAbsencje.getSelectedRows();
	}

	@Override
	public List<AbsencjaDTO> getAbsencjeZTabeli() {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		for (int lvInt : getZaznaczenieTabeli()) {
			AbsencjaDTO lvAbsencja = new AbsencjaDTO();
			if (lvInt == -1)
				return Collections.emptyList();
			int lvRow = tbAbsencje.convertRowIndexToModel(lvInt);
			lvAbsencja.setId((Integer) tbAbsencje.getModel().getValueAt(lvRow, 0));
			Date lvOd = (Timestamp) tbAbsencje.getModel().getValueAt(lvRow, 2);
			Date lvDo = (Timestamp) tbAbsencje.getModel().getValueAt(lvRow, 3);
			lvAbsencja.setOkres(new Interval(lvOd, lvDo));
			lvAbsencja.setIdPracownika(mPracownik.getId());
			SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.AbsencjaPoNazwie((String) tbAbsencje.getModel().getValueAt(lvRow, 1));
			lvAbsencja.setRodzaj(lvRodzajAbs);
			lvAbsencja.setProcent(SLEkwiwalentZaUrlop.getByKod(getProcentZTabeli(lvRow)));
			lvAbsencja.setIdPracownika(mPracownik.getId());
			lvLista.add(lvAbsencja);
		}
		return lvLista;
	}

	private String getProcentZTabeli(int lvRow) {
		return tbAbsencje.getModel().getValueAt(lvRow, 4) == null ? "0" : tbAbsencje.getModel().getValueAt(lvRow, 4).toString().replace("%", "");
	}

	@Override
	public PracownikDTO getPracownika() {
		return mPracownik;
	}

	@Override
	protected void ustawDane() {
		mPracownik = mObsluga.ustawEtat();
		odswiezKontrolki();
	}

}
