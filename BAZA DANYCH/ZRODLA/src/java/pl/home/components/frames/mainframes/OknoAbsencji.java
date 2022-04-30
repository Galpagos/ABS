package pl.home.components.frames.mainframes;

import ProjektGlowny.commons.utils.Data;
import ProjektGlowny.commons.utils.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import Absencja.WalidatorAbsenci;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.SLRodzajeAbsencji;
import enums.WalidacjeTwarde;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.absencje.ObslugaAbsencji;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.parameters.OAbsencjiWyjscie;
import pl.home.components.frames.src.SrcOknoAbsencji;

public class OknoAbsencji extends SrcOknoAbsencji {

	private static final long serialVersionUID = 1L;
	private AbsencjaDTO mAbsencja;
	private ObslugaAbsencji mObsAbs;
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
		mObsAbs = new ObslugaAbsencji();
		mWalidator = new WalidatorAbsenci();
	}

	@Override
	protected void odswiezKontrolki() {

		super.odswiezKontrolki();
		if (mParamsIn.isCzyTylkoRodzaj()) {
			mDataDo.setVisible(false);
			mDataOd.setVisible(false);
			lblDataDo.setVisible(false);
			lblDataOd.setVisible(false);
			btnPickDate1.setVisible(false);
			btnPickDate2.setVisible(false);

		} else {
			cbRodzajAbsencji.setSelectedItem(mAbsencja.getRodzaj());
			cbProcent.setSelectedItem(mAbsencja.getProcent());
			mDataOd.setValue(Data.DateFromLocalDate(mAbsencja.getOkres().getStart()));
			mDataDo.setValue(Data.DateFromLocalDate(mAbsencja.getOkres().getEnd()));
		}
	}

	@Override
	public void zapiszAbsencje() {
		if (mParamsIn.isCzyTylkoRodzaj())
			return;
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

			Optional<AbsencjaDTO> lvUsuwana = mObsAbs.getAbsenceById(mAbsencja.getId());

			mObsAbs.deleteAbsence(mAbsencja.getId());
			if (mWalidator.czyPrzekraczaLimity(lvAbsencja) && lvUsuwana.isPresent()) {
				mObsAbs.saveAbsence(lvUsuwana.get());
			} else {
				mWalidator.czyDniL4Ciagiem(lvAbsencja);
				mObsAbs.saveAbsence(lvAbsencja);
				dispose();
			}
		}
	}

	private List<AbsencjaDTO> budujAbsencje() {

		List<AbsencjaDTO> lvLista = new ArrayList<>();
		AbsencjaDTO lvAbsencja = budujNiepelnaAbsencje();
		if (mListaPracownikow == null || mListaPracownikow.isEmpty()) {
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

	@Override
	protected OAbsencjiWyjscie budujWyjscie() {

		return OAbsencjiWyjscie//
				.builder()//
				.withAbsencja(budujNiepelnaAbsencje())//
				.build();
	}
}
