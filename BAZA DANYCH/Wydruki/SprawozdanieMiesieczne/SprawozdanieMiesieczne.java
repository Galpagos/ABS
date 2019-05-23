package SprawozdanieMiesieczne;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import Enums.SLRodzajeAbsencji;
import PrzygotowanieDanych.AbsencjaDTO;
import PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import PrzygotowanieDanych.PracownikDTO;
import PrzygotowanieDanych.SprawozdanieMiesieczneCellRender;
import dbAccess.Components.ResultTableWindow;

public class SprawozdanieMiesieczne
{
	DefaultTableModel mModel = new DefaultTableModel();
	DaneDoSprawozdaniaMiesiecznego mDane;
	ResultTableWindow mOknoWyniku;
	SprawozdaniaRepository mRepo;
	List<DniWolneDTO> mListaDniWolnych;
	int mLiczbaDniWMiesiacu;

	public SprawozdanieMiesieczne(DaneDoSprawozdaniaMiesiecznego pmDane)
	{
		mRepo = new SprawozdaniaRepository();
		mListaDniWolnych = new ArrayList<DniWolneDTO>();
		mDane = pmDane;
		przygotujNaglowekMiesieczny();
		utworzListeDniWolnych();
		utworzListeWeekendow();
		uzupelnijAbsencje();
		filtrujAbsencjePoOkresie();
		utworzWierszeTabeli();
		pokazResult();

	}

	private void utworzWierszeTabeli()
	{

		for (PracownikDTO lvPrac : mDane.getListaPracownikow())
		{
			Object[] lvRekord = new Object[mLiczbaDniWMiesiacu + 1];
			uwzglednijDniWolneiWeekendy(lvRekord);
			uwzglednijAbsencjePracowniow(lvRekord, lvPrac);
			mModel.addRow(lvRekord);
		}

	}

	private void uwzglednijDniWolneiWeekendy(Object[] pmRekord)
	{
		if (mListaDniWolnych != null)
		{
			for (DniWolneDTO lvDzien : mListaDniWolnych)
			{
				pmRekord[lvDzien.getDzienWMiesiacu()] = lvDzien.getPowod();
			}
		}
	}

	private void utworzListeWeekendow()
	{
		int lvDzien = mDane.getOkresSprawozdawczy().getStart().getDayOfWeek();
		for (int i = 0; i < mLiczbaDniWMiesiacu; i++)
		{
			if ((lvDzien + i) % 7 == 6)
			{
				DniWolneDTO lvDzien1 = new DniWolneDTO();
				lvDzien1.setDzienWMiesiacu(i + 1);
				lvDzien1.setPowod("Sobota");
				mListaDniWolnych.add(lvDzien1);
			}
			if ((lvDzien + i) % 7 == 0)
			{
				DniWolneDTO lvDzien1 = new DniWolneDTO();
				lvDzien1.setDzienWMiesiacu(i + 1);
				lvDzien1.setPowod("Niedziela");
				mListaDniWolnych.add(lvDzien1);
			}
		}

	}

	private void utworzListeDniWolnych()
	{
		List<Object[]> lvLista = Arrays.asList(mRepo.getDniWolne());
		List<Interval> lvDniWolne = new ArrayList<Interval>();
		for (Object[] lvElem : lvLista)
		{
			DateTime lvDT = DateTime.parse(Datownik.LicznikDaty.LDTparseFromObject(lvElem[0]).toString());
			Interval lvInterval = new Interval(lvDT, new Duration(1000));
			lvDniWolne.add(lvInterval);
		}

		for (Interval lvOkres : lvDniWolne)
		{

			Interval lvNowyOkres = lvOkres.overlap(mDane.getOkresSprawozdawczy());
			if (lvNowyOkres != null)
			{
				DniWolneDTO lvDzienWolny = new DniWolneDTO();
				lvDzienWolny.setDzienWMiesiacu(lvNowyOkres.getStart().getDayOfMonth());
				lvDzienWolny.setPowod("DW");
				mListaDniWolnych.add(lvDzienWolny);

			}
		}
	}

	private void uwzglednijAbsencjePracowniow(Object[] pmRekord, PracownikDTO pmPrac)
	{
		pmRekord[0] = pmPrac.getNazwa();

		for (AbsencjaDTO lvAbs : pmPrac.getListaAbsencji())
		{
			if (lvAbs.getOkres() != null)
			{
				for (int i = lvAbs.getOkres().getStart().getDayOfMonth(); i <= lvAbs.getOkres().getEnd()
						.getDayOfMonth(); i++)
					pmRekord[i] = lvAbs.getRodzaj().toString();

			}
		}

	}

	private void filtrujAbsencjePoOkresie()
	{
		for (PracownikDTO lvPrac : mDane.getListaPracownikow())
		{
			if (lvPrac.getListaAbsencji() != null)
			{
				for (AbsencjaDTO lvAbs : lvPrac.getListaAbsencji())
				{
					Interval lvNowyOkres = lvAbs.getOkres().overlap(mDane.getOkresSprawozdawczy());
					lvAbs.setOkres(lvNowyOkres);
				}
			}
		}

	}

	private void uzupelnijAbsencje()
	{
		for (PracownikDTO lvPrac : mDane.getListaPracownikow())
		{
			List<AbsencjaDTO> lvListaAbs = new ArrayList<AbsencjaDTO>();
			Object[][] lvDanePracownika = mRepo.getAbsencjeDlaPracownika(lvPrac.getId(), mDane.getListaAbsencji());
			for (int i = 0; i < lvDanePracownika.length; i++)
			{
				AbsencjaDTO lvAbs = new AbsencjaDTO();
				lvAbs.setId((int) lvDanePracownika[i][0]);
				lvAbs.setIdPracownika((int) lvDanePracownika[i][1]);
				lvAbs.setNazwaPracownika(lvPrac.getNazwa());
				lvAbs.setRodzaj(SLRodzajeAbsencji.AbsencjaPoNazwie((String) lvDanePracownika[i][4]));
				lvAbs.setOkres(Datownik.LicznikDaty.OkreszBazy(lvDanePracownika[i][2], lvDanePracownika[i][3]));
				
				lvListaAbs.add(lvAbs);
			}
			lvPrac.setListaAbsencji(lvListaAbs);
		}

	}

	private void przygotujNaglowekMiesieczny()
	{
		mModel.addColumn("Pracownik");

		YearMonth yearMonthObject = YearMonth.of(mDane.getOkresSprawozdawczy().getStart().getYear(),
				mDane.getOkresSprawozdawczy().getStart().getMonthOfYear());
		mLiczbaDniWMiesiacu = yearMonthObject.lengthOfMonth();

		for (int i = 1; i <= mLiczbaDniWMiesiacu; i++)
			mModel.addColumn("" + i);
	}

	public void pokazResult()
	{
		mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new SprawozdanieMiesieczneCellRender());
		mOknoWyniku.setTytul("Sprawozdanie za okres od " + mDane.getOkresSprawozdawczy().getStart().toLocalDate()
				+ " do " + mDane.getOkresSprawozdawczy().getEnd().toLocalDate());
		mOknoWyniku.pokazWynik();
	}
}
