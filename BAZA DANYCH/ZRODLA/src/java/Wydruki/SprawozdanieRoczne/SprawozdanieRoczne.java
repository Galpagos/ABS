package Wydruki.SprawozdanieRoczne;

import ProjektGlowny.commons.enums.SLMiesiace;
import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import Frames.dbAccess.Components.ResultTableWindow;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import Wydruki.SprawozdanieMiesieczne.SprawozdaniaRepository;
import Wydruki.SprawozdanieMiesieczne.wynikWResultTableWindow;
import enums.RodzajWydruku;
import enums.SLRodzajeAbsencji;
import pl.home.components.frames.mainframes.ReportsResult;
import pl.home.components.frames.parameters.ReportsResultIn;

public class SprawozdanieRoczne implements wynikWResultTableWindow {
	DefaultTableModel mModel = new DefaultTableModel();
	ResultTableWindow mOknoWyniku;
	private SprawozdaniaRepository mRepo;
	private DaneDoSprawozdaniaMiesiecznego mDane;
	private List<Interval> mDniWolneWRoku;
	private List<SLRodzajeAbsencji> mListaAbsWMiesiacu;

	private boolean mCzyWersjaRozszerzona;

	public SprawozdanieRoczne(DaneDoSprawozdaniaMiesiecznego pmDane) {
		mRepo = new SprawozdaniaRepository();
		mDane = pmDane;
		mDniWolneWRoku = utworzListeDniWolnych();
		mCzyWersjaRozszerzona = false;
		przygotujNaglowekMiesieczny();
		uzupelnijAbsencje();
		utworzWierszeTabeli();
		pokazResult();

	}

	public SprawozdanieRoczne(DaneDoSprawozdaniaMiesiecznego pmDane, boolean pmB) {
		mRepo = new SprawozdaniaRepository();
		mDane = pmDane;
		mDniWolneWRoku = utworzListeDniWolnych();
		mCzyWersjaRozszerzona = true;
		przygotujNaglowekMiesieczny();
		uzupelnijUrlopNalezny();
		uzupelnijAbsencje();

		utworzWierszeTabeli();
		pokazResult();
	}

	private void pokazResult() {

		ReportsResultIn lvParams = ReportsResultIn//
				.builder()//
				.dane(this)//
				.rodzajWydruku(RodzajWydruku.SPR_ROCZNE)//
				.model(mModel)//
				.tytul("Sprawozdanie za okres od " + mDane.getOkresSprawozdawczy().getStart() + " do " + mDane.getOkresSprawozdawczy().getEnd())//
				.build();

		new ReportsResult(lvParams).get();
	}

	private void utworzWierszeTabeli() {
		int lvIloscKolumn;
		if (mCzyWersjaRozszerzona) {
			lvIloscKolumn = 16;

		} else {
			lvIloscKolumn = 14;
		}

		for (

		PracownikDTO lvPrac : mDane.getListaPracownikow()) {
			Object[] lvRekord = new Object[lvIloscKolumn];
			lvRekord[0] = lvPrac.getNazwa();
			lvRekord[13] = "Suma";
			for (int i = 1; i <= 12; i++) {
				lvRekord[i] = przeliczKomorke(lvPrac.getListaAbsencji(), i);
			}

			uzupelnijPoleSuma(lvRekord);
			if (mCzyWersjaRozszerzona) {
				lvRekord[14] = lvPrac.getUrlopNalezny();
				Integer lvRoznica = Integer.parseInt((String) lvRekord[14]) - (Integer) lvRekord[13];
				lvRekord[15] = lvRoznica;
			}
			mModel.addRow(lvRekord);
		}

	}

	private void uzupelnijPoleSuma(Object[] pmRekord) {
		int lvSuma = 0;
		for (int i = 1; i <= 12; i++) {
			lvSuma = lvSuma + Integer.valueOf(pmRekord[i].toString().split("<X>")[0]);

		}
		pmRekord[13] = lvSuma;

	}

	private KomorkaSprawozdaniaRocznegoDTO przeliczKomorke(List<AbsencjaDTO> pmList, int pmI) {
		mListaAbsWMiesiacu = null;
		mListaAbsWMiesiacu = new ArrayList<SLRodzajeAbsencji>();

		for (AbsencjaDTO lvAbs : pmList) {
			AbsencjaDTO lvAbsWMiesiacu = null;
			lvAbsWMiesiacu = new AbsencjaDTO();

			lvAbsWMiesiacu.setRodzaj(lvAbs.getRodzaj());
			lvAbsWMiesiacu.setProcent(lvAbs.getProcent());
			Interval lvNowyOkres = lvAbs.getOkres().overlap(new Interval(pmI, mDane.getOkresSprawozdawczy().getEnd().getYear())).orElse(null);

			if (lvNowyOkres != null) {
				lvAbsWMiesiacu.setOkres(lvNowyOkres);
				policzDniRobocze(lvAbsWMiesiacu);
			}

		}
		KomorkaSprawozdaniaRocznegoDTO lvDane = new KomorkaSprawozdaniaRocznegoDTO();
		lvDane.setMapa(mListaAbsWMiesiacu.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

		return lvDane;// TODO Auto-generated method stub

	}

	private void policzDniRobocze(AbsencjaDTO pmAbsWMiesiacu) {
		LocalDate date = pmAbsWMiesiacu.getOkres().getStart();
		LocalDate stop = pmAbsWMiesiacu.getOkres().getEnd();
		while (date.isBefore(stop) || date.isEqual(stop)) {
			if (!DayOfWeek.SUNDAY.equals(date.getDayOfWeek()) && !DayOfWeek.SATURDAY.equals(date.getDayOfWeek())) { // If
																													// not
																													// weekend,
																													// collect
																													// this
																													// LocalDate.
				if (!dzienWolny(date, pmAbsWMiesiacu.getOkres())) {
					mListaAbsWMiesiacu.add(pmAbsWMiesiacu.getRodzaj());
				}
			}
			// Prepare for next loop.
			date = date.plusDays(1); // Increment to next day.
		}
	}

	private boolean dzienWolny(LocalDate pmDate, Interval pmNowyOkres) {
		for (Interval lvDzien : mDniWolneWRoku) {
			Interval lvSprawdz = lvDzien.overlap(pmNowyOkres).orElse(null);
			if (lvSprawdz != null) {
				for (LocalDate lvDay = lvSprawdz.getStart(); lvDay.isBefore(lvSprawdz.getEnd())
						|| lvDay.isEqual(lvSprawdz.getEnd()); lvDay = lvDay.plusDays(1)) {
					if (lvDay.equals(pmDate))
						return true;
				}
			}
		}
		return false;
	}

	private void uzupelnijAbsencje() {
		for (PracownikDTO lvPrac : mDane.getListaPracownikow()) {
			lvPrac.setListaAbsencji(mRepo.getAbsencjeDlaPracownika(lvPrac.getId(), mDane.getListaAbsencji()));
		}
	}

	private void uzupelnijUrlopNalezny() {
		for (PracownikDTO lvPrac : mDane.getListaPracownikow()) {
			lvPrac.setUrlopNalezny(mRepo.getUrlopNalezny(lvPrac.getId()));
		}
	}

	private void przygotujNaglowekMiesieczny() {
		mModel.addColumn("Pracownik");

		for (int i = 0; i < 12; i++)
			mModel.addColumn(SLMiesiace.values()[i].getNazwa());

		mModel.addColumn("Wykorzystano");
		if (mCzyWersjaRozszerzona) {
			mModel.addColumn("Należny Urlop");
			mModel.addColumn("Różnica");
		}
	}

	private List<Interval> utworzListeDniWolnych() {
		return mRepo.getDniWolne();
	}

	@Override
	public Object[] przeliczWierszTabeli(PracownikDTO pmPrac) {
		uzupelnijAbsencje();
		int lvIloscKolumn;
		if (mCzyWersjaRozszerzona) {
			lvIloscKolumn = 16;

		} else {
			lvIloscKolumn = 14;
		}

		Object[] lvRekord = new Object[lvIloscKolumn];
		lvRekord[0] = pmPrac.getNazwa();
		lvRekord[13] = "Suma";
		for (int i = 1; i <= 12; i++) {
			lvRekord[i] = przeliczKomorke(pmPrac.getListaAbsencji(), i);
		}

		uzupelnijPoleSuma(lvRekord);
		if (mCzyWersjaRozszerzona) {
			lvRekord[14] = pmPrac.getUrlopNalezny();
			Integer lvRoznica = Integer.parseInt((String) lvRekord[14]) - (Integer) lvRekord[13];
			lvRekord[15] = lvRoznica;
		}
		return lvRekord;
	}

}
