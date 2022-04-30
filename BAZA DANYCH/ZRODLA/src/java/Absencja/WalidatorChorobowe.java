package Absencja;

import ProjektGlowny.commons.utils.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import Pracownik.PracownikRepository;
import enums.SLRodzajeAbsencji;

public class WalidatorChorobowe extends WalidatorRodzajuAbsencji {

	private static final List<SLRodzajeAbsencji> RODZAJE_CHOROBOWE = Arrays.asList(//
			SLRodzajeAbsencji.ciaza, //
			SLRodzajeAbsencji.L_4, //
			SLRodzajeAbsencji.szpital);

	private static final int WIEK50_ZMIANY_LIMITU = 50;
	private static final int LIMIT_PO_50 = 14;
	private static final int LIMIT_DO_50 = 33;
	private PracownikRepository mPracownikRepo = new PracownikRepository();

	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return RODZAJE_CHOROBOWE;
	}

	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return this::wyznaczLimit;
	}

	private Integer wyznaczLimit(Integer pmIdPracownika) {
		Date lvDataUr = mPracownikRepo.getDataUrodzenia(pmIdPracownika);
		if (lvDataUr != null) {
			int lvRokUrodzenia = Data.LocalDateFromDate(lvDataUr).getYear();
			if (mOkres.getEnd().getYear() - lvRokUrodzenia <= WIEK50_ZMIANY_LIMITU)
				return LIMIT_DO_50;
		}
		return LIMIT_PO_50;
	}

	// TO DO _ DATA GRANICZNA
	// TO DO _ BADANIA OKRESOWE
}
