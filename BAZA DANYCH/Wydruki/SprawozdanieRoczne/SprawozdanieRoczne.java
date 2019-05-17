package SprawozdanieRoczne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import Datownik.JodaTime;
import Enums.SLMiesiace;
import Enums.SLRodzajeAbsencji;
import PrzygotowanieDanych.AbsencjaDTO;
import PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import PrzygotowanieDanych.PracownikDTO;
import SprawozdanieMiesieczne.SprawozdaniaRepository;
import dbAccess.Components.ResultTableWindow;

public class SprawozdanieRoczne
{
	DefaultTableModel mModel = new DefaultTableModel();
	ResultTableWindow mOknoWyniku;
	private SprawozdaniaRepository mRepo;
	private DaneDoSprawozdaniaMiesiecznego mDane;
	private int mRok = 2019;
	private List<Interval> mDniWolneWRoku;
	private List<SLRodzajeAbsencji> mListaAbsWMiesiacu;

	public SprawozdanieRoczne(DaneDoSprawozdaniaMiesiecznego pmDane)
	{
		mRepo = new SprawozdaniaRepository();
		mDane = pmDane;
		mDniWolneWRoku = utworzListeDniWolnych();
		przygotujNaglowekMiesieczny();
		uzupelnijAbsencje();
		utworzWierszeTabeli();
		pokazResult();

	}

	private void pokazResult()
	{
		mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new SprawozdanieRoczneCellRender());
		mOknoWyniku.setTytul("Sprawozdanie za okres od " + mDane.getOkresSprawozdawczy().getStart().toLocalDate()
				+ " do " + mDane.getOkresSprawozdawczy().getEnd().toLocalDate());
		mOknoWyniku.pokazWynik();
	}

	private void utworzWierszeTabeli()
	{
		for (PracownikDTO lvPrac : mDane.getListaPracownikow())
		{
			Object[] lvRekord = new Object[14];
			lvRekord[0] = lvPrac.getNazwa();
			lvRekord[13] = "Suma";
			for (int i = 1; i <= 12; i++)
			{
				lvRekord[i] = przeliczKomorke(lvPrac.getListaAbsencji(), i);
			}
			sumujPole13(lvRekord);

			mModel.addRow(lvRekord);
		}

	}

	private void sumujPole13(Object[] pmRekord)
	{
		int lvSuma = 0;
		for (int i = 1; i <= 12; i++)
		{
			lvSuma = lvSuma + Integer.valueOf(pmRekord[i].toString().split("<X>")[0]);

		}
		pmRekord[13] = lvSuma;

	}

	private KomorkaSprawozdaniaRocznegoDTO przeliczKomorke(List<AbsencjaDTO> pmList, int pmI)
	{
		mListaAbsWMiesiacu = null;
		mListaAbsWMiesiacu = new ArrayList<SLRodzajeAbsencji>();

		for (AbsencjaDTO lvAbs : pmList)
		{
			AbsencjaDTO lvAbsWMiesiacu = null;
			lvAbsWMiesiacu = new AbsencjaDTO();

			lvAbsWMiesiacu.setRodzaj(lvAbs.getRodzaj());
			Interval lvNowyOkres = lvAbs.getOkres().overlap(JodaTime.okresMiesiac(pmI, mRok));

			if (lvNowyOkres != null)
			{
				lvAbsWMiesiacu.setOkres(lvNowyOkres);
				policzDniRobocze(lvAbsWMiesiacu);
			}

		}
		KomorkaSprawozdaniaRocznegoDTO lvDane = new KomorkaSprawozdaniaRocznegoDTO();
		lvDane.setMapa(
				mListaAbsWMiesiacu.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

		return lvDane;// TODO Auto-generated method stub

	}

	private void policzDniRobocze(AbsencjaDTO pmAbsWMiesiacu)
	{
		LocalDate date = pmAbsWMiesiacu.getOkres().getStart().toLocalDate();
		LocalDate stop = pmAbsWMiesiacu.getOkres().getEnd().toLocalDate();
		while (date.isBefore(stop) || date.isEqual(stop))
		{
			if (date.getDayOfWeek() != 6 && date.getDayOfWeek() != 7)
			{ // If not weekend, collect this LocalDate.
				if (!dzienWolny(date, pmAbsWMiesiacu.getOkres()))
				{
					mListaAbsWMiesiacu.add(pmAbsWMiesiacu.getRodzaj());
				}
			}
			// Prepare for next loop.
			date = date.plusDays(1); // Increment to next day.
		}
	}

	private boolean dzienWolny(LocalDate pmDate, Interval pmNowyOkres)
	{
		for (Interval lvDzien : mDniWolneWRoku)
		{
			Interval lvSprawdz = lvDzien.overlap(pmNowyOkres);
			if (lvSprawdz != null)
			{
				for (LocalDate lvDay = lvSprawdz.getStart().toLocalDate(); lvDay
						.isBefore(lvSprawdz.getEnd().toLocalDate())
						|| lvDay.isEqual(lvSprawdz.getEnd().toLocalDate()); lvDay = lvDay.plusDays(1))
				{
					if (lvDay.equals(pmDate))
						return true;
				}
			}
		}
		return false;
	}

	private void uzupelnijAbsencje()
	{
		for (PracownikDTO lvPrac : mDane.getListaPracownikow())
		{
			List<AbsencjaDTO> lvListaAbs = new ArrayList<>();
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

		for (int i = 0; i < 12; i++)
			mModel.addColumn(SLMiesiace.values()[i].toString());
		mModel.addColumn("Suma");

	}

	private List<Interval> utworzListeDniWolnych()
	{
		List<Object[]> lvLista = Arrays.asList(mRepo.getDniWolne());
		List<Interval> lvDniWolne = new ArrayList<Interval>();
		for (Object[] lvElem : lvLista)
		{
			DateTime lvDT = DateTime.parse(Datownik.LicznikDaty.LDTparseFromObject(lvElem[0]).toString());
			Interval lvInterval = new Interval(lvDT, new Duration(1000));
			lvDniWolne.add(lvInterval);
		}
		return lvDniWolne;

	}

}
