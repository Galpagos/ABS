package Wydruki.SprawozdanieMiesieczne;

import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.List;

import java.time.DayOfWeek;
import java.time.YearMonth;

import javax.swing.table.DefaultTableModel;

import Frames.dbAccess.Components.ResultTableWindow;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.RodzajWydruku;
import pl.home.absencje.ObslugaAbsencji;
import pl.home.components.frames.mainframes.ReportsResult;
import pl.home.components.frames.parameters.ReportsResultIn;

public class SprawozdanieMiesieczne implements wynikWResultTableWindow {
	DefaultTableModel mModel = new DefaultTableModel();
	DaneDoSprawozdaniaMiesiecznego mDane;
	ResultTableWindow mOknoWyniku;
	SprawozdaniaRepository mRepo;
	List<DniWolneDTO> mListaDniWolnych;
	int mLiczbaDniWMiesiacu;
	private ObslugaAbsencji mObslugaAbs;

	public SprawozdanieMiesieczne(DaneDoSprawozdaniaMiesiecznego pmDane) {
		mRepo = new SprawozdaniaRepository();
		mObslugaAbs = new ObslugaAbsencji();
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

	private void utworzWierszeTabeli() {
		for (PracownikDTO lvPrac : mDane.getListaPracownikow()) {
			Object[] lvRekord = new Object[mLiczbaDniWMiesiacu + 1];
			uwzglednijDniWolneiWeekendy(lvRekord);
			uwzglednijAbsencjePracowniow(lvRekord, lvPrac);
			mModel.addRow(lvRekord);
		}
	}

	@Override
	public Object[] przeliczWierszTabeli(PracownikDTO lvPrac) {
		lvPrac.setListaAbsencji(mObslugaAbs.getAbsencjePracownika(lvPrac.getId()));
		if (lvPrac.getListaAbsencji() != null) {
			for (AbsencjaDTO lvAbs : lvPrac.getListaAbsencji()) {
				Interval lvNowyOkres = lvAbs.getOkres().overlap(mDane.getOkresSprawozdawczy()).orElse(null);
				lvAbs.setOkres(lvNowyOkres);
			}
		}

		Object[] lvRekord = new Object[mLiczbaDniWMiesiacu + 1];
		uwzglednijDniWolneiWeekendy(lvRekord);
		uwzglednijAbsencjePracowniow(lvRekord, lvPrac);
		return lvRekord;
	}

	private void uwzglednijDniWolneiWeekendy(Object[] pmRekord) {
		if (mListaDniWolnych != null) {
			for (DniWolneDTO lvDzien : mListaDniWolnych) {
				pmRekord[lvDzien.getDzienWMiesiacu()] = lvDzien.getPowod();
			}
		}
	}

	private void utworzListeWeekendow() {
		DayOfWeek lvDzien = mDane.getOkresSprawozdawczy().getStart().getDayOfWeek();
		for (int i = 0; i < mLiczbaDniWMiesiacu; i++) {
			if (DayOfWeek.SATURDAY.equals(lvDzien.plus(i))) {
				DniWolneDTO lvDzien1 = new DniWolneDTO();
				lvDzien1.setDzienWMiesiacu(i + 1);
				lvDzien1.setPowod("Sobota");
				mListaDniWolnych.add(lvDzien1);
			}
			if (DayOfWeek.SUNDAY.equals(lvDzien.plus(i))) {
				DniWolneDTO lvDzien1 = new DniWolneDTO();
				lvDzien1.setDzienWMiesiacu(i + 1);
				lvDzien1.setPowod("Niedziela");
				mListaDniWolnych.add(lvDzien1);
			}
		}

	}

	private void utworzListeDniWolnych() {
		List<Interval> lvDniWolne = mRepo.getDniWolne();

		for (Interval lvOkres : lvDniWolne) {

			Interval lvNowyOkres = lvOkres.overlap(mDane.getOkresSprawozdawczy()).orElse(null);
			if (lvNowyOkres != null) {
				DniWolneDTO lvDzienWolny = new DniWolneDTO();
				lvDzienWolny.setDzienWMiesiacu(lvNowyOkres.getStart().getDayOfMonth());
				lvDzienWolny.setPowod("DW");
				mListaDniWolnych.add(lvDzienWolny);

			}
		}
	}

	private void uwzglednijAbsencjePracowniow(Object[] pmRekord, PracownikDTO pmPrac) {
		pmRekord[0] = pmPrac;

		for (AbsencjaDTO lvAbs : pmPrac.getListaAbsencji()) {
			if (lvAbs.getOkres() != null) {
				for (int i = lvAbs.getOkres().getStart().getDayOfMonth(); i <= lvAbs.getOkres().getEnd().getDayOfMonth(); i++)
					pmRekord[i] = lvAbs;// .getRodzaj().toString();

			}
		}

	}

	private void filtrujAbsencjePoOkresie() {
		for (PracownikDTO lvPrac : mDane.getListaPracownikow()) {
			if (lvPrac.getListaAbsencji() != null) {
				for (AbsencjaDTO lvAbs : lvPrac.getListaAbsencji()) {
					Interval lvNowyOkres = lvAbs.getOkres().overlap(mDane.getOkresSprawozdawczy()).orElse(null);
					lvAbs.setOkres(lvNowyOkres);
				}
			}
		}

	}

	private void uzupelnijAbsencje() {
		for (PracownikDTO lvPrac : mDane.getListaPracownikow()) {
			lvPrac.setListaAbsencji(mRepo.getAbsencjeDlaPracownika(lvPrac.getId(), mDane.getListaAbsencji()));
		}

	}

	private void przygotujNaglowekMiesieczny() {
		mModel.addColumn("Pracownik");

		YearMonth yearMonthObject = YearMonth.of(mDane.getOkresSprawozdawczy().getStart().getYear(), mDane.getOkresSprawozdawczy().getStart().getMonth());
		mLiczbaDniWMiesiacu = yearMonthObject.lengthOfMonth();

		for (int i = 1; i <= mLiczbaDniWMiesiacu; i++)
			mModel.addColumn("" + i);
	}

	public void pokazResult() {

		ReportsResultIn lvParams = ReportsResultIn//
				.builder()//
				.dane(this)//
				.model(mModel)//
				.rodzajWydruku(RodzajWydruku.SPR_MIESIECZNE)//
				.tytul("Sprawozdanie za okres od " + mDane.getOkresSprawozdawczy().getStart() + " do " + mDane.getOkresSprawozdawczy().getEnd())//
				.build();

		new ReportsResult(lvParams).get();

	}
}
