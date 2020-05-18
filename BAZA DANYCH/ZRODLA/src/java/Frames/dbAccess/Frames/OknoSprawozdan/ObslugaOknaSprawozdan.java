package Frames.dbAccess.Frames.OknoSprawozdan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import ProjektGlowny.commons.Components.DatePicker;
import Wydruki.ListaPlac.ListaPlacWydruk;
import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.SprawozdanieMiesieczne.SprawozdanieMiesieczne;
import Wydruki.SprawozdanieRoczne.SprawozdanieRoczne;
import pl.home.components.frames.mainframes.OknoListyObecnosci;
import pl.home.components.frames.mainframes.OknoPrzygotowaniaListyPracownikow;
import pl.home.components.frames.mainframes.PobieranieModulow;
import pl.home.components.frames.parameters.ListaObecnosciWejscie;
import pl.home.components.frames.parameters.OPrzygListyPracWejscie;

public class ObslugaOknaSprawozdan {
	private DaneDoSprawozdaniaMiesiecznego mDane;
	private int mRok = 2019;

	public ObslugaOknaSprawozdan() {
		mDane = new DaneDoSprawozdaniaMiesiecznego();
	}

	public void sprawozdanieMiesieczne() {

		if (!przygotujDaneMiesieczne())
			return;
		new SprawozdanieMiesieczne(mDane);

	}

	public void sprawozdanieRoczne() {
		przygotujDaneRoczne(false);
		new SprawozdanieRoczne(mDane);

	}

	private void przygotujDaneRoczne(boolean pmB) {
		if (pmB) {
			List<SLRodzajeAbsencji> lvLista = new ArrayList<>();
			lvLista.add(SLRodzajeAbsencji.urlop_w_pracy);
			lvLista.add(SLRodzajeAbsencji.urlop_wypoczynkowy);
			mDane.setListaAbsencji(lvLista);
		} else {
			mDane.setListaAbsencji(new PobieranieModulow().get().getListaAbsencji());
		}
		OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().build();
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow());
		mDane.setOkresSprawozdawczy(SLMiesiace.N00_ROK.getOkres(mRok));
	}

	private boolean przygotujDaneMiesieczne() {
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return false;
		mDane.setOkresSprawozdawczy(SLMiesiace.values()[lvData.getMonthValue() - 1].getOkres(lvData.getYear()));
		OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().build();
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow());
		if (mDane.getListaPracownikow().size() == 0)
			return false;
		mDane.setListaAbsencji(new PobieranieModulow().get().getListaAbsencji());
		if (mDane.getListaAbsencji().size() == 0)
			return false;
		return true;
	}

	public void sprawozdanieRoczneUrlopy() {
		przygotujDaneRoczne(true);
		new SprawozdanieRoczne(mDane, true);

	}

	public void generujListeObecnosci() {
		new OknoListyObecnosci(new ListaObecnosciWejscie());
	}

	public void generujListePlac() {

		if (przygotujDaneDoListyPlac())
			new ListaPlacWydruk(mDane);

	}

	private boolean przygotujDaneDoListyPlac() {
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return false;
		mDane.setData(lvData);
		OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().build();
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow());
		if (mDane.getListaPracownikow().size() == 0)
			return false;
		return true;
	}
}
