package dbAccess.Frames.OknoSprawozdan;

import java.time.LocalDate;

import org.joda.time.DateTime;
import org.joda.time.Interval;

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
		przygotujDaneRoczne();
		new SprawozdanieRoczne(mDane);

	}

	private void przygotujDaneRoczne()
	{
		mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow().WybierzPracownikow());
		DateTime lvStart = new DateTime().withMonthOfYear(1).withDayOfMonth(1).withYear(mRok).withTimeAtStartOfDay();
		DateTime lvEnd = new DateTime().withMonthOfYear(12).withDayOfMonth(31).withYear(mRok).withTimeAtStartOfDay();
		mDane.setOkresSprawozdawczy(new Interval(lvStart, lvEnd));
		System.out.println(lvStart);
		System.out.println(mDane.getOkresSprawozdawczy());

	}

	private void przygotujDaneMiesieczne()
	{
		LocalDate lvData = new DatePicker().setPickedLocalDate();
		if (lvData == null)
			return;
		DateTime lvStart = DateTime.parse(lvData.minusDays(lvData.getDayOfMonth() - 1).toString());
		DateTime lvEnd = DateTime.parse(lvData.plusMonths(1).minusDays(lvData.getDayOfMonth()).toString());
		mDane.setOkresSprawozdawczy(new Interval(lvStart, lvEnd));
		mDane.setListaPracownikow(new OknoPrzygotowaniaListyPracownikow().WybierzPracownikow());
		mDane.setListaAbsencji(new PobieranieModulow().ZwrocModuly());
	}
}
