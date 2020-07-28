package pl.home.components.frames.mainframes;

import ProjektGlowny.commons.utils.Data;
import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import Absencja.ObslugaAbsencji;
import Absencja.WalidatorAbsenci;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.src.SrcOknoAbsencji;

public class OknoAbsencji extends SrcOknoAbsencji {

	private static final long serialVersionUID = 1L;
	private AbsencjaDTO mAbsencja;
	private ObslugaAbsencji mObslugaAbsencji;
	private WalidatorAbsenci mWalidator;
	private List<PracownikDTO> mListaPracownikow;

	public OknoAbsencji(OAbsencjiWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void readParams() {
		mAbsencja = mParamsIn.getAbsencja();
		mListaPracownikow = mParamsIn.getListaPracownikow();
	}

	@Override
	protected void budujOkno() {
		super.budujOkno();
		mObslugaAbsencji = new ObslugaAbsencji();
		mWalidator = new WalidatorAbsenci();
	}

	@Override
	protected void odswiezKontrolki() {

		super.odswiezKontrolki();
		cbRodzajAbsencji.setSelectedItem(mAbsencja.getRodzaj());
		cbProcent.setSelectedItem(mAbsencja.getProcent());
		mDataOd.setValue(Data.DateFromLocalDate(mAbsencja.getOkres().getStart()));
		mDataDo.setValue(Data.DateFromLocalDate(mAbsencja.getOkres().getEnd()));
	}

	@Override
	public void zapiszAbsencje() {
		List<AbsencjaDTO> lvNoweAbsencje = budujAbsencje();
		for (int i = 0; i < lvNoweAbsencje.size(); i++) {
			AbsencjaDTO lvAbsencja = lvNoweAbsencje.get(i);

			Optional<WalidacjeTwarde> lvWalidacja = mWalidator.czyWystepujeAbsencjaWOkresie(lvAbsencja);
			if (lvNoweAbsencje.size() == 1 && lvWalidacja.isPresent()) {
				{
					lvWalidacja.get().pokaz();
					return;
				}
			} else if (lvWalidacja.isPresent()) {

				JOptionPane.showMessageDialog(null, "Jeden z pracowników ma już wprowadzoną absencje na ten dzień");
				continue;
			}

			AbsencjaDTO lvUsuwana = mObslugaAbsencji.pobierzAbsencjePoId(mAbsencja.getId());
			mObslugaAbsencji.usunAbsencje(mAbsencja.getId(), true);
			if (mWalidator.czyPrzekraczaLimity(lvAbsencja)) {
				mObslugaAbsencji.dodajAbsencje(lvUsuwana);
			} else {
				mWalidator.czyDniL4Ciagiem(lvAbsencja);
				mObslugaAbsencji.dodajAbsencje(lvAbsencja);
				dispose();
			}
		}
	}

	private List<AbsencjaDTO> budujAbsencje() {

		List<AbsencjaDTO> lvLista = new ArrayList<>();
		AbsencjaDTO lvAbsencja = budujNiepelnaAbsencje();
		if (mListaPracownikow.isEmpty()) {
			lvAbsencja.setId(mAbsencja.getId()).setIdPracownika(mAbsencja.getIdPracownika());
			lvLista.add(lvAbsencja);
		} else {
			mListaPracownikow.forEach(lvPrac -> lvLista.add(new AbsencjaDTO(lvAbsencja).setIdPracownika(lvPrac.getId())));
		}
		return lvLista;
	}

	private AbsencjaDTO budujNiepelnaAbsencje() {

		LocalDate lvOd = mDataOd.getDateValue();
		LocalDate lvDo = mDataDo.getDateValue();

		AbsencjaDTO lvNowaAbsencja = new AbsencjaDTO();
		lvNowaAbsencja.setOkres(new Interval(lvOd, lvDo));
		lvNowaAbsencja.setRodzaj((SLRodzajeAbsencji) cbRodzajAbsencji.getSelectedItem());
		lvNowaAbsencja.setProcent((SLEkwiwalentZaUrlop) cbProcent.getSelectedItem());
		return lvNowaAbsencja;
	}

	@Override
	protected boolean validateForm() {
		LocalDate lvOd = mDataOd.getDateValue();
		LocalDate lvDo = mDataDo.getDateValue();

		if (!mWalidator.czyPrawidloweDaty(lvOd, lvDo))
			return false;
		return super.validateForm();
	}
}
