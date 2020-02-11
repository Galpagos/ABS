package Wydruki.SprawozdanieMiesieczne;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Absencja.ObslugaAbsencji;
import Datownik.Interval;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.ResultTableWindow;
import Pracownik.ObslugaPracownka;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.DaneDoSprawozdaniaMiesiecznego;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class SprawozdanieMiesieczne implements wynikWResultTableWindow {
	DefaultTableModel mModel = new DefaultTableModel();
	DaneDoSprawozdaniaMiesiecznego mDane;
	ResultTableWindow mOknoWyniku;
	SprawozdaniaRepository mRepo;
	List<DniWolneDTO> mListaDniWolnych;
	int mLiczbaDniWMiesiacu;

	public SprawozdanieMiesieczne(DaneDoSprawozdaniaMiesiecznego pmDane) {
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
		ObslugaAbsencji lvObsluga = new ObslugaAbsencji();
		lvPrac.setListaAbsencji(lvObsluga.pobierzAbsencjePracownika(lvPrac.getId()));
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
		List<Object[]> lvLista = Arrays.asList(mRepo.getDniWolne());
		List<Interval> lvDniWolne = new ArrayList<Interval>();
		for (Object[] lvElem : lvLista) {
			Interval lvInterval = Interval.OkreszBazy(lvElem[0], lvElem[0]);
			lvDniWolne.add(lvInterval);
		}

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
				for (int i = lvAbs.getOkres().getStart().getDayOfMonth(); i <= lvAbs.getOkres().getEnd()
						.getDayOfMonth(); i++)
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
			List<AbsencjaDTO> lvListaAbs = new ArrayList<>();
			Object[][] lvDanePracownika = mRepo.getAbsencjeDlaPracownika(lvPrac.getId(), mDane.getListaAbsencji());
			for (int i = 0; i < lvDanePracownika.length; i++) {
				AbsencjaDTO lvAbs = new AbsencjaDTO();
				lvAbs.setId((int) lvDanePracownika[i][0]);
				lvAbs.setIdPracownika((int) lvDanePracownika[i][1]);
				lvAbs.setNazwaPracownika(lvPrac.getNazwa());
				lvAbs.setRodzaj(SLRodzajeAbsencji.getByKod((String) lvDanePracownika[i][4]));
				;
				lvAbs.setOkres(new Interval((Timestamp) lvDanePracownika[i][2], (Timestamp) lvDanePracownika[i][3]));
				lvListaAbs.add(lvAbs);
			}
			lvPrac.setListaAbsencji(lvListaAbs);
		}

	}

	private void przygotujNaglowekMiesieczny() {
		mModel.addColumn("Pracownik");

		YearMonth yearMonthObject = YearMonth.of(mDane.getOkresSprawozdawczy().getStart().getYear(),
				mDane.getOkresSprawozdawczy().getStart().getMonth());
		mLiczbaDniWMiesiacu = yearMonthObject.lengthOfMonth();

		for (int i = 1; i <= mLiczbaDniWMiesiacu; i++)
			mModel.addColumn("" + i);
	}

	public void pokazResult() {
		mOknoWyniku = new ResultTableWindow();
		mOknoWyniku.setDane(this);
		mOknoWyniku.ustawTabele(mModel);
		mOknoWyniku.getMtable().setDefaultRenderer(Object.class, new SprawozdanieMiesieczneCellRender());
		mOknoWyniku.getMtable().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent pmE) {
				JTable target = (JTable) pmE.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();

				if ((target.getValueAt(row, column) != null)
						&& target.getValueAt(row, column).getClass() == PracownikDTO.class) {
					PracownikDTO lvPracownik = (PracownikDTO) target.getValueAt(row, column);
					new ObslugaPracownka().pokazPracownika(lvPracownik);
					int k = target.getModel().getColumnCount();
					Object[] lvPrzeliczonyWiersz = przeliczWierszTabeli(lvPracownik);
					for (int i = 0; i < k; i++) {
						target.getModel().setValueAt(lvPrzeliczonyWiersz[i], row, i);
					}
				}
				if ((target.getValueAt(row, column) != null)
						&& target.getValueAt(row, column).getClass() == AbsencjaDTO.class) {
					PracownikDTO lvPracownik = (PracownikDTO) target.getValueAt(row, 0);
					new ObslugaPracownka().pokazPracownika(lvPracownik);
					int k = target.getModel().getColumnCount();
					Object[] lvPrzeliczonyWiersz = przeliczWierszTabeli(lvPracownik);
					for (int i = 0; i < k; i++) {
						target.getModel().setValueAt(lvPrzeliczonyWiersz[i], row, i);
					}
					mOknoWyniku.repaint();
				}
			}
		});
		mOknoWyniku.setTytul("Sprawozdanie za okres od " + mDane.getOkresSprawozdawczy().getStart() + " do "
				+ mDane.getOkresSprawozdawczy().getEnd());
		mOknoWyniku.pokazWynik();

	}
}
