package pl.home.absencje;

import java.util.List;

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

	private boolean checkAbsence(AbsencjaDTO pmAbsencja) {
		return mRepo.getWorkerAbsenceInTerms(pmAbsencja.getIdPracownika(), pmAbsencja.getOkres()).isEmpty();

	}

	public boolean saveAbsence(AbsencjaDTO pmAbsencja, List<PracownikDTO> pmListaPracownikow) {
		boolean lvOk = true;
		for (PracownikDTO lvPracownik : pmListaPracownikow) {

			pmAbsencja.setIdPracownika(lvPracownik.getId());
			lvOk = lvOk && saveAbsence(pmAbsencja);
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
		OAbsencjiWejscie lvParams = OAbsencjiWejscie.builder().withAbsencja(mRepo.getAbsenceById(pmId)).build();
		new OknoAbsencji(lvParams).get();
	}
}
