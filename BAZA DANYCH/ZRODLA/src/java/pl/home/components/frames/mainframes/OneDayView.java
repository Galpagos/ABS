package pl.home.components.frames.mainframes;

import ProjektGlowny.commons.utils.Interval;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.absencje.ObslugaAbsencji;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.parameters.OPrzygListyPracWejscie;
import pl.home.components.frames.parameters.OPrzygListyPracWyjscie;
import pl.home.components.frames.parameters.OneDayViewIn;
import pl.home.components.frames.src.SrcOneDayView;

public class OneDayView extends SrcOneDayView {

	private static final long serialVersionUID = 1L;
	private ObslugaAbsencji mObsluga = null;
	public OneDayView(OneDayViewIn pmParams) {
		super(pmParams);
	}
	@Override
	protected void onOpen() {
		super.onOpen();
		mObsluga = new ObslugaAbsencji();
	}

	@Override
	protected void minusMonth() {
		mData = mData.minusMonths(1);
		odswiezKontrolki();
	}

	@Override
	protected void addMonth() {
		mData = mData.plusMonths(1);
		odswiezKontrolki();
	}

	@Override
	protected void minusDay() {
		mData = mData.minusDays(1);
		odswiezKontrolki();
	}

	@Override
	protected void addDay() {
		mData = mData.plusDays(1);
		odswiezKontrolki();
	}

	@Override
	protected void addWorker() {
		OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().withCzyTrybSzybki(true).build();
		OPrzygListyPracWyjscie lvWynik = new OknoPrzygotowaniaListyPracownikow(lvParams).get();
		if (!lvWynik.isAccepted() || lvWynik.getLista().isEmpty())
			return;
		AbsencjaDTO lvAbsencja = new AbsencjaDTO();
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.NB;
		lvAbsencja.setOkres(new Interval(mData, mData));
		lvAbsencja.setProcent(SLEkwiwalentZaUrlop.PROCENT_100);
		lvAbsencja.setRodzaj(lvRodzajAbs);

		if (cbEditableAbsence.isSelected()) {
			OAbsencjiWejscie lvWejscie = OAbsencjiWejscie//
					.builder()//
					.withAbsencja(lvAbsencja)//
					.withListaPracownikow(lvWynik.getLista())//
					.build();

			new OknoAbsencji(lvWejscie).get();
		} else if (!mObsluga.saveAbsence(lvAbsencja, lvWynik.getLista()))
			err(WalidacjeTwarde.NachodzaNaSiebieOkresy, new String[0]);

		odswiezKontrolki();
	}
	@Override
	protected void deleteAbsence() {
		mObsluga.deleteAbsence(getSelectedAbsenceId());
		odswiezKontrolki();
	}

	@Override
	protected void modifyAbsence() {
		mObsluga.modifyAbsence(getSelectedAbsenceId().get(0));
		odswiezKontrolki();

	}

}
