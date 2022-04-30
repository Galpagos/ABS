package pl.home.absencje;

import ProjektGlowny.commons.utils.Interval;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import pl.home.components.frames.mainframes.OknoAbsencji;
import pl.home.components.frames.parameters.OAbsencjiWejscie;

public class ObslugaAbsencji {

	private AbsencjeRepositoryDB mRepo = new AbsencjeRepositoryDB();

	public boolean saveAbsence(AbsencjaDTO pmAbsencja) {
		if (checkAbsence(pmAbsencja)) {
			mRepo.saveAbsence(pmAbsencja);
			return true;
		}
		return false;
	}

	public void deleteAbsence(Integer pmId) {
		mRepo.deleteAbsence(pmId);
	}

	private boolean checkAbsence(AbsencjaDTO pmAbsencja) {
		return mRepo.getWorkerAbsenceInTerms(pmAbsencja.getIdPracownika(), pmAbsencja.getOkres()).isEmpty();

	}

	public boolean saveAbsence(AbsencjaDTO pmAbsencja, List<PracownikDTO> pmListaPracownikow) {
		boolean lvOk = true;
		for (PracownikDTO lvPracownik : pmListaPracownikow) {

			pmAbsencja.setIdPracownika(lvPracownik.getId());
			lvOk = saveAbsence(pmAbsencja) && lvOk;
		}
		return lvOk;
	}

	public List<AbsencjaDTO> getAbsencjePracownika(Integer pmPracownikId) {
		return mRepo.getAbsencjePracownika(pmPracownikId);
	}

	public void deleteAbsence(List<Integer> pmSelectedAbsenceId) {
		pmSelectedAbsenceId.forEach(lvId -> mRepo.deleteAbsence(lvId));
	}

	public void modifyAbsence(Integer pmId) {
		OAbsencjiWejscie lvParams = OAbsencjiWejscie.builder().withAbsencja(mRepo.getAbsenceById(pmId).get()).build();
		new OknoAbsencji(lvParams).get();
	}

	public Optional<AbsencjaDTO> getAbsenceById(int pmId) {
		return mRepo.getAbsenceById(pmId);
	}

	public void deleteAbsence(List<Integer> pmSelectedAbsenceId, LocalDate pmData) {
		pmSelectedAbsenceId.forEach(lvId -> divideAndDelete(lvId, pmData));
	}

	private void divideAndDelete(Integer pmId, LocalDate pmData) {
		AbsencjaDTO lvAbsencja = mRepo.getAbsenceById(pmId).get();
		mRepo.deleteAbsence(pmId);

		if (lvAbsencja.getOkres().getStart().isBefore(pmData))
			mRepo.saveAbsence(new AbsencjaDTO(lvAbsencja)//
					.setOkres(new Interval(lvAbsencja.getOkres().getStart(), pmData.minusDays(1))));

		if (lvAbsencja.getOkres().getEnd().isAfter(pmData))
			mRepo.saveAbsence(new AbsencjaDTO(lvAbsencja)//
					.setOkres(new Interval(pmData.plusDays(1), lvAbsencja.getOkres().getEnd())));

	}
}
