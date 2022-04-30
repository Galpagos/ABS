package Absencja;

import Pracownik.PracownikRepository;

public enum WalidatorRodzajuAbsencjiFactory {

	INSTANCE;

	public WalidatorRodzajuAbsencji createForUrlopWypoczynkowy(Integer pmRok) {
		return new WalidatorUrlopuWypoczynkowego()//
				.setRepository(new PracownikRepository())//
				.setRok(pmRok);
	}

	public WalidatorRodzajuAbsencji createForUrlopOjcowski(Integer pmRok) {
		return new WalidatorUrlopuOjcowskiego();
	}

	public WalidatorRodzajuAbsencji createForRodzicielski(Integer pmRok) {
		return new WalidatorUrlopuRodzicielskiego();
	}
	public WalidatorRodzajuAbsencji createForMacierzynski(Integer pmRok) {
		return new WalidatorUrlopuMacierzynskiego();
	}

	public WalidatorRodzajuAbsencji createEmpty(Integer pmRok) {
		return new WalidatorPusty();
	}

	public WalidatorRodzajuAbsencji createForNZ(Integer pmRok) {
		return new WalidatorNaZadanie()//
				.setRok(pmRok);
	}
	public WalidatorRodzajuAbsencji createOpiekiNaDziecko(Integer pmRok) {
		return new WalidatorOpiekiNaDziecko()//
				.setWalidatorNaKogos(createOpiekiNaKogos(pmRok))//
				.setRok(pmRok);

	}
	public WalidatorRodzajuAbsencji createOpiekiNaKogos(Integer pmRok) {
		return new WalidatorOpiekiNaKogos()//
				.setRok(pmRok);
	}

	public WalidatorRodzajuAbsencji createChorobowe(Integer pmRok) {
		return new WalidatorChorobowe()//
				.setRok(pmRok);
	}
}
