package Frames.dbAccess.Frames.OknoSprawozdan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.DatePicker;
import Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow.OknoPrzygotowaniaListyPracownikow;
import Frames.dbAccess.Components.PobieranieModulow.PobieranieModulow;
import Wydruki.ListaObecnosci.OknoListaObecnosci;
import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.SprawozdanieMiesieczne.SprawozdanieMiesieczne;
import Wydruki.SprawozdanieRoczne.SprawozdanieRoczne;

public class ObslugaOknaSprawozdan {
	DaneDoSprawozdaniaMiesiecznego mDane;
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
			mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
		}

		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow("Wybierz pracowników").WybierzPracownikow());
		mDane.setOkresSprawozdawczy(SLMiesiace.Rok.getOkres(mRok));
	}

	private boolean przygotujDaneMiesieczne() {
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return false;
		mDane.setOkresSprawozdawczy(SLMiesiace.values()[lvData.getMonthValue() - 1].getOkres(lvData.getYear()));
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow("Wybierz pracowników").WybierzPracownikow());
		if (mDane.getListaPracownikow().size() == 0)
			return false;
		mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
		if (mDane.getListaAbsencji().size() == 0)
			return false;
		return true;
	}

	public void sprawozdanieRoczneUrlopy() {
		przygotujDaneRoczne(true);
		new SprawozdanieRoczne(mDane, true);

	}

	public void generujListeObecnosci() {
		new OknoListaObecnosci().main(null);

	}
}
