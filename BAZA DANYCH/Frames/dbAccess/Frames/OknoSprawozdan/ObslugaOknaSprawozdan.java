package dbAccess.Frames.OknoSprawozdan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

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
		DateTime lvStart = new DateTime().withMonthOfYear(1).withDayOfMonth(1).withYear(mRok).withTimeAtStartOfDay();
		DateTime lvEnd = new DateTime().withMonthOfYear(12).withDayOfMonth(31).withYear(mRok).withTimeAtStartOfDay()
				.plusHours(2);
		mDane.setOkresSprawozdawczy(new Interval(lvStart, lvEnd));
	}

	private void przygotujDaneMiesieczne()
	{
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return;
		DateTime lvStart = DateTime.parse(lvData.minusDays(lvData.getDayOfMonth() - 1).toString());
		DateTime lvEnd = DateTime.parse(lvData.plusMonths(1).minusDays(lvData.getDayOfMonth()).toString()).plusHours(2);
		mDane.setOkresSprawozdawczy(new Interval(lvStart, lvEnd));
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow().WybierzPracownikow());
		mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
	}

	public void sprawozdanieRoczneUrlopy()
	{
		przygotujDaneRoczne(true);
		new SprawozdanieRoczne(mDane, true);

	}
}
