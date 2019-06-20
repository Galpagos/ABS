package dbAccess.Frames.OknoSprawozdan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import SprawozdanieMiesieczne.SprawozdanieMiesieczne;
import SprawozdanieRoczne.SprawozdanieRoczne;
import dbAccess.Components.DatePicker;
import dbAccess.Components.OknoPrzygotowniaLustyPracownikow.OknoPrzygotowaniaListyPracownikow;
import dbAccess.Components.PobieranieModulow.PobieranieModulow;

public class ObslugaOknaSprawozdan
{
	DaneDoSprawozdaniaMiesiecznego mDane;
	private int mRok = 2019;

	public ObslugaOknaSprawozdan()
	{
		mDane = new DaneDoSprawozdaniaMiesiecznego();
	}

	public void sprawozdanieMiesieczne()
	{
		przygotujDaneMiesieczne();
		if (mDane.getListaAbsencji() == null)
			return;
		new SprawozdanieMiesieczne(mDane);

	}

	public void sprawozdanieRoczne()
	{
		przygotujDaneRoczne(false);
		new SprawozdanieRoczne(mDane);

	}

	private void przygotujDaneRoczne(boolean pmB)
	{
		if (pmB)
		{
			List<SLRodzajeAbsencji> lvLista = new ArrayList<>();
			lvLista.add(SLRodzajeAbsencji.urlop_w_pracy);
			lvLista.add(SLRodzajeAbsencji.urlop_wypoczynkowy);
			mDane.setListaAbsencji(lvLista);
		} else
		{
			mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
		}

		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow().WybierzPracownikow());
		mDane.setOkresSprawozdawczy(SLMiesiace.Rok.getOkres(mRok));
	}

	private void przygotujDaneMiesieczne()
	{
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return;
		mDane.setOkresSprawozdawczy(SLMiesiace.values()[lvData.getMonthValue() - 1].getOkres(lvData.getYear()));
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow().WybierzPracownikow());
		mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
	}

	public void sprawozdanieRoczneUrlopy()
	{
		przygotujDaneRoczne(true);
		new SprawozdanieRoczne(mDane, true);

	}
}
