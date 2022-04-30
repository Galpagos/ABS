package Absencja;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import Pracownik.PracownikRepository;
import enums.SLRodzajeAbsencji;

public class WalidatorUrlopuWypoczynkowego extends WalidatorRodzajuAbsencji {

	private PracownikRepository mPracRepo;
	private static List<SLRodzajeAbsencji> WALIDOWANE_RODZAJE = Arrays.asList(//
			SLRodzajeAbsencji.urlop_w_pracy, //
			SLRodzajeAbsencji.urlop_wypoczynkowy);

	WalidatorUrlopuWypoczynkowego() {
		super();
	}

	WalidatorUrlopuWypoczynkowego setRepository(PracownikRepository pmRepo) {
		mPracRepo = pmRepo;
		return this;
	}

	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return WALIDOWANE_RODZAJE;
	}

	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return mPracRepo::getUrlopNalezny;
	}

}
