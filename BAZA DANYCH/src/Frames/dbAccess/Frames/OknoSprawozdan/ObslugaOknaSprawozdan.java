package Frames.dbAccess.Frames.OknoSprawozdan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.DatePicker;
import Frames.dbAccess.Components.ScriptParams;
import Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow.OknoPrzygotowaniaListyPracownikow;
import Frames.dbAccess.Components.OknoPrzygotowniaLustyPracownikow.OknoPrzygotowaniaListyPracownikowParams;
import Frames.dbAccess.Components.PobieranieModulow.PobieranieModulow;
import Wydruki.ListaObecnosci.OknoListaObecnosci;
import Wydruki.ListaPlac.ListaPlacWydruk;
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
		ScriptParams lvParams = new ScriptParams();
		lvParams.add(OknoPrzygotowaniaListyPracownikowParams.NAZWA, "Wybierz pracowników");
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow());
		mDane.setOkresSprawozdawczy(SLMiesiace.N00_ROK.getOkres(mRok));
	}

	private boolean przygotujDaneMiesieczne() {
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return false;
		mDane.setOkresSprawozdawczy(SLMiesiace.values()[lvData.getMonthValue() - 1].getOkres(lvData.getYear()));
		ScriptParams lvParams = new ScriptParams();
		lvParams.add(OknoPrzygotowaniaListyPracownikowParams.NAZWA, "Wybierz pracowników");
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow());
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

		String[] lvArg = new String[0];
		OknoListaObecnosci.main(lvArg);

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
		ScriptParams lvParams = new ScriptParams();
		lvParams.add(OknoPrzygotowaniaListyPracownikowParams.NAZWA, "Wybierz pracowników");
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow());
		if (mDane.getListaPracownikow().size() == 0)
			return false;
		return true;
	}
}
